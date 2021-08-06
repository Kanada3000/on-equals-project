package org.onequals.repo;

import org.onequals.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepo extends JpaRepository<Page, Long> {

    @Query("SELECT p FROM Page p WHERE p.label = ?1")
    List<Page> findAllByLabel(String label);

    @Query("SELECT p.id FROM Page p WHERE p.id = (select max(pStart.id) from Page pStart where pStart.id < ?1)")
    String prevId(Long id);

    @Query(value = "SELECT p.id FROM Page p WHERE p.id = (select min(pStart.id) from Page pStart where pStart.id > ?1)")
    String nextId(Long id);

    @Query(value = "SELECT min(p.id) FROM Page p")
    String minId();

    @Query(value = "SELECT max(p.id) FROM Page p")
    String maxId();
}