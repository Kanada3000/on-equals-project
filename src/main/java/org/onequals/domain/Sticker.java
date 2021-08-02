package org.onequals.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sticker")
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private String text;
    private Timestamp createdDate;

    public Sticker() {
    }

    public Sticker(Long id, String title, String image, String text, Timestamp createdDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.text = text;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
