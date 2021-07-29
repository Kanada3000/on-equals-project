package org.onequals.services;

import org.onequals.domain.Category;
import org.onequals.domain.Employer;
import org.onequals.domain.Resume;
import org.onequals.domain.Vacancy;
import org.onequals.repo.CategoryRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService{
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public List<Category> updateTotal(List<Vacancy> vacancies){
        List<Category> categories = categoryRepo.findAll(Sort.by("id"));

        List<Long> vacanciesId = vacancies.stream()
                .map(Vacancy::getId)
                .collect(Collectors.toList());

        List<Category> test = categoryRepo.countByCategoryList(vacanciesId);

        for (Category value : categories) {
            value.setTotal(0);
            for (Category category : test) {
                if (value.getId().equals(category.getId())) {
                    value.setTotal(category.getTotal());
                }
            }
        }
        return categories;
    }

    @Transactional
    public List<Category> updateTotalResumes(List<Resume> resumes){
        List<Category> categories = categoryRepo.findAll(Sort.by("id"));

        List<Long> resumesId = resumes.stream()
                .map(Resume::getId)
                .collect(Collectors.toList());

        List<Category> test = categoryRepo.countByCategoryListResume(resumesId);

        for (Category value : categories) {
            value.setTotal(0);
            for (Category category : test) {
                if (value.getId().equals(category.getId())) {
                    value.setTotal(category.getTotal());
                }
            }
        }

        return categories;
    }

    @Transactional
    public List<Category> getAll(){
        return categoryRepo.findAll();
    }

    @Transactional
    public List<Category> getAllAll(){
        return categoryRepo.findAllAll();
    }

    @Transactional
    public Category findByName(String name){
        return categoryRepo.findCategoryByLong(name);
    }

    @Transactional
    public Category findById(Long id){
        return categoryRepo.findById(id).get();
    }

    @Transactional
    public void save(Category category){
        categoryRepo.save(category);
    }

    @Transactional
    public void delete(Long id){
        categoryRepo.deleteFromVacancy(id);
        categoryRepo.deleteFromResume(id);
        categoryRepo.deleteFromEmployer(id);
        categoryRepo.deleteFromSeeker(id);
        categoryRepo.deleteById(id);
    }

    @Transactional
    public Page<Category> findPaginated(Pageable pageable, List<Category> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Category> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<Category>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}