package org.onequals.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private Boolean blocked = Boolean.FALSE;
    private Boolean activated = Boolean.FALSE;
    private Boolean hidden = Boolean.FALSE;
    private String file;
    private Timestamp createdDate;

    @Column(unique = true)
    private String link;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Collections.singleton(Role.USER);

    @ManyToMany
    @JoinTable(
            name = "vacancy_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id"))
    private Set<Vacancy> likedVacancy;

    @ManyToMany
    @JoinTable(
            name = "resume_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "resume_id"))
    private Set<Resume> likedResume;

    public User() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(Long id, String name, String username, String password, Boolean blocked, Boolean activated,
                Boolean hidden, String link, Set<Role> roles, Set<Vacancy> likedVacancy, Set<Resume> likedResume,
                String file, Timestamp createdDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.blocked = blocked;
        this.activated = activated;
        this.hidden = hidden;
        this.link = link;
        this.roles = roles;
        this.likedVacancy = likedVacancy;
        this.likedResume = likedResume;
        this.file = file;
        this.createdDate = createdDate;
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

    public String getUsername() {
        return username;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Vacancy> getLikedVacancy() {
        return likedVacancy;
    }

    public void setLikedVacancy(Set<Vacancy> likedVacancy) {
        this.likedVacancy = likedVacancy;
    }

    public Set<Resume> getLikedResume() {
        return likedResume;
    }

    public void setLikedResume(Set<Resume> likedResume) {
        this.likedResume = likedResume;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                ", activated=" + activated +
                ", hidden=" + hidden +
                ", link='" + link + '\'' +
                ", roles=" + roles +
                ", likedVacancy=" + likedVacancy +
                ", likedResume=" + likedResume +
                '}';
    }
}