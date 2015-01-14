package ru.javawebinar.webapp.storage;

import org.junit.BeforeClass;

public class ArrayStorageTest extends AbstractStorageTest {

    @BeforeClass
    public static void beforeClass() {
        storage = new ArrayStorage();
    }

}