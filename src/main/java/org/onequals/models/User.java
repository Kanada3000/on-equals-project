package org.onequals.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "login")
    private String login;

    @Size(min = 8, max = 30, message = "Password should be between 2 and 30 characters")
    @Column(name = "password")
    private String password;

    public User(){

    }

    public User(int id, String login, String password){
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString(){
        return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + '}';
    }
}
