package org.onequals.repo;

import org.onequals.domain.Category;
import org.onequals.domain.Employer;
import org.onequals.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployerRepo extends JpaRepository<Employer, Long> {

    @Query("SELECT e FROM Employer e WHERE e.user = ?1")
    Employer findEmployerByUser(User user);
}