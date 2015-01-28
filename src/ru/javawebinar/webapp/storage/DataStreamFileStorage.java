package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GKislin
 * 23.01.2015.
 */
public class DataStreamFileStorage extends FileStorage {

    public DataStreamFileStorage(String path) {
        super(path);
    }

    protected boolean isNotNull(String str) {
        return str != null;
    }

    protected void write(File file, Resume r) {
        try (FileOutputStream fos = new FileOutputStream(file); DataOutputStream dos = new DataOutputStream(fos)) {

            dos.writeBoolean(isNotNull(r.getUuid()));
            if (isNotNull(r.getUuid()))
                dos.writeUTF((r.getUuid()));

            dos.writeBoolean(isNotNull(r.getFullName()));
            if (isNotNull(r.getFullName()))
                dos.writeUTF((r.getFullName()));

            dos.writeBoolean(isNotNull(r.getLocation()));
            if (isNotNull(r.getLocation()))
                dos.writeUTF((r.getLocation()));

            dos.writeBoolean(isNotNull(r.getHomePage()));
            if (isNotNull(r.getHomePage()))
                dos.writeUTF((r.getHomePage()));

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeInt(entry.getKey().ordinal());
                dos.writeUTF(entry.getValue());
            }

            // TODO section implementation
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeInt(entry.getKey().ordinal());

                if (entry.getKey() == SectionType.OBJECTIVE) {
                    if (!(entry.getValue() instanceof TextSection)) {
                        throw new WebAppException(SectionType.OBJECTIVE + " must be always a TextSection type");
                    }
                    dos.writeUTF(((TextSection) entry.getValue()).getTitle());
                    dos.writeUTF(((TextSection) entry.getValue()).getComment());
                    continue;
                }

                if (entry.getKey() == SectionType.ACHIEVEMENT) {
                    if (!(entry.getValue() instanceof MultiTextSection)) {
                        throw new WebAppException(SectionType.ACHIEVEMENT + " must be always a MultiTextSection type");
                    }

                    MultiTextSection multi = (MultiTextSection) entry.getValue();

                    // write the number of elements
                    dos.writeInt(multi.getValues().size());

                    for (String item : multi.getValues())
                        dos.writeUTF(item);
                }
            }
        } catch (IOException e) {
            throw new WebAppException("Couldn't write file " + file.getAbsolutePath(), r, e);
        }
    }

    protected Resume read(File file) {
        Resume r = new Resume();
        try (InputStream is = new FileInputStream(file); DataInputStream dis = new DataInputStream(is)) {
            if (dis.readBoolean())
                r.setUuid(dis.readUTF());

            if (dis.readBoolean())
                r.setFullName(dis.readUTF());

            if (dis.readBoolean())
                r.setLocation(dis.readUTF());

            if (dis.readBoolean())
                r.setHomePage(dis.readUTF());

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                r.addContact(ContactType.VALUES[dis.readInt()], dis.readUTF());
            }

            // TODO section implementation
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.VALUES[dis.readInt()];

                if (sectionType == SectionType.OBJECTIVE) {
                    r.addSection(sectionType, new TextSection(dis.readUTF(), dis.readUTF()));
                    continue;
                }

                if (sectionType == SectionType.ACHIEVEMENT) {
                    // read the number of elements
                    int achievemetListSize = dis.readInt();
                    List<String> achievemetList = new ArrayList<>(achievemetListSize);

                    for (int j = 0; j < achievemetListSize; j++)
                        achievemetList.add(dis.readUTF());

                    r.addSection(sectionType, new MultiTextSection(achievemetList));
                }

            }

        } catch (IOException e) {
            throw new WebAppException("Couldn't read file " + file.getAbsolutePath(), e);
        }
        return r;

    }
}
