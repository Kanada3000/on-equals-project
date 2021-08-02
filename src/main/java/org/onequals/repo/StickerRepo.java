package org.onequals.repo;

import org.onequals.domain.Sticker;
import org.onequals.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepo extends JpaRepository<Sticker, Long> {
}