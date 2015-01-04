package ru.javawebinar.webapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 26.12.2014.
 */
public class OrganizationSection extends Section {
    private List<Organization> values = new ArrayList<>();

    public OrganizationSection() {}

    public OrganizationSection(OrganizationSection section) {
        this.values = new ArrayList<>(section.values);
    }

}
