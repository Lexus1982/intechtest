package me.alexand.intech.server.model;

import javax.persistence.*;

/**
 * @author asidorov84@gmail.com
 */

@Entity
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String body;

    @Column
    @Enumerated(EnumType.STRING)
    private ContentCategory category;

    @Column
    private int duration;

    public Content() {
    }

    public Content(int id, String title, String body, ContentCategory category, int duration) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public ContentCategory getCategory() {
        return category;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + body.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + duration;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null || obj.getClass() != getClass()) return false;

        Content arg = (Content) obj;

        return id == arg.id &&
                duration == arg.duration &&
                (title != null && title.equals(arg.title)) &&
                (body != null && body.equals(arg.body)) &&
                (category != null && category.equals(arg.category));
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", category=" + category +
                ", duration=" + duration +
                '}';
    }
}
