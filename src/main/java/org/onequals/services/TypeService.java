package org.onequals.services;

import org.onequals.domain.Resume;
import org.onequals.domain.Type;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.repo.ResumeRepo;
import org.onequals.repo.TypeRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
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
    public List<Type> getAllAll(){
        return typeRepo.findAllAll(Sort.by("id"));
    }

    @Transactional
    public void save(Type type){
        typeRepo.save(type);
    }

    @Transactional
    public void delete(Long id){
        typeRepo.deleteFromResume(id);
        typeRepo.deleteFromVacancy(id);
        typeRepo.deleteById(id);
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

        List<Long> resumesId = resumes.stream()
                .map(Resume::getId)
                .collect(Collectors.toList());

        List<Type> test = typeRepo.countByTypeListResume(resumesId);

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
    public Page<Type> findPaginated(Pageable pageable, List<Type> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Type> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<Type>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}