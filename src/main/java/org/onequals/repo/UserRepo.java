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

    @Query("SELECT u FROM User u join u.roles ur WHERE ur = ?1 AND ur.size = ?2")
    List<User> findAllByRoleUser(Set<Role> roles, int size);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findByVacancy(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.link = ?1")
    User findUserByLink(String link);

    @Query("SELECT e.email FROM Employer e WHERE e.user = ?1")
    String findEmailEmp(User user);

    @Query("SELECT s.email FROM Seeker s WHERE s.user = ?1")
    String findEmailSeek(User user);
}