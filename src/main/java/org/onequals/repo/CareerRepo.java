package org.onequals.repo;

import org.onequals.domain.Career;
import org.onequals.domain.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CareerRepo extends JpaRepository<Career, Long> {

}