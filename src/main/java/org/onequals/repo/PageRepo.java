package org.onequals.repo;

import org.onequals.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepo extends JpaRepository<Page, Long> {

    @Query("SELECT p FROM Page p WHERE p.label = ?1")
    List<Page> findAllByLabel(String label);
}