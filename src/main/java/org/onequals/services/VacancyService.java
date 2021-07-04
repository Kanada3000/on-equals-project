package org.onequals.services;

import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.repo.UserRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class VacancyService {
    private final VacancyRepo vacancyRepo;
    private final UserRepo userRepo;

    public VacancyService(VacancyRepo vacancyRepo, UserRepo userRepo) {
        this.vacancyRepo = vacancyRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public List<Vacancy> getAll() {
        return vacancyRepo.findAll();
    }

    @Transactional
    public List<Vacancy> sortAndFilter(String key_words, String catString, String citString, int min,
                                       int max, String sort, List<Long> category, List<Long> type) {
        List<Vacancy> vacancyList = vacancyRepo.findAll();
        if (!vacancyList.isEmpty()) {
            if (key_words != null)
                if (!key_words.isEmpty()) {
                    vacancyList = vacancyRepo.filterByKey(key_words, vacancyList);
                }

            if (catString != null)
                if (!catString.isEmpty()) {
                    vacancyList = vacancyRepo.filterByCategory(catString, vacancyList);
                }

            if (citString != null)
                if (!citString.isEmpty()) {
                    vacancyList = vacancyRepo.filterByCity(citString, vacancyList);
                }

            if (category != null) {
                vacancyList = vacancyRepo.filterByCategoryList(category, vacancyList);
            }

            if (type != null) {
                vacancyList = vacancyRepo.filterByTypeList(type, vacancyList);
            }
            vacancyList = vacancyRepo.sort(min, max, vacancyList, Sort.by(sort));
            return vacancyList;
        } else return null;
    }

    @Transactional
    public void save(Vacancy vacancy) {
        vacancyRepo.save(vacancy);
    }

    @Transactional
    public void delete(Long id) {
        List<User> users = vacancyRepo.getUsersByLike(id);
        for (User u : users) {
            Set<Vacancy> set = u.getLikedVacancy();
            set.remove(vacancyRepo.getById(id));
            u.setLikedVacancy(set);
            userRepo.save(u);
        }
        vacancyRepo.deleteById(id);
    }

    @Transactional
    public List<Vacancy> findByUser(User user) {
        return vacancyRepo.findByUser(user.getId());
    }

    @Transactional
    public HashMap<Object, Object> findMinMax(Integer min, Integer max) {
        HashMap<Object, Object> minMax = new HashMap<Object, Object>();
        List<Vacancy> vacancies = vacancyRepo.findAll(Sort.by("salary"));
        if (!vacancies.isEmpty()) {
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
        } else {
            minMax.put("min", 0);
            minMax.put("max", 0);
            minMax.put("minSalary", 0);
            minMax.put("maxSalary", 0);
        }
        return minMax;

    }

}