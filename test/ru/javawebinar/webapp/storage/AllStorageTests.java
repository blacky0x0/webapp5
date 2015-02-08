package ru.javawebinar.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * GKislin
 * 09.01.2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        MapStorageTest.class,
        DataStreamFileStorageTest.class,
        SerializeFileStorageTest.class,
        JsonFileStorageTest.class,
        XmlFileStorageTest.class
})
public class AllStorageTests {
}
