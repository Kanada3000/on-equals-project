package org.onequals.repo;

import org.onequals.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepo extends JpaRepository<Career, Long> {

}