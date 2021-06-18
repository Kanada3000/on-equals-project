package org.onequals.repo;

import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VacancyRepo extends JpaRepository<Vacancy, Long> {

    @Query("SELECT COUNT(v) FROM Vacancy v")
    int countVacancy();
}