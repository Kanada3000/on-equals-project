package org.onequals.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.onequals.domain.Role;
import org.onequals.domain.User;

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

    @Query("SELECT u FROM User u join u.roles ur WHERE ur <> ?1")
    List<User> findAllUsers(Set<Role> roles);

    @Query("SELECT u FROM User u join u.roles ur WHERE ur <> ?1")
    List<User> findAllUsersSort(Set<Role> roles, Sort sort);

    @Query("SELECT c FROM User c join c.roles ur WHERE ur <> ?1 AND " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'name') AND (lower(c.name) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'username') AND (lower(c.username) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'hidden') AND (lower(c.hidden) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'activated') AND (lower(c.activated) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'blocked') AND (lower(c.blocked) LIKE CONCAT('%', lower(?2), '%' )))")
    List<User> findAllUsersSort(Set<Role> roles, String field, String value, Sort sort);

    @Query("SELECT u.file FROM User u WHERE u.file <> null")
    List<String> findAllFiles();

}