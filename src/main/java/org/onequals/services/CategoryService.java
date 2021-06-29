package org.onequals.services;

import org.onequals.domain.Category;
import org.onequals.domain.Vacancy;
import org.onequals.repo.CategoryRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Category> getAll(){
        return categoryRepo.findAll();
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
        categoryRepo.deleteById(id);
    }
}