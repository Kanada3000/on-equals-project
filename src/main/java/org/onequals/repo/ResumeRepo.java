package org.onequals.repo;

import org.onequals.domain.Resume;
import org.onequals.domain.Vacancy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeRepo extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r " +
            "WHERE (r.salary >= ?1 AND r.salary <= ?2)")
    List<Resume> findNeeded(int min, int max, Sort sort);

    @Query("SELECT r FROM Resume r " +
            "WHERE (r.salary >= ?1 AND r.salary <= ?2) " +
            "AND r.category.id IN ?3 ")
    List<Resume> findNeededCategory(int min, int max, List<Long> category, Sort sort);

    @Query("SELECT r FROM Resume r " +
            "WHERE (r.salary >= ?1 AND r.salary <= ?2) " +
            "AND r.type.id IN ?3 ")
    List<Resume> findNeededType(int min, int max, List<Long> type, Sort sort);

    @Query("SELECT r FROM Resume r " +
            "WHERE (r.salary >= ?1 AND r.salary <= ?2) " +
            "AND r.category.id IN ?3 " +
            "AND r.type.id IN ?4 ")
    List<Resume> findNeededCategoryType(int min, int max, List<Long> category, List<Long> type, Sort sort);
}