package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.ReasonEntity;

@Repository
@Transactional
public interface ReasonRepository extends JpaRepository<ReasonEntity, Integer> {

}
