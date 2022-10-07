package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.TimeGroupEntity;

@Repository
@Transactional
public interface TimeGroupRepository extends JpaRepository<TimeGroupEntity, Long> {

	TimeGroupEntity findOneById(Long id);

}
