package org.onequals.services;

import org.onequals.domain.*;
import org.onequals.repo.CityRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CityService {
    private final CityRepo cityRepo;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final SeekerService seekerService;
    private final EmployerService employerService;

    public CityService(CityRepo cityRepo, VacancyService vacancyService, ResumeService resumeService, SeekerService seekerService, EmployerService employerService) {
        this.cityRepo = cityRepo;
        this.vacancyService = vacancyService;
        this.resumeService = resumeService;
        this.seekerService = seekerService;
        this.employerService = employerService;
    }

    @Transactional
    public List<City> getAll() {
        return cityRepo.findAll();
    }

    @Transactional
    public List<City> getAllAll() {
        return cityRepo.findAllAll();
    }

    @Transactional
    public City findByName(String name) {
        return cityRepo.findCity(name);
    }

    @Transactional
    public void save(City city) {
        cityRepo.save(city);
    }

    @Transactional
    public void delete(Long id) {
        City city = cityRepo.findById(id).get();
        List<Vacancy> vacancies = cityRepo.findInVacancy(city);
        List<Resume> resumes = cityRepo.findInResume(city);
        List<Employer> employers = cityRepo.findInEmployer(city);
        List<Seeker> seekers = cityRepo.findInSeeker(city);

        if (!vacancies.isEmpty())
            for (Vacancy v : vacancies) {
                v.getCity().remove(city);
                if (v.getCity().isEmpty()) {
                    v.setCity(cityRepo.findUndefined());
                }
            }
        if (!resumes.isEmpty())
            for (Resume r : resumes) {
                r.getCity().remove(city);
                if (r.getCity().isEmpty()) {
                    r.setCity(cityRepo.findUndefined());
                }
            }
        if (!employers.isEmpty())
            for (Employer e : employers) {
                e.getCity().remove(city);
                if (e.getCity().isEmpty()) {
                    e.setCity(cityRepo.findUndefined());
                }
            }
        if (!seekers.isEmpty())
            for (Seeker s : seekers) {
                s.getCity().remove(city);
                if (s.getCity().isEmpty()) {
                    s.setCity(cityRepo.findUndefined());
                }
            }
        cityRepo.deleteById(id);
    }

    @Transactional
    public List<Long> findByNames(List<String> names) {
        return cityRepo.findByCities(names);
    }

    @Transactional
    public void addCities(Object o, List<Long> id) {
        if (o instanceof Vacancy) {
            Set<City> cities = ((Vacancy) o).getCity();
            Set<City> newCities = new HashSet<>(cityRepo.findAllById(id));
            if (cities != null) {
                cities.addAll(newCities);
            } else {
                cities = newCities;
            }
            ((Vacancy) o).setCity(cities);
            vacancyService.save((Vacancy) o);
        } else if (o instanceof Resume) {
            Set<City> cities = ((Resume) o).getCity();
            Set<City> newCities = new HashSet<>(cityRepo.findAllById(id));
            if (cities != null) {
                cities.addAll(newCities);
            } else {
                cities = newCities;
            }
            ((Resume) o).setCity(cities);
            resumeService.save((Resume) o);
        } else if (o instanceof Employer) {
            Set<City> cities = ((Employer) o).getCity();
            Set<City> newCities = new HashSet<>(cityRepo.findAllById(id));
            if (cities != null) {
                cities.addAll(newCities);
            } else {
                cities = newCities;
            }
            ((Employer) o).setCity(cities);
            employerService.save((Employer) o);
        } else if (o instanceof Seeker) {
            Set<City> cities = ((Seeker) o).getCity();
            Set<City> newCities = new HashSet<>(cityRepo.findAllById(id));
            if (cities != null) {
                cities.addAll(newCities);
            } else {
                cities = newCities;
            }
            ((Seeker) o).setCity(cities);
            seekerService.save((Seeker) o);
        }
    }

    @Transactional
    public List<String> getAllCountry() {
        return cityRepo.findAllCountries();
    }

    @Transactional
    public Page<City> findPaginated(Pageable pageable, List<City> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<City> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<City>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}