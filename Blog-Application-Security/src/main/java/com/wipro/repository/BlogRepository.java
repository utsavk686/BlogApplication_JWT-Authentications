package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.BlogEntity;


@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {

}
