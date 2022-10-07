package com.fonada.masking.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fonada.masking.entity.UnAnsweredCdr;

@Repository
public interface UnAnsweredCdrRepository extends JpaRepository<UnAnsweredCdr, Long> {

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from unans_cdr where start like %:start_date% and end like %:end_date% and clid=:clid", nativeQuery = true)
	public List<UnAnsweredCdr> findAllUnAnsweredCustomCdrReport(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("clid") String clid, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from unans_cdr where start like %:start_date% and end like %:end_date%", nativeQuery = true)
	public List<UnAnsweredCdr> findAllUnAnsweredCustomCdrWithOutCLID(@Param("start_date") String start_date,
			@Param("end_date") String end_date, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from unans_cdr where start like %:start_date% or end like %:end_date% and clid=:clid", nativeQuery = true)
	public List<UnAnsweredCdr> findAllUnAnsweredCustomCdrStartOrEndDateIsNull(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("clid") String clid, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from unans_cdr where start like %:start_date% and end like %:end_date%", nativeQuery = true)
	public List<UnAnsweredCdr> findAllUnAnsweredCustomCdrByStartAndEndCurrentDate(
			@Param("start_date") String start_date, @Param("end_date") String end_date, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from unans_cdr where start like %:start_date%", nativeQuery = true)
	public List<UnAnsweredCdr> findAllUnAnsweredCustomCdrByStartDate(@Param("start_date") String start_date,
			PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from unans_cdr where end like %:end%", nativeQuery = true)
	public List<UnAnsweredCdr> findAllUnAnsweredCustomCdrByEndDate(@Param("end") String end, PageRequest page);

}
