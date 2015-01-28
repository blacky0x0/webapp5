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

    protected void write(File file, Resume r) {
        try (FileOutputStream fos = new FileOutputStream(file); DataOutputStream dos = new DataOutputStream(fos)) {
            // TODO fix NullPointerException
            dos.writeUTF(r.getFullName());
            dos.writeUTF(r.getLocation());
            dos.writeUTF(r.getHomePage());
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
            r.setFullName(dis.readUTF());
            r.setLocation(dis.readUTF());
            r.setHomePage(dis.readUTF());
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                r.addContact(ContactType.VALUES[dis.readInt()], dis.readUTF());
            }
            // TODO section implementation
        } catch (IOException e) {
            throw new WebAppException("Couldn't read file " + file.getAbsolutePath(), e);
        }
        return null;

    }
}
