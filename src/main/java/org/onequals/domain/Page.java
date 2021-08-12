package org.onequals.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String shortBody;
    private String fullBody;

    private String label;
    private String author;
    private Timestamp createdDate;
    private String category;

    public Page() {
    }

    public Page(Long id, String name, String shortBody,
                String fullBody, String label, String author,
                Timestamp createdDate, String category) {
        this.id = id;
        this.name = name;
        this.shortBody = shortBody;
        this.fullBody = fullBody;
        this.label = label;
        this.author = author;
        this.createdDate = createdDate;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortBody() {
        return shortBody;
    }

    public void setShortBody(String shortBody) {
        this.shortBody = shortBody;
    }

    public String getFullBody() {
        return fullBody;
    }

    public void setFullBody(String fullBody) {
        this.fullBody = fullBody;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
