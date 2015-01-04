package ru.javawebinar.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javawebinar.webapp.model.Contact;
import ru.javawebinar.webapp.model.ContactType;
import ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorageTest {
    private static Resume R1, R2, R3;
    private static final int NUMBER_OF_RESUMES = 3;

    private ArrayStorage storage = new ArrayStorage();

    static {
        R1 = new Resume("Полное Имя1", "location1");
        R1.addContact(new Contact(ContactType.MAIL, "mail1@ya.ru"));
        R1.addContact(new Contact(ContactType.PHONE, "11111"));
        R2 = new Resume("Полное Имя2", null);
        R2.addContact(new Contact(ContactType.SKYPE, "skype2"));
        R2.addContact(new Contact(ContactType.PHONE, "22222"));
        R3 = new Resume("Полное Имя3", null);
    }

    @BeforeClass
    public static void beforeClass() {
        // the same as static {}
    }

    @Before
    public void before() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void testSave() throws Exception {
        Resume R4 = new Resume("fullname", "Super location");
        R4.addContact(new Contact(ContactType.MAIL, "resume@ya.ru"));

        storage.save(R4);
        Assert.assertEquals(R4, storage.load(R4.getUuid()));
        Assert.assertEquals(NUMBER_OF_RESUMES + 1, storage.size());
    }

    @Test (expected = Exception.class)
    public void testSaveNull() throws Exception {
        storage.save(null);
        Assert.assertEquals(NUMBER_OF_RESUMES, storage.size());
    }

    @Test (expected = Exception.class)
    public void testSaveClone() throws Exception {
        storage.save(R1);
        Assert.assertEquals(NUMBER_OF_RESUMES, storage.size());
    }

    @Test
    public void testClear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void testUpdate() throws Exception {
        String updatedName = "Updated name";
        String updatedLocation = "Updated location";

        R3.setFullName(updatedName);
        R3.setLocation(updatedLocation);

        storage.update(R3);
        Assert.assertEquals(R3, storage.load(R3.getUuid()));
        Assert.assertEquals(R3.getFullName(), storage.load(R3.getUuid()).getFullName());
        Assert.assertEquals(R3.getLocation(), storage.load(R3.getUuid()).getLocation());
        Assert.assertEquals(NUMBER_OF_RESUMES, storage.size());
    }

    @Test
    public void testLoad() throws Exception {
        Assert.assertEquals(R3, storage.load(R3.getUuid()));
    }

    @Test (expected = Exception.class)
    public void testLoadEmptyUuid() throws Exception {
        Assert.assertEquals(R3, storage.load(""));
    }

    @Test
    public void testDelete() throws Exception {
        storage.delete(R3.getUuid());
        Assert.assertEquals(null, storage.load(R3.getUuid()));
        Assert.assertEquals(NUMBER_OF_RESUMES - 1, storage.size());
    }

    @Test
    public void testGetAllSorted() throws Exception {
        Assert.assertEquals(Arrays.asList(R1, R2, R3).toString(), storage.getAllSorted().toString());
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(NUMBER_OF_RESUMES, storage.size());
    }
}