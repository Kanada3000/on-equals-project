package org.onequals.services;

import org.onequals.domain.Resume;
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
    public List<Resume> sortAndFilter(int min, int max, String sort, List<Long> category, List<Long> type) {
        if (category == null) {
            if (type == null)
                return resumeRepo.findNeeded(min, max, Sort.by(sort));
            return resumeRepo.findNeededType(min, max, type, Sort.by(sort));
        }
        if (type == null)
            return resumeRepo.findNeededCategory(min, max, category, Sort.by(sort));
        return resumeRepo.findNeededCategoryType(min, max, category, type, Sort.by(sort));
    }

    @Transactional
    public void save(Resume resume) {
        resumeRepo.save(resume);
    }

    @Transactional
    public void delete(Long id){
        resumeRepo.deleteById(id);
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