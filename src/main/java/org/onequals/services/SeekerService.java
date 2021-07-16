package org.onequals.services;

import org.onequals.domain.Employer;
import org.onequals.domain.Resume;
import org.onequals.domain.Seeker;
import org.onequals.domain.User;
import org.onequals.repo.SeekerRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Seeker> getAll(){
        return seekerRepo.findAll();
    }

    @Transactional
    public Seeker findByUser(User user){
        return seekerRepo.findByUser(user);
    }

    @Transactional
    public void save(Seeker seeker){
        seekerRepo.save(seeker);
    }

    @Transactional
    public void delete(Long id){
        Seeker seeker = seekerRepo.findById(id).get();
        List<Resume> resumes = resumeService.findByUser(seeker.getUser());
        resumeService.deleteAll(resumes);
        seekerRepo.deleteById(id);
    }

    @Transactional
    public Seeker findById(Long id){
        return seekerRepo.findById(id).get();
    }

    @Transactional
    public List<Seeker> getUnapproved(){
        return seekerRepo.findUnapproved();
    }
}