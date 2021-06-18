package org.onequals.repo;

import org.onequals.domain.Category;
import org.onequals.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TypeRepo extends JpaRepository<Type, Long> {


    @Query("SELECT t FROM Type t WHERE t.name = ?1")
    Type findTypeByName(String name);
}