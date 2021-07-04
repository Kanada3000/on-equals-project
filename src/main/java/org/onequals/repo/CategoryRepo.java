package org.onequals.repo;

import org.onequals.domain.Category;
import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.longName = ?1")
    Category findCategoryByLong(String l);

    @Query("SELECT new Category(v.category.id, COUNT(v.category)) FROM Vacancy v WHERE v.id IN (?1) GROUP BY v.category")
    List<Category> countByCategoryList(List<Long> id);

    @Modifying
    @Query("UPDATE Vacancy v SET v.category = (SELECT c FROM Category c WHERE c.id = 1) WHERE v.category.id = ?1")
    void deleteFromVacancy(Long id);

    @Modifying
    @Query("UPDATE Resume r SET r.category = (SELECT c FROM Category c WHERE c.id = 1) WHERE r.category.id = ?1")
    void deleteFromResume(Long id);

    @Modifying
    @Query("UPDATE Employer e SET e.category = (SELECT c FROM Category c WHERE c.id = 1) WHERE e.category.id = ?1")
    void deleteFromEmployer(Long id);

    @Modifying
    @Query("UPDATE Seeker s SET s.category = (SELECT c FROM Category c WHERE c.id = 1) WHERE s.category.id = ?1")
    void deleteFromSeeker(Long id);
}