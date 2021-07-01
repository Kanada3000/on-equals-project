package org.onequals.services;

import org.onequals.domain.Category;
import org.onequals.domain.Resume;
import org.onequals.domain.Type;
import org.onequals.domain.Vacancy;
import org.onequals.repo.TypeRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {
    private final TypeRepo typeRepo;

    public TypeService(TypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }


    @Transactional
    public Type findByName(String name){
        return typeRepo.findTypeByName(name);
    }

    @Transactional
    public List<Type> getAll(){
        return typeRepo.findAll(Sort.by("id"));
    }

    @Transactional
    public List<Type> updateTotal(List<Vacancy> vacancies){
        List<Type> types = typeRepo.findAll(Sort.by("id"));

        List<Long> vacanciesId = vacancies.stream()
                .map(Vacancy::getId)
                .collect(Collectors.toList());

        List<Type> test = typeRepo.countByTypeList(vacanciesId);

        for (Type value : types) {
            value.setTotal(0);
            for (Type type : test) {
                if (value.getId().equals(type.getId())) {
                    value.setTotal(type.getTotal());
                }
            }
        }
        return types;
    }

    @Transactional
    public List<Type> updateTotalResumes(List<Resume> resumes){
        List<Type> types = typeRepo.findAll(Sort.by("id"));

        List<Long> vacanciesId = resumes.stream()
                .map(Resume::getId)
                .collect(Collectors.toList());

        List<Type> test = typeRepo.countByTypeList(vacanciesId);

        for (Type value : types) {
            value.setTotal(0);
            for (Type type : test) {
                if (value.getId().equals(type.getId())) {
                    value.setTotal(type.getTotal());
                }
            }
        }
        return types;
    }
}