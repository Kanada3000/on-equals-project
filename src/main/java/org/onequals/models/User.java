package org.onequals.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "login")
    private String login;

    @Size(min = 8, max = 30, message = "Password should be between 8 and 30 characters")
    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    public User(){

    }

    public User(int id, String login, String password, Set<Role> role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
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

    @Override
    public String getUsername(){
        return null;
    }

    @Override
    public boolean isAccountNonExpired(){
        return false;
    }

    @Override
    public boolean isAccountNonLocked(){
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return false;
    }

    @Override
    public boolean isEnabled(){
        return false;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Set<Role> getRole(){
        return role;
    }

    public void setRole(Set<Role> role){
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }
}
