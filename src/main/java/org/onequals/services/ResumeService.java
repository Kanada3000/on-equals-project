package org.onequals.services;

import org.onequals.domain.Resume;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.repo.ResumeRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
public class ResumeService {
    private final ResumeRepo resumeRepo;

    public ResumeService(ResumeRepo resumeRepo) {
        this.resumeRepo = resumeRepo;
    }

    @Transactional
    public List<Resume> getAll() {
        return resumeRepo.findAll(Sort.by("salary"));
    }

    @Transactional
    public List<Resume> sortAndFilter(String key_words, String catString, String citString, int min,
                                      int max, String sort, List<Long> category, List<Long> type) {
        List<Resume> resumeList = resumeRepo.findAll();
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
            resumeList = resumeRepo.filterByCategoryList(category, resumeList);
        }

        if (type != null) {
            resumeList = resumeRepo.filterByTypeList(type, resumeList);
        }
        resumeList = resumeRepo.sort(min, max, resumeList, Sort.by(sort));
        return resumeList;
    }

    @Transactional
    public void save(Resume resume) {
        resumeRepo.save(resume);
    }

    @Transactional
    public void delete(Long id) {
        resumeRepo.deleteById(id);
    }

    @Transactional
    public List<Resume> findByUser(User user) {
        return resumeRepo.findByUser(user.getId());
    }

    @Transactional
    public HashMap<Object, Object> findMinMax(Integer min, Integer max) {
        HashMap<Object, Object> minMax = new HashMap<Object, Object>();
        List<Resume> resumes = getAll();
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
        return minMax;
    }

}