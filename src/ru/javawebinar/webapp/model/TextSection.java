package ru.javawebinar.webapp.model;

/**
 * GKislin
 * 26.12.2014.
 */
public class TextSection extends Section{
    private String title;
    private String comment;

    public TextSection() {}

    public TextSection(TextSection section) {
        this.title = section.title;
        this.comment = section.comment;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
