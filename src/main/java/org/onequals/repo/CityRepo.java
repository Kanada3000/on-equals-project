package org.onequals.repo;

import org.onequals.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepo extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.city = ?1")
    City findCity(String city);

    @Query("SELECT c FROM City c WHERE c.country = ?1")
    List<City> findByCountry(String country);

    @Query("SELECT DISTINCT (c.country) FROM City c")
    List<String> findAllCountries();

    @Modifying
    @Query("UPDATE Vacancy v SET v.city = (SELECT c FROM City c WHERE c.id = 1) WHERE v.city.id = ?1")
    void deleteFromVacancy(Long id);

    @Modifying
    @Query("UPDATE Resume r SET r.city = (SELECT c FROM City c WHERE c.id = 1) WHERE r.city.id = ?1")
    void deleteFromResume(Long id);

    @Modifying
    @Query("UPDATE Employer e SET e.city = (SELECT c FROM City c WHERE c.id = 1) WHERE e.city.id = ?1")
    void deleteFromEmployer(Long id);

    @Modifying
    @Query("UPDATE Seeker s SET s.city = (SELECT c FROM City c WHERE c.id = 1) WHERE s.city.id = ?1")
    void deleteFromSeeker(Long id);
}