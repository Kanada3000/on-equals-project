package org.onequals.services;

import org.onequals.domain.Page;
import org.onequals.repo.PageRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PageService {
    private final PageRepo pageRepo;

    public PageService(PageRepo pageRepo) {
        this.pageRepo = pageRepo;
    }

    @Transactional
    public Page getById(Long id){
        return pageRepo.findById(id).get();
    }

    @Transactional
    public void save(Page page){
        pageRepo.save(page);
    }

    @Transactional
    public List<Page> getByLabel(String label){
        return pageRepo.findAllByLabel(label);
    }

    @Transactional
    public void delete(Long id){
        pageRepo.deleteById(id);
    }
}