package org.onequals.domain;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String longName;

    private int total = 0;

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
