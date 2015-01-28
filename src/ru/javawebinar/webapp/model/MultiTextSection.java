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

    public MultiTextSection(List<String> values) {
        this.values = values;
    }

    public MultiTextSection(MultiTextSection section) {
        this.values = new ArrayList<>(section.values);
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiTextSection that = (MultiTextSection) o;

        if (values != null ? !values.equals(that.values) : that.values != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return values != null ? values.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MultiTextSection{" +
                "values=" + values +
                '}';
    }
}
