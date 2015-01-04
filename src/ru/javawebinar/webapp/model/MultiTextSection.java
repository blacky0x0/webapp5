package ru.javawebinar.webapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 26.12.2014.
 */
public class MultiTextSection extends Section {
    private List<String> values = new ArrayList<>();

    public MultiTextSection() {}

    public MultiTextSection(MultiTextSection section) {
        this.values = new ArrayList<>(section.values);
    }
}
