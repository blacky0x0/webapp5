package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.ContactType;
import ru.javawebinar.webapp.model.Resume;

import java.io.*;
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
        } catch (IOException e) {
            throw new WebAppException("Couldn't read file " + file.getAbsolutePath(), e);
        }
        return r;

    }
}
