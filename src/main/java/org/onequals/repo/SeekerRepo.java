package org.onequals.repo;

import org.onequals.domain.Seeker;
import org.onequals.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeekerRepo extends JpaRepository<Seeker, Long> {

    @Query("SELECT s FROM Seeker s WHERE s.user = ?1")
    Seeker findByUser(User user);
}