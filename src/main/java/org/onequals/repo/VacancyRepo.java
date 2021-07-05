package org.onequals.repo;

import org.onequals.domain.Resume;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface VacancyRepo extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v WHERE v.user.id = ?1")
    List<Vacancy> findByUser(Long id);

    @Query("SELECT v FROM Vacancy v WHERE (" +
            "v IN ?2 AND " +
            "(SELECT c.city FROM City c WHERE c MEMBER OF v.city) LIKE %?1% OR " +
            "v.category.longName LIKE %?1% OR " +
            "CONCAT(v.salary, '') LIKE %?1% OR " +
            "v.description LIKE %?1% OR " +
            "v.type.name LIKE %?1% OR " +
            "v.user.name LIKE %?1%)")
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
    Set<Vacancy> findByIdString (List<String> likes);

    @Query("SELECT u FROM User u WHERE (SELECT v FROM Vacancy v WHERE v.id = ?1) MEMBER OF u.likedVacancy")
    List<User> getUsersByLike (Long id);
}