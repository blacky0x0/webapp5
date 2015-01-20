package ru.javawebinar.webapp.model;

import java.util.*;

/**
 * gkislin
 * 12.12.2014.
 */
public class Resume {//implements Comparable<Resume> {
    private String uuid;
    private String fullName;
    private String location;
    private String homePage;
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    //private List<Section> sections = new LinkedList<>();
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName, String location) {
        this(UUID.randomUUID().toString(), fullName, location);
    }

    public Resume(String uuid, String fullName, String location) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.location = location;
    }

    public Resume() {
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLocation() {
        return location;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, location, homePage, contacts, sections);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Resume other = (Resume) obj;
        return Objects.equals(this.fullName, other.fullName) && Objects.equals(this.location, other.location) && Objects.equals(this.homePage, other.homePage) && Objects.equals(this.contacts, other.contacts) && Objects.equals(this.sections, other.sections);
    }

    //    @Override
    public int compareTo(Resume o) {
        return fullName.compareToIgnoreCase(o.fullName);
    }

    @Override
    public String toString() {
        return "fullName" + fullName + " (" + uuid + ")";
    }

/*
    private String getEmail(List<Contact> list) {
        for (Contact c : list) {
            if (c.getType() == ContactType.MAIL) {
                return c.getValue();
            }
        }
        return null;
    }
*/

}
