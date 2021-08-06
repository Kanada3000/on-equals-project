package org.onequals.services;

import org.onequals.domain.Category;
import org.onequals.domain.Page;
import org.onequals.repo.PageRepo;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class PageService {
    private final PageRepo pageRepo;

    public PageService(PageRepo pageRepo) {
        this.pageRepo = pageRepo;
    }

    @Transactional
    public List<Page> findAll() {
        return pageRepo.findAll();
    }

    @Transactional
    public Page getById(Long id) {
        return pageRepo.findById(id).get();
    }

    @Transactional
    public void save(Page page) {
        pageRepo.save(page);
    }

    @Transactional
    public List<Page> getByLabel(String label) {
        return pageRepo.findAllByLabel(label);
    }

    @Transactional
    public List<Page> getShort() {
        List<Page> pages = pageRepo.findAll();
        Collections.shuffle(pages);
        if (pages.size() > 5)
            return pages.subList(0, 5);
        else return pages;
    }

    @Transactional
    public String getPrevId(Long id) {
        if (!id.toString().equals(pageRepo.minId()))
            return pageRepo.prevId(id);
        else return pageRepo.maxId();
    }

    @Transactional
    public String getNextId(Long id) {
        if (!id.toString().equals(pageRepo.maxId()))
            return pageRepo.nextId(id);
        else return pageRepo.minId();
    }

    @Transactional
    public void delete(Long id) {
        pageRepo.deleteById(id);
    }

    @Transactional
    public org.springframework.data.domain.Page<Page> findPaginated(Pageable pageable, List<Page> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Page> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<Page>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}