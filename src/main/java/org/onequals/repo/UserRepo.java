package org.onequals.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;

import java.util.List;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u join u.roles ur WHERE ur =?1")
    List<User> findAllByRoleUser(Set<Role> roles);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findByVacancy(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.link = ?1")
    User findUserByLink(String link);

}