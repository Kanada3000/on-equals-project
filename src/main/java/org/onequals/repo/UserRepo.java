package org.onequals.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.onequals.models.Role;
import org.onequals.models.User;

import java.util.List;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query("SELECT u FROM User u join u.role ur WHERE ur =?1")
    List<User> findAllByRoleUser(Set<Role> roles);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findUserById(@Param("id") Long id);

}