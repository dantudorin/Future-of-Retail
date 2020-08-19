package com.infosys.admin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.infosys.admin.model.Store;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {

    Optional<Store> findByName(String name);
    Optional<Store> findById(Long Id);
}