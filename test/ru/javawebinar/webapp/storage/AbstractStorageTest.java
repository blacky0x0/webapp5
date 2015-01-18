package ru.javawebinar.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.ContactType;
import ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * GKislin
 * 09.01.2015.
 */
public abstract class AbstractStorageTest {
    protected Resume R1, R2, R3;

    protected IStorage storage;

    protected final int NUMBER_OF_RESUMES = 3;

    @BeforeClass
    public static void beforeClass() {
        // the same as static {}
    }

    @Before
    public void before() {
        R1 = new Resume("Полное Имя1", "location1");
        R1.addContact(ContactType.MAIL, "mail1@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        R2 = new Resume("Полное Имя2", null);
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");
        R3 = new Resume("Полное Имя3", null);
        storage.clear();
        storage.save(R3);
        storage.save(R1);
        storage.save(R2);
    }

    @Test
    public void testSave() throws Exception {
        Resume R4 = new Resume("fullname", "Super location");
        R4.addContact(ContactType.MAIL, "resume@ya.ru");

        storage.save(R4);
        Assert.assertEquals(R4, storage.load(R4.getUuid()));
        Assert.assertEquals(NUMBER_OF_RESUMES + 1, storage.size());
    }

    @Test (expected = WebAppException.class)
    public void testSaveNull() throws Exception {
        storage.save(null);
        Assert.assertEquals(NUMBER_OF_RESUMES, storage.size());
    }

    @Test (expected = WebAppException.class)
    public void testSaveClone() throws Exception {
        storage.save(R1);
    }

    @Test
    public void testClear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test (expected = WebAppException.class)
    public void testUpdateNull() throws Exception {
        storage.update(null);
    }

    @Test
    public void testUpdate() throws Exception {
        R2.setFullName("Updated N2");
        storage.update(R2);
        assertEquals(R2, storage.load(R2.getUuid()));
    }

    @Test
    public void testLoad() throws Exception {
        assertEquals(R1, storage.load(R1.getUuid()));
        assertEquals(R2, storage.load(R2.getUuid()));
        assertEquals(R3, storage.load(R3.getUuid()));
    }

    @Test(expected = WebAppException.class)
    public void testDeleteNotFound() throws Exception {
        storage.load("dummy");
    }

    @Test
    public void testDelete() throws Exception {
        storage.delete(R1.getUuid());
        Assert.assertEquals(NUMBER_OF_RESUMES - 1, storage.size());
    }

    @Test
    public void testGetAllSorted() throws Exception {
//        Resume[] src = new Resume[]{R1, R2, R3};
//        Arrays.sort(src);
//        assertArrayEquals(src, storage.getAllSorted().toArray());
        List<Resume> list = Arrays.asList(R1, R2, R3);
        Collections.sort(list, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                return 0;
            }
        });
        assertEquals(list, storage.getAllSorted());
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(NUMBER_OF_RESUMES, storage.size());
    }

    @Test(expected = WebAppException.class)
    public void testDeleteMissed() throws Exception {
        storage.delete("dummy");
    }

    @Test(expected = WebAppException.class)
    public void testSavePresented() throws Exception {
        storage.save(R1);
    }

    @Test(expected = WebAppException.class)
    public void testUpdateMissed() throws Exception {
        Resume resume = new Resume("dummy", "fullName_U1", "location_U1");
        storage.update(resume);
    }
}
