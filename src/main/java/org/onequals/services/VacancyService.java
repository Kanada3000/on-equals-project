package org.onequals.services;

import org.onequals.domain.Vacancy;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VacancyService {
    private final VacancyRepo vacancyRepo;

    public VacancyService(VacancyRepo vacancyRepo) {
        this.vacancyRepo = vacancyRepo;
    }

    @Transactional
    public List<Vacancy> getAll() {
        return vacancyRepo.findAll();
    }

    @Transactional
    public List<Vacancy> sortAndFilter(int min, int max, String sort, List<Long> category, List<Long> type) {
        if (category == null) {
            if (type == null)
                return vacancyRepo.findNeeded(min, max, Sort.by(sort));
            return vacancyRepo.findNeededType(min, max, type, Sort.by(sort));
        }
        if (type == null)
            return vacancyRepo.findNeededCategory(min, max, category, Sort.by(sort));
        return vacancyRepo.findNeededCategoryType(min, max, category, type, Sort.by(sort));
    }

    @Transactional
    public void save(Vacancy vacancy) {
        vacancyRepo.save(vacancy);
    }

    @Transactional
    public void delete(Long id){
        vacancyRepo.deleteById(id);
    }

    @Transactional
    public HashMap<Object, Object> findMinMax(Integer min, Integer max) {
        HashMap<Object, Object> minMax = new HashMap<Object, Object>();
        List<Vacancy> vacancies = getAll();
        int minSalary = vacancies.get(0).getSalary();
        int maxSalary = vacancies.get(vacancies.size() - 1).getSalary();

        if (min == null) {
            min = minSalary;
        }
        if (max == null) {
            max = maxSalary;
        }

        minMax.put("min", min);
        minMax.put("max", max);
        minMax.put("minSalary", minSalary);
        minMax.put("maxSalary", maxSalary);
        return minMax;
    }

}