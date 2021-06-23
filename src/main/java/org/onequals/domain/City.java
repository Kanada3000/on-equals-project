package org.onequals.domain;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;
    private String country;

    public City() {
    }

    public City(Long id, String city, String country) {
        this.id = id;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
