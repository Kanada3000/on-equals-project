package org.onequals.repo;

import org.onequals.domain.Category;
import org.onequals.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepo extends JpaRepository<Type, Long> {


    @Query("SELECT t FROM Type t WHERE t.name = ?1")
    Type findTypeByName(String name);

    @Query("SELECT new Type(v.type.id, COUNT(v.type)) FROM Vacancy v WHERE v.id IN (?1) GROUP BY v.type")
    List<Type> countByTypeList(List<Long> id);
}