package org.onequals.repo;

import org.onequals.domain.Employer;
import org.onequals.domain.Resume;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ResumeRepo extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r WHERE r.approved = true AND r.user.hidden <> true")
    List<Resume> findAll();

    @Query("SELECT r FROM Resume r WHERE r.approved = true AND r.user.hidden <> true")
    List<Resume> findAll(Sort sort);

    @Query("SELECT r FROM Resume r WHERE r.approved = true")
    List<Resume> findAllAll();

    @Query("SELECT r FROM Resume r WHERE r.approved = true")
    List<Resume> findAllAll(Sort sort);

    @Query("SELECT r FROM Resume r WHERE r.user.id = ?1")
    List<Resume> findByUser(Long id);

//    @Query("SELECT r FROM Resume r WHERE (" +
//            "(SELECT lower(c.city) FROM City c WHERE c MEMBER OF r.city) LIKE %?1% OR " +
//            "lower(r.category.longName) LIKE %?1% OR " +
//            "lower(CONCAT(r.salary, '')) LIKE %?1% OR " +
//            "lower(r.description) LIKE %?1% OR " +
//            "lower(r.type.name) LIKE %?1% OR " +
//            "lower(r.user.name) LIKE %?1%) AND r IN ?2")
//    List<Resume> filterByKey(String key, List<Resume> resumes);

    @Query("SELECT r FROM Resume r WHERE lower(CONCAT(" +
            "' ', r.category.longName, " +
            "' ', r.salary, " +
            "' ', r.description, " +
            "' ', r.type.name, " +
            "' ', r.user.name)) LIKE CONCAT('% ', ?1, ' %' )  AND r IN ?2")
    List<Resume> filterByKey(String key, List<Resume> resumes);

    @Query("SELECT r FROM Resume r WHERE r.category.longName = ?1 AND r IN ?2")
    List<Resume> filterByCategory(String category, List<Resume> resumes);

    @Query("SELECT r FROM Resume r WHERE r.category.id IN ?1 AND r IN ?2")
    List<Resume> filterByCategoryList(List<Long> category, List<Resume> resumes);

    @Query("SELECT r FROM Resume r WHERE r.type.id IN ?1 AND r IN ?2")
    List<Resume> filterByTypeList(List<Long> type, List<Resume> resumes);

    @Query("SELECT r FROM Resume r WHERE ?1 IN (SELECT c.city FROM City c WHERE c MEMBER OF r.city) AND r IN ?2")
    List<Resume> filterByCity(String city, List<Resume> resumes);

    @Query("SELECT r FROM Resume r WHERE (r.salary BETWEEN ?1 and ?2) AND r IN ?3")
    List<Resume> sort(int min, int max, List<Resume> resumes, Sort sort);

    @Query("SELECT r FROM Resume r WHERE CONCAT(r.id,'') IN ?1")
    Set<Resume> findByIdString (List<String> likes);

    @Query("SELECT u FROM User u WHERE (SELECT r FROM Resume r WHERE r.id = ?1) MEMBER OF u.likedResume")
    List<User> getUsersByLike (Long id);

    @Query("SELECT r FROM Resume r WHERE r.approved = false")
    List<Resume> findUnapproved();
}