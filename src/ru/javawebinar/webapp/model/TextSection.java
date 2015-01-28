package ru.javawebinar.webapp.model;

/**
 * GKislin
 * 26.12.2014.
 */
public class TextSection extends Section{
    private String title;
    private String comment;

    public TextSection() {}

    public TextSection(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    public TextSection(TextSection section) {
        this.title = section.title;
        this.comment = section.comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
