package org.onequals.services;

import org.onequals.domain.*;
import org.onequals.repo.EmployerRepo;
import org.onequals.repo.SeekerRepo;
import org.onequals.repo.UserRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final SeekerRepo seekerRepo;
    private final EmployerRepo employerRepo;
    private final VacancyRepo vacancyRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, SeekerRepo seekerRepo, EmployerRepo employerRepo, VacancyRepo vacancyRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.seekerRepo = seekerRepo;
        this.employerRepo = employerRepo;
        this.vacancyRepo = vacancyRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User findUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }

    @Transactional
    public void save(User user) {
        userRepo.save(user);
    }

    @Transactional
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Transactional
    public String setPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepo.findById(id).get();
//        if(user.getUserType() == UserType.SEEKER){
//            Seeker seeker = seekerRepo.findByUser(user);
//            seekerRepo.delete(seeker);
//        }else if(user.getUserType() == UserType.EMPLOYER){
//            Employer employer = employerRepo.findEmployerByUser(user);
//            employerRepo.delete(employer);
//        }
        Vacancy vacancy = vacancyRepo.findByUser(id);
        vacancyRepo.delete(vacancy);
        userRepo.deleteById(id);
    }

    @Transactional
    public String getRandomLink() {
        int len = 50;
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    @Transactional
    public User getByLink(String link){
        return userRepo.findUserByLink(link);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}