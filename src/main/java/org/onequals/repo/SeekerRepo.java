package org.onequals.repo;

import org.onequals.domain.Employer;
import org.onequals.domain.Seeker;
import org.onequals.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeekerRepo extends JpaRepository<Seeker, Long> {

    @Query("SELECT s FROM Seeker s WHERE s.user = ?1")
    Seeker findByUser(User user);

    @Query("SELECT s FROM Seeker s WHERE s.approved = false")
    List<Seeker> findUnapproved();

    @Query("SELECT s FROM Seeker s WHERE s.approved = false")
    List<Seeker> findUnapproved(Sort sort);

    @Query("SELECT c FROM Seeker c join c.city cit WHERE " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'user') AND (lower(c.user.username) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'name') AND (lower(c.name) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'email') AND (lower(c.email) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'category') AND (lower(c.category.longName) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'city') AND (lower(cit.city) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'site') AND (lower(c.site) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'facebook') AND (lower(c.linkFacebook) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'instagram') AND (lower(c.linkInstagram) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'linkedIn') AND (lower(c.linkLinkedIn) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'twitter') AND (lower(c.linkTwitter) LIKE CONCAT('%', lower(?2), '%' ))) OR " +
            "((?1 LIKE 'description') AND (lower(c.description) LIKE CONCAT('%', lower(?2), '%' )))")
    List<Seeker> findAll(String field, String value, Sort sort);
}