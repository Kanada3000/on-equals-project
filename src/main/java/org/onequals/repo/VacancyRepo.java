package org.onequals.repo;

import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface VacancyRepo extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v WHERE v.approved = true AND v.user.hidden <> true ")
    List<Vacancy> findAll();

    @Query("SELECT v FROM Vacancy v WHERE v.approved = true AND v.user.hidden <> true ")
    List<Vacancy> findAll(Sort sort);

    @Query("SELECT v FROM Vacancy v WHERE v.approved = true")
    List<Vacancy> findAllAll();

    @Query("SELECT v FROM Vacancy v WHERE v.approved = true")
    List<Vacancy> findAllAll(Sort sort);

    @Query("SELECT v FROM Vacancy v WHERE v.user.id = ?1")
    List<Vacancy> findByUser(Long id);

    @Query("SELECT v FROM Vacancy v WHERE lower(CONCAT(" +
            "' ', v.category.longName, " +
            "' ', v.salary, " +
            "' ', v.description, " +
            "' ', v.type.name, " +
            "' ', v.user.name)) LIKE CONCAT('% ', ?1, ' %' )  AND v IN ?2")
    List<Vacancy> filterByKey(String key, List<Vacancy> vacancies);

    @Query("SELECT v FROM Vacancy v WHERE v.category.longName = ?1 AND v IN ?2")
    List<Vacancy> filterByCategory(String category, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE v.category.id IN ?1 AND v IN ?2")
    List<Vacancy> filterByCategoryList(List<Long> category, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE v.type.id IN ?1 AND v IN ?2")
    List<Vacancy> filterByTypeList(List<Long> type, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE ?1 IN (SELECT c.city FROM City c WHERE c MEMBER OF v.city) AND v IN ?2")
    List<Vacancy> filterByCity(String city, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE (v.salary BETWEEN ?1 and ?2) AND v IN ?3")
    List<Vacancy> sort(int min, int max, List<Vacancy> vacancy, Sort sort);

    @Query("SELECT v FROM Vacancy v WHERE CONCAT(v.id,'') IN ?1")
    Set<Vacancy> findByIdString(List<String> likes);

    @Query("SELECT u FROM User u WHERE (SELECT v FROM Vacancy v WHERE v.id = ?1) MEMBER OF u.likedVacancy")
    List<User> getUsersByLike(Long id);

    @Query("SELECT v FROM Vacancy v WHERE v.approved = false")
    List<Vacancy> findUnapproved();

    @Query("SELECT v FROM Vacancy v WHERE v.approved = false")
    List<Vacancy> findUnapproved(Sort sort);

    @Query("SELECT c FROM Vacancy c join c.city cit WHERE c.approved = false AND " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'user') AND (lower(c.user.username) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'type') AND (lower(c.type.name) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'category') AND (lower(c.category.longName) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'city') AND (lower(cit.city) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'salary') AND (lower(c.salary) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'description') AND (lower(c.description) LIKE CONCAT('%', lower(?2), '%' )))")
    List<Vacancy> findUnapproved(String field, String value, Sort sort);

    @Query("SELECT v FROM Vacancy v WHERE v in ?1")
    List<Vacancy> sortAdmin(List<Vacancy> vacancies, Sort sort);

    @Query("SELECT c FROM Vacancy c join c.city cit WHERE " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'user') AND (lower(c.user.username) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'type') AND (lower(c.type.name) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'category') AND (lower(c.category.longName) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'city') AND (lower(cit.city) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'salary') AND (lower(c.salary) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'description') AND (lower(c.description) LIKE CONCAT('%', lower(?2), '%' )))")
    List<Vacancy> sortAdmin(List<Vacancy> vacancies, String field, String value, Sort sort);
}