package org.onequals.repo;

import org.onequals.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CityRepo extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.city <> 'Undefined'")
    List<City> findAll();

    @Query("SELECT c FROM City c WHERE c.city <> 'Undefined'")
    List<City> findAll(Sort sort);

    @Query("SELECT c FROM City c")
    List<City> findAllAll();

    @Query("SELECT c FROM City c")
    List<City> findAllAll(Sort sort);

    @Query("SELECT c FROM City c WHERE c.city = ?1")
    City findCity(String city);

    @Query("SELECT c FROM City c WHERE c.country = ?1")
    List<City> findByCountry(String country);

    @Query("SELECT DISTINCT (c.country) FROM City c WHERE c.country <> 'Undefined'")
    List<String> findAllCountries();

    @Query("SELECT c.id FROM City c WHERE c.city IN ?1")
    List<Long> findByCities(List<String> names);

    @Query("SELECT v FROM Vacancy v WHERE ?1 MEMBER OF v.city")
    List<Vacancy> findInVacancy(City city);

    @Query("SELECT r FROM Resume r WHERE ?1 MEMBER OF r.city")
    List<Resume> findInResume(City city);

    @Query("SELECT s FROM Seeker s WHERE ?1 MEMBER OF s.city")
    List<Seeker> findInSeeker(City city);

    @Query("SELECT e FROM Employer e WHERE ?1 MEMBER OF e.city")
    List<Employer> findInEmployer(City city);

    @Query("SELECT c FROM City c WHERE c.city = 'Undefined'")
    Set<City> findUndefined();

    @Query("SELECT c FROM City c WHERE " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'city') AND (lower(c.city) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'country') AND (lower(c.country) LIKE CONCAT('%', lower(?2), '%' )))")
    List<City> findAllAllSortFilter(String field, String value, Sort sort);
}