package org.onequals.repo;

import org.onequals.domain.Category;
import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.longName = ?1")
    Category findCategoryByLong(String l);
}