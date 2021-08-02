package org.onequals.repo;

import org.onequals.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepo extends JpaRepository<Story, Long> {
}