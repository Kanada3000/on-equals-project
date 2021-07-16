package org.onequals.services;

import org.onequals.domain.*;
import org.onequals.repo.EmployerRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployerService {
    private final EmployerRepo employerRepo;
    private final VacancyService vacancyService;

    public EmployerService(EmployerRepo employerRepo, VacancyService vacancyService) {
        this.employerRepo = employerRepo;
        this.vacancyService = vacancyService;
    }

    @Transactional
    public List<Employer> getAll(){
        return employerRepo.findAll();
    }

    @Transactional
    public Employer findByUser(User user){
        return employerRepo.findEmployerByUser(user);
    }

    @Transactional
    public void save(Employer employer){
        employerRepo.save(employer);
    }

    @Transactional
    public void delete(Long id){
        Employer employer = employerRepo.findById(id).get();
        List<Vacancy> vacancies = vacancyService.findByUser(employer.getUser());
        vacancyService.deleteAll(vacancies);
        employerRepo.deleteById(id);
    }

    @Transactional
    public Employer findById(Long id){
        return employerRepo.findById(id).get();
    }

    @Transactional
    public List<Employer> getUnapproved(){
        return employerRepo.findUnapproved();
    }
}