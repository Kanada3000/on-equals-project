package org.onequals.services;

import org.onequals.domain.Employer;
import org.onequals.domain.Resume;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.repo.UserRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
    public Vacancy getById(Long id) {
        return vacancyRepo.findById(id).get();
    }

    @Transactional
    public List<Vacancy> getAllAll() {
        return vacancyRepo.findAllAll();
    }

    @Transactional
    public List<Vacancy> sortAndFilter(String key_words, String catString, String citString, int min,
                                       int max, String sort, List<Long> category, List<Long> type) {
        List<Vacancy> vacancyList = vacancyRepo.findAll();
        if (!vacancyList.isEmpty()) {
            if (key_words != null)
                if (!key_words.isEmpty()) {
                    key_words = key_words.toLowerCase();
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
                if (!category.isEmpty())
                    vacancyList = vacancyRepo.filterByCategoryList(category, vacancyList);
            }

            if (type != null) {
                if (!type.isEmpty())
                    vacancyList = vacancyRepo.filterByTypeList(type, vacancyList);
            }
            vacancyList = vacancyRepo.sort(min, max, vacancyList, Sort.by(sort));
            return vacancyList;
        } else return null;
    }

    @Transactional
    public Page<Vacancy> findPaginated(Pageable pageable, List<Vacancy> vacancies) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Vacancy> list;

        if (vacancies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, vacancies.size());
            list = vacancies.subList(startItem, toIndex);
        }

        return new PageImpl<Vacancy>(list, PageRequest.of(currentPage, pageSize), vacancies.size());
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
    public void deleteAll(List<Vacancy> vacancies) {
        for (Vacancy v : vacancies) {
            Long id = v.getId();
            List<User> users = vacancyRepo.getUsersByLike(id);
            for (User u : users) {
                Set<Vacancy> set = u.getLikedVacancy();
                set.remove(vacancyRepo.getById(id));
                u.setLikedVacancy(set);
                userRepo.save(u);
            }
            vacancyRepo.deleteById(id);
        }
    }

    @Transactional
    public List<Vacancy> findByUser(User user) {
        return vacancyRepo.findByUser(user.getId());
    }

    @Transactional
    public List<Vacancy> getUnapproved() {
        return vacancyRepo.findUnapproved();
    }

    @Transactional
    public List<Vacancy> getUnapproved(String sort) {
        return vacancyRepo.findUnapproved(Sort.by(sort));
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

    @Transactional
    public List<Vacancy> adminSort(List<Vacancy> vacancy, String sort) {
        return vacancyRepo.sortAdmin(vacancy, Sort.by(sort));
    }

}