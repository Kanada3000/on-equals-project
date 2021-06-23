package org.onequals.domain;

import javax.persistence.*;

@Entity
@Table(name = "employer")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String email;
    private String site;
    private String linkFacebook;
    private String linkInstagram;
    private String linkTwitter;
    private String linkLinkedIn;
    private int age;
    private int empCount;
    private int size;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private City city;

    public Employer() {
    }

    public Employer(Long id, User user, String name, String email, String site, String linkFacebook,
                    String linkInstagram, String linkTwitter, String linkLinkedIn, int age, int empCount,
                    int size, String description, Category category, City city) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.email = email;
        this.site = site;
        this.linkFacebook = linkFacebook;
        this.linkInstagram = linkInstagram;
        this.linkTwitter = linkTwitter;
        this.linkLinkedIn = linkLinkedIn;
        this.age = age;
        this.empCount = empCount;
        this.size = size;
        this.description = description;
        this.category = category;
        this.city = city;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public String getLinkInstagram() {
        return linkInstagram;
    }

    public void setLinkInstagram(String linkInstagram) {
        this.linkInstagram = linkInstagram;
    }

    public String getLinkTwitter() {
        return linkTwitter;
    }

    public void setLinkTwitter(String linkTwitter) {
        this.linkTwitter = linkTwitter;
    }

    public String getLinkLinkedIn() {
        return linkLinkedIn;
    }

    public void setLinkLinkedIn(String linkLinkedIn) {
        this.linkLinkedIn = linkLinkedIn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEmpCount() {
        return empCount;
    }

    public void setEmpCount(int empCount) {
        this.empCount = empCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
