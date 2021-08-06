package org.onequals.services;

import org.onequals.domain.*;
import org.onequals.repo.CareerRepo;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerService {
    private final CareerRepo careerRepo;

    public CareerService(CareerRepo careerRepo) {
        this.careerRepo = careerRepo;
    }

    @Transactional
    public List<Career> findAll(){
        return careerRepo.findAll();
    }

    @Transactional
    public void save(Career career){
        careerRepo.save(career);
    }

    @Transactional
    public void delete(Career career){
        careerRepo.delete(career);
    }

    @Transactional
    public Career findById(Long id) {
        return careerRepo.findById(id).get();
    }

    @Transactional
    public Page<Career> findPaginated(Pageable pageable, List<Career> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Career> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<Career>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}