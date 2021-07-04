package org.onequals.services;

import org.onequals.domain.City;
import org.onequals.repo.CityRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepo cityRepo;

    public CityService(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Transactional
    public List<City> getAll(){
        return cityRepo.findAll();
    }

    @Transactional
    public City findByName(String name){
        return cityRepo.findCity(name);
    }

    @Transactional
    public void save(City city){
        cityRepo.save(city);
    }

    @Transactional
    public void delete(Long id){
        cityRepo.deleteFromVacancy(id);
        cityRepo.deleteFromResume(id);
        cityRepo.deleteFromEmployer(id);
        cityRepo.deleteFromSeeker(id);
        cityRepo.deleteById(id);
    }

    @Transactional
    public List<String> getAllCountry(){
        return cityRepo.findAllCountries();
    }
}