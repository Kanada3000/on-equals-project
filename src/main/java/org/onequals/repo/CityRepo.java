package org.onequals.repo;

import org.onequals.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepo extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.city = ?1")
    City findCity(String city);

    @Query("SELECT c FROM City c WHERE c.country = ?1")
    List<City> findByCountry(String country);

    @Query("SELECT DISTINCT (c.country) FROM City c")
    List<String> findAllCountries();
}