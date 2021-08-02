package org.onequals.services;

import org.onequals.domain.Sticker;
import org.onequals.domain.Story;
import org.onequals.repo.StickerRepo;
import org.onequals.repo.StoryRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StickerService {
    private final StickerRepo stickerRepo;

    public StickerService(StickerRepo stickerRepo) {
        this.stickerRepo = stickerRepo;
    }

    @Transactional
    public List<Sticker> getAll(){
        return stickerRepo.findAll(Sort.by("createdDate"));
    }

    @Transactional
    public Sticker getById(Long id){
        return stickerRepo.findById(id).get();
    }

    @Transactional
    public void save(Sticker story){
        stickerRepo.save(story);
    }

    @Transactional
    public void delete(Long id){
        stickerRepo.deleteById(id);
    }
}