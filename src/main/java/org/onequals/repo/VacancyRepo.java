package org.onequals.repo;

import org.onequals.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VacancyRepo extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v " +
            "WHERE (v.salary >= ?1 AND v.salary <= ?2)")
    List<Vacancy> findNeeded(int min, int max, Sort sort);

    @Query("SELECT v FROM Vacancy v " +
            "WHERE (v.salary >= ?1 AND v.salary <= ?2) " +
            "AND v.category.id IN ?3 ")
    List<Vacancy> findNeededCategory(int min, int max, List<Long> category, Sort sort);

    @Query("SELECT v FROM Vacancy v " +
            "WHERE (v.salary >= ?1 AND v.salary <= ?2) " +
            "AND v.type.id IN ?3 ")
    List<Vacancy> findNeededType(int min, int max, List<Long> type, Sort sort);

    @Query("SELECT v FROM Vacancy v " +
            "WHERE (v.salary >= ?1 AND v.salary <= ?2) " +
            "AND v.category.id IN ?3 " +
            "AND v.type.id IN ?4 ")
    List<Vacancy> findNeededCategoryType(int min, int max, List<Long> category, List<Long> type, Sort sort);

    @Query("SELECT v FROM Vacancy v WHERE v.user.id = ?1")
    Vacancy findByUser(Long id);
}