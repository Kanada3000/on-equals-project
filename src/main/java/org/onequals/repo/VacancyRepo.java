package org.onequals.repo;

import org.onequals.domain.Vacancy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacancyRepo extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v WHERE v.user.id = ?1")
    Vacancy findByUser(Long id);

    @Query("SELECT v FROM Vacancy v WHERE (" +
            "v.city.city LIKE %?1% OR " +
            "v.category.longName LIKE %?1% OR " +
            "CONCAT(v.salary, '') LIKE %?1% OR " +
            "v.description LIKE %?1% OR " +
            "v.type.name LIKE %?1% OR " +
            "v.user.name LIKE %?1%) AND v IN ?2")
    List<Vacancy> filterByKey(String key, List<Vacancy> vacancies);

    @Query("SELECT v FROM Vacancy v WHERE v.category.longName = ?1 AND v IN ?2")
    List<Vacancy> filterByCategory(String category, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE v.category.id IN ?1 AND v IN ?2")
    List<Vacancy> filterByCategoryList(List<Long> category, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE v.type.id IN ?1 AND v IN ?2")
    List<Vacancy> filterByTypeList(List<Long> type, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE v.city.city = ?1 AND v IN ?2")
    List<Vacancy> filterByCity(String city, List<Vacancy> vacancy);

    @Query("SELECT v FROM Vacancy v WHERE (v.salary BETWEEN ?1 and ?2) AND v IN ?3")
    List<Vacancy> sort(int min, int max, List<Vacancy> vacancy, Sort sort);
}