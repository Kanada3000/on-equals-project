package org.onequals.domain;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longName;

    private int total = 0;

    public Category(Long id, String longName, int total) {
        this.id = id;
        this.longName = longName;
        this.total = total;
    }

    public Category(Long id, Long total) {
        this.id = id;
        this.total = total.intValue();
    }

    public Category() {

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getLongName(){
        return longName;
    }

    public void setLongName(String longName){
        this.longName = longName;
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }
}
