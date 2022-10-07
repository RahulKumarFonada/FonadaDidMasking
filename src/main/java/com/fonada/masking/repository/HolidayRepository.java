package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.HolidayEntity;

@Repository
@Transactional
public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {

	HolidayEntity findOneById(Long id);

}
