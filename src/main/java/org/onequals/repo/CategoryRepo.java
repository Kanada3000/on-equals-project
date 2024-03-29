package org.onequals.repo;

import org.onequals.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.longName = ?1")
    Category findCategoryByLong(String l);

    @Query("SELECT c FROM Category c WHERE c.longName <> 'Undefined'")
    List<Category> findAll();

    @Query("SELECT c FROM Category c WHERE c.longName <> 'Undefined'")
    List<Category> findAll(Sort sort);

    @Query("SELECT c FROM Category c")
    List<Category> findAllAll();

    @Query("SELECT c FROM Category c WHERE " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'name') AND (lower(c.longName) LIKE CONCAT('%', lower(?2), '%' ) ))")
    List<Category> findAllAllSortFilter(String field, String value, Sort sort);

    @Query("SELECT c FROM Category c")
    List<Category> findAllAllSort(String value, Sort sort);

    @Query("SELECT c FROM Category c")
    List<Category> findAllAllSort(Sort sort);

    @Query("SELECT new Category(v.category.id, COUNT(v.category)) FROM Vacancy v WHERE v.id IN (?1) GROUP BY v.category")
    List<Category> countByCategoryList(List<Long> id);

    @Query("SELECT new Category(r.category.id, COUNT(r.category)) FROM Resume r WHERE r.id IN (?1) GROUP BY r.category")
    List<Category> countByCategoryListResume(List<Long> id);

    @Modifying
    @Query("UPDATE Vacancy v SET v.category = (SELECT c FROM Category c WHERE c.longName = 'Undefined') WHERE v.category.id = ?1")
    void deleteFromVacancy(Long id);

    @Modifying
    @Query("UPDATE Resume r SET r.category = (SELECT c FROM Category c WHERE c.longName = 'Undefined') WHERE r.category.id = ?1")
    void deleteFromResume(Long id);

    @Modifying
    @Query("UPDATE Employer e SET e.category = (SELECT c FROM Category c WHERE c.longName = 'Undefined') WHERE e.category.id = ?1")
    void deleteFromEmployer(Long id);

    @Modifying
    @Query("UPDATE Seeker s SET s.category = (SELECT c FROM Category c WHERE c.longName = 'Undefined') WHERE s.category.id = ?1")
    void deleteFromSeeker(Long id);

    @Query("select c from Category c where CONCAT(c.id, '')=:value")
    List<Category> searchAdmin(@Param("value") String value);
}