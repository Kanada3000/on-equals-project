package org.onequals.services;

import org.onequals.domain.Category;
import org.onequals.domain.Employer;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.repo.CategoryRepo;
import org.onequals.repo.EmployerRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployerService {
    private final EmployerRepo employerRepo;

    public EmployerService(EmployerRepo employerRepo) {
        this.employerRepo = employerRepo;
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
        employerRepo.deleteById(id);
    }
}