package ru.javawebinar.webapp.storage;

import org.junit.BeforeClass;

/**
 * GKislin
 * blacky0x0
 * 09.01.2015.
 */
public class MapStorageTest extends AbstractStorageTest {

    @BeforeClass
    public static void beforeClass() {
        storage = new MapStorage();
    }

}
