package org.onequals.repo;

import org.onequals.domain.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepo extends JpaRepository<Type, Long> {

    @Query("SELECT t FROM Type t WHERE t.name <> 'Undefined'")
    List<Type> findAll();

    @Query("SELECT t FROM Type t WHERE t.name <> 'Undefined'")
    List<Type> findAll(Sort sort);

    @Query("SELECT t FROM Type t")
    List<Type> findAllAll();

    @Query("SELECT t FROM Type t")
    List<Type> findAllAll(Sort sort);

    @Query("SELECT t FROM Type t WHERE t.name = ?1")
    Type findTypeByName(String name);

    @Query("SELECT new Type(v.type.id, COUNT(v.type)) FROM Vacancy v WHERE v.id IN (?1) GROUP BY v.type")
    List<Type> countByTypeList(List<Long> id);

    @Query("SELECT new Type(r.type.id, COUNT(r.type)) FROM Resume r WHERE r.id IN (?1) GROUP BY r.type")
    List<Type> countByTypeListResume(List<Long> id);

    @Modifying
    @Query("UPDATE Resume r SET r.type = (SELECT t FROM Type t WHERE t.name = 'Undefined') WHERE r.type.id = ?1")
    void deleteFromResume(Long id);

    @Modifying
    @Query("UPDATE Vacancy v SET v.type = (SELECT t FROM Type t WHERE t.name = 'Undefined') WHERE v.type.id = ?1")
    void deleteFromVacancy(Long id);

    @Query("SELECT c FROM Type c WHERE " +
            "((?1 LIKE 'id') AND (CONCAT(c.id, '') = ?2)) OR " +
            "((?1 LIKE 'name') AND (lower(c.name) LIKE CONCAT('%', lower(?2), '%' ) ))")
    List<Type> findAllAllSortFilter(String field, String value, Sort sort);
}