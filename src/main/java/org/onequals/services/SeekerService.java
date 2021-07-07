package org.onequals.services;

import org.onequals.domain.Employer;
import org.onequals.domain.Seeker;
import org.onequals.domain.User;
import org.onequals.repo.SeekerRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeekerService {
    private final SeekerRepo seekerRepo;

    public SeekerService(SeekerRepo seekerRepo) {
        this.seekerRepo = seekerRepo;
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
        seekerRepo.deleteById(id);
    }

    @Transactional
    public Seeker findById(Long id){
        return seekerRepo.findById(id).get();
    }
}