package org.onequals.repo;

import org.onequals.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepo extends JpaRepository<Type, Long> {


    @Query("SELECT t FROM Type t WHERE t.name = ?1")
    Type findTypeByName(String name);

    @Query("SELECT new Type(v.type.id, COUNT(v.type)) FROM Vacancy v WHERE v.id IN (?1) GROUP BY v.type")
    List<Type> countByTypeList(List<Long> id);

    @Modifying
    @Query("UPDATE Resume r SET r.type = (SELECT t FROM Type t WHERE t.id = 1) WHERE r.type.id = ?1")
    void deleteFromResume(Long id);

    @Modifying
    @Query("UPDATE Vacancy v SET v.type = (SELECT t FROM Type t WHERE t.id = 1) WHERE v.type.id = ?1")
    void deleteFromVacancy(Long id);
}