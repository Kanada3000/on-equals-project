package org.onequals.services;

import org.onequals.domain.Category;
import org.onequals.domain.Story;
import org.onequals.repo.StoryRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class StoryService {
    private final StoryRepo storyRepo;

    public StoryService(StoryRepo storyRepo) {
        this.storyRepo = storyRepo;
    }

    @Transactional
    public List<Story> getAll(){
        return storyRepo.findAll(Sort.by("createdDate"));
    }

    @Transactional
    public Story getById(Long id){
        return storyRepo.findById(id).get();
    }

    @Transactional
    public void save(Story story){
        storyRepo.save(story);
    }

    @Transactional
    public void delete(Long id){
        storyRepo.deleteById(id);
    }

    @Transactional
    public Page<Story> findPaginated(Pageable pageable, List<Story> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Story> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<Story>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}