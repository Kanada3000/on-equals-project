package org.onequals.services;

import org.onequals.domain.Employer;
import org.onequals.domain.Resume;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.repo.ResumeRepo;
import org.onequals.repo.UserRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class ResumeService {
    private final ResumeRepo resumeRepo;
    private final UserRepo userRepo;

    public ResumeService(ResumeRepo resumeRepo, UserRepo userRepo) {
        this.resumeRepo = resumeRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public List<Resume> getAll() {
        return resumeRepo.findAll(Sort.by("salary"));
    }

    @Transactional
    public List<Resume> getAllAll() {
        return resumeRepo.findAllAll(Sort.by("salary"));
    }

    @Transactional
    public List<Resume> getAllAll(String sort, String field, String search) {
        if(search != null){
            if (!search.isEmpty()){
                return resumeRepo.findAllAll(field, search, Sort.by(sort));
            }
        }
        return resumeRepo.findAllAll(Sort.by(sort));
    }

    @Transactional
    public Resume getById(Long id) {
        return resumeRepo.findById(id).get();
    }

    @Transactional
    public List<Resume> sortAndFilter(String key_words, String catString, String citString, int min,
                                      int max, String sort, List<Long> category, List<Long> type) {
        List<Resume> resumeList = resumeRepo.findAll();
        if (!resumeList.isEmpty()) {
            if (key_words != null)
                if (!key_words.isEmpty()) {
                    resumeList = resumeRepo.filterByKey(key_words, resumeList);
                }

            if (catString != null)
                if (!catString.isEmpty()) {
                    resumeList = resumeRepo.filterByCategory(catString, resumeList);
                }

            if (citString != null)
                if (!citString.isEmpty()) {
                    resumeList = resumeRepo.filterByCity(citString, resumeList);
                }

            if (category != null) {
                if (!category.isEmpty())
                    resumeList = resumeRepo.filterByCategoryList(category, resumeList);
            }

            if (type != null) {
                if (!type.isEmpty())
                    resumeList = resumeRepo.filterByTypeList(type, resumeList);
            }
            resumeList = resumeRepo.sort(min, max, resumeList, Sort.by(sort));
            return resumeList;
        } else return null;
    }

    @Transactional
    public Page<Resume> findPaginated(Pageable pageable, List<Resume> resumes) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Resume> list;

        if (resumes.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, resumes.size());
            list = resumes.subList(startItem, toIndex);
        }

        return new PageImpl<Resume>(list, PageRequest.of(currentPage, pageSize), resumes.size());
    }

    @Transactional
    public void save(Resume resume) {
        resumeRepo.save(resume);
    }

    @Transactional
    public void delete(Long id) {
        List<User> users = resumeRepo.getUsersByLike(id);
        for (User u : users) {
            Set<Resume> set = u.getLikedResume();
            set.remove(resumeRepo.getById(id));
            u.setLikedResume(set);
            userRepo.save(u);
        }
        resumeRepo.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Resume> resumes) {
        for (Resume r : resumes) {
            Long id = r.getId();
            List<User> users = resumeRepo.getUsersByLike(id);
            for (User u : users) {
                Set<Resume> set = u.getLikedResume();
                set.remove(resumeRepo.getById(id));
                u.setLikedResume(set);
                userRepo.save(u);
            }
            resumeRepo.deleteById(id);
        }
    }

    @Transactional
    public List<Resume> findByUser(User user) {
        return resumeRepo.findByUser(user.getId());
    }

    @Transactional
    public List<Resume> getUnapproved() {
        return resumeRepo.findUnapproved();
    }

    @Transactional
    public List<Resume> getUnapproved(String sort, String field, String search) {
        if(search != null){
            if (!search.isEmpty()){
                return resumeRepo.findUnapproved(field, search, Sort.by(sort));
            }
        }
        return resumeRepo.findUnapproved(Sort.by(sort));
    }


    @Transactional
    public HashMap<Object, Object> findMinMax(Integer min, Integer max) {
        HashMap<Object, Object> minMax = new HashMap<Object, Object>();
        List<Resume> resumes = resumeRepo.findAll(Sort.by("salary"));
        if (!resumes.isEmpty()) {
            int minSalary = resumes.get(0).getSalary();
            int maxSalary = resumes.get(resumes.size() - 1).getSalary();

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