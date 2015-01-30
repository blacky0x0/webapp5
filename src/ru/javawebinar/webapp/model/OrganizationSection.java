package ru.javawebinar.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * GKislin
 * 26.12.2014.
 */
public class OrganizationSection extends Section {
    private List<Organization> values;

    public OrganizationSection(Organization... values) {
        this.values = new LinkedList<>(Arrays.asList(values));
    }

}
