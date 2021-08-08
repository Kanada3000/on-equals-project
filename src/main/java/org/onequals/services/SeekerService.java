package org.onequals.services;

import org.onequals.domain.*;
import org.onequals.repo.SeekerRepo;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class SeekerService {
    private final SeekerRepo seekerRepo;
    private final ResumeService resumeService;

    public SeekerService(SeekerRepo seekerRepo, ResumeService resumeService) {
        this.seekerRepo = seekerRepo;
        this.resumeService = resumeService;
    }

    @Transactional
    public List<Seeker> getAll() {
        return seekerRepo.findAll();
    }

    @Transactional
    public List<Seeker> getAll(String sort) {
        return seekerRepo.findAll(Sort.by(sort));
    }

    @Transactional
    public Seeker findByUser(User user) {
        return seekerRepo.findByUser(user);
    }

    @Transactional
    public void save(Seeker seeker) {
        seekerRepo.save(seeker);
    }

    @Transactional
    public void delete(Long id) {
        Seeker seeker = seekerRepo.findById(id).get();
        List<Resume> resumes = resumeService.findByUser(seeker.getUser());
        resumeService.deleteAll(resumes);
        seekerRepo.deleteById(id);
    }

    @Transactional
    public Seeker findById(Long id) {
        return seekerRepo.findById(id).get();
    }

    @Transactional
    public List<Seeker> getUnapproved() {
        return seekerRepo.findUnapproved();
    }

    @Transactional
    public List<Seeker> getUnapproved(String sort) {
        return seekerRepo.findUnapproved(Sort.by(sort));
    }

    @Transactional
    public Page<Seeker> findPaginated(Pageable pageable, List<Seeker> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Seeker> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<Seeker>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}