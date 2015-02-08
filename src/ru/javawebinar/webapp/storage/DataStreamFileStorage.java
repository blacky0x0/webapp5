package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * GKislin
 * 23.01.2015.
 */
public class DataStreamFileStorage extends FileStorage {
    private static final String NULL = "null";

    public DataStreamFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(OutputStream os, Resume resume) throws IOException {
        try (final DataOutputStream dos = new DataOutputStream(os)) {
            writeString(dos, resume.getUuid());
            writeString(dos, resume.getFullName());
            writeString(dos, resume.getLocation());
            writeString(dos, resume.getHomePage());
            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeInt(entry.getKey().ordinal());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                writeString(dos, type.name());
                switch (type) {
                    case OBJECTIVE:
                        writeString(dos, ((TextSection) section).getValue());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((MultiTextSection) section).getValues(), value -> writeString(dos, value));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollection(dos, ((OrganizationSection) section).getValues(), value -> writeOrganization(dos, value));
                        break;
                }
            }
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        Resume r = new Resume();
        try (DataInputStream dis = new DataInputStream(is)) {
            r.setUuid(readString(dis));
            r.setFullName(readString(dis));
            r.setLocation(readString(dis));
            r.setHomePage(readString(dis));
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                r.addContact(ContactType.VALUES[dis.readInt()], readString(dis));
            }
            final int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(readString(dis));
                switch (sectionType) {
                    case OBJECTIVE:
                        r.addObjective(readString(dis));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(sectionType,
                                new MultiTextSection(readList(dis, () -> readString(dis))));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        r.addSection(sectionType,
                                new OrganizationSection(readList(dis, () -> readOrganization(dis))));
                        break;
                }
            }
            return r;
        }
    }

    private void writeOrganization(DataOutputStream dos, Organization organization) throws IOException {
        writeString(dos, organization.getLink().getName());
        writeString(dos, organization.getLink().getUrl());
        dos.writeInt(organization.getPeriods().size());

        for (Organization.Period period : organization.getPeriods()) {
            dos.writeInt(period.getStartDate().getYear());
            dos.writeInt(period.getStartDate().getMonthValue());

            dos.writeInt(period.getEndDate().getYear());
            dos.writeInt(period.getEndDate().getMonthValue());

            writeString(dos, period.getPosition());
            writeString(dos, period.getContent());
        }
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Organization organization = new Organization();
        Link link = new Link(readString(dis), readString(dis));

        int numPeriods = dis.readInt();

        organization.setLink(link);

        for (int i = 0; i < numPeriods; i++) {
            Organization.Period curPeriod = new Organization.Period();

            curPeriod.setStartDate(LocalDate.of(dis.readInt(), dis.readInt(), 1));
            curPeriod.setEndDate(LocalDate.of(dis.readInt(), dis.readInt(), 1));

            curPeriod.setPosition(readString(dis));
            curPeriod.setContent(readString(dis));
            organization.addPeriod(curPeriod);
        }

        return organization;
    }

    private void writeString(DataOutputStream dos, String str) throws IOException {
        dos.writeUTF(str == null ? NULL : str);
    }

    private String readString(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return str.equals(NULL) ? null : str;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}
