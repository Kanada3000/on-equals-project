package org.onequals.services;

import org.onequals.domain.*;
import org.onequals.repo.ResumeRepo;
import org.onequals.repo.UserRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final VacancyRepo vacancyRepo;
    private final PasswordEncoder passwordEncoder;
    private final ResumeRepo resumeRepo;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final SeekerService seekerService;
    private final EmployerService employerService;
    private final MailSender mailSender;

    public UserService(UserRepo userRepo, VacancyRepo vacancyRepo, PasswordEncoder passwordEncoder, ResumeRepo resumeRepo, VacancyService vacancyService, ResumeService resumeService, SeekerService seekerService, EmployerService employerService, MailSender mailSender) {
        this.userRepo = userRepo;
        this.vacancyRepo = vacancyRepo;
        this.passwordEncoder = passwordEncoder;
        this.resumeRepo = resumeRepo;
        this.vacancyService = vacancyService;
        this.resumeService = resumeService;
        this.seekerService = seekerService;
        this.employerService = employerService;
        this.mailSender = mailSender;
    }

    @Transactional
    public void sendEmail(User user, String title, String msg){
        mailSender.send(user.getUsername(), title, msg);
    }

    @Transactional
    public User findUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Transactional
    public void save(User user) {
        userRepo.save(user);
    }

    @Transactional
    public List<User> getAll() {
        return userRepo.findAllUsers(Collections.singleton(Role.ADMIN));
    }

    @Transactional
    public String setPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepo.findById(id).get();
        if (user.getRoles().contains(Role.EMPLOYER)) {
            List<Vacancy> vacancies = vacancyRepo.findByUser(id);
            for (Vacancy v : vacancies) {
                vacancyService.delete(v.getId());
            }
            employerService.delete(employerService.findByUser(user).getId());
        } else if (user.getRoles().contains(Role.SEEKER)) {
            List<Resume> resumes = resumeRepo.findByUser(id);
            for (Resume r : resumes) {
                resumeService.delete(r.getId());
            }
            seekerService.delete(seekerService.findByUser(user).getId());
        }
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
    public User getByLink(String link) {
        return userRepo.findUserByLink(link);
    }

    @Transactional
    public void addResumeLikes(User user, List<String> likes) {
        Set<Resume> newLikes = resumeRepo.findByIdString(likes);
        Set<Resume> oldLikes = user.getLikedResume();
        oldLikes.addAll(newLikes);
        user.setLikedResume(oldLikes);
        save(user);
    }

    @Transactional
    public void deleteResumeLikes(User user, List<String> dislikes) {
        Set<Resume> newLikes = resumeRepo.findByIdString(dislikes);
        Set<Resume> oldLikes = user.getLikedResume();
        oldLikes.removeAll(newLikes);
        user.setLikedResume(oldLikes);
        save(user);
    }

    @Transactional
    public void addVacancyLikes(User user, List<String> likes) {
        Set<Vacancy> newLikes = vacancyRepo.findByIdString(likes);
        Set<Vacancy> oldLikes = user.getLikedVacancy();
        oldLikes.addAll(newLikes);
        user.setLikedVacancy(oldLikes);
        save(user);
    }

    @Transactional
    public void deleteVacancyLikes(User user, List<String> dislikes) {
        Set<Vacancy> newLikes = vacancyRepo.findByIdString(dislikes);
        Set<Vacancy> oldLikes = user.getLikedVacancy();
        oldLikes.removeAll(newLikes);
        user.setLikedVacancy(oldLikes);
        save(user);
    }

    @Transactional
    public void activatedAdmin() {
        User user = userRepo.findByUsername("adminLog");
        user.setActivated(true);
        save(user);
    }

    @Transactional
    public void updateRole(Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(role);

        if (role == Role.ADMIN) {
            updatedAuthorities.remove(Role.USER);
        }

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Transactional
    public void auth(User user) {
        Set<Role> privileges = user.getRoles();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, privileges);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional
    public String getEmail(User user) {
        if (user.getRoles().contains(Role.EMPLOYER)) {
            return userRepo.findEmailEmp(user);
        } else return userRepo.findEmailSeek(user);

    }

    @Transactional
    public List<User> getAllUser(String role) {
        if (role.equals("user"))
            return userRepo.findAllByRoleUser(Collections.singleton(Role.USER), 1);
        else if (role.equals("employer"))
            return userRepo.findAllByRoleUser(Collections.singleton(Role.EMPLOYER), 2);
        else if (role.equals("seeker"))
            return userRepo.findAllByRoleUser(Collections.singleton(Role.SEEKER), 2);
        else if (role.equals("userseeker")){
            List<User> list1 = userRepo.findAllByRoleUser(Collections.singleton(Role.SEEKER), 2);
            List<User> list2 = userRepo.findAllByRoleUser(Collections.singleton(Role.USER), 1);
            list1.addAll(list2);
            return list1;
        } else {
            List<User> list1 = userRepo.findAllByRoleUser(Collections.singleton(Role.EMPLOYER), 2);
            List<User> list2 = userRepo.findAllByRoleUser(Collections.singleton(Role.USER), 1);
            list1.addAll(list2);
            return list1;
        }

    }

    @Transactional
    public void addRole(User user, Role role) {
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        save(user);
    }

    @Transactional
    public void removeRole(User user, Role role) {
        Set<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        save(user);
    }

    @Transactional
    public void clearSession() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Transactional
    public String randomPassword() {
        int len = 40;
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    @Transactional
    public Page<User> findPaginated(Pageable pageable, List<User> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}