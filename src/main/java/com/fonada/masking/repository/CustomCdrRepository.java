package com.fonada.masking.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fonada.masking.entity.CustomCdr;

@Repository
public interface CustomCdrRepository extends JpaRepository<CustomCdr, Integer> {

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr where calldate>=:start_date and calldate<=:end_date and disposition=:disposition and clid=:clid", nativeQuery = true)
	public List<CustomCdr> findAllAnsweredCustomCdr(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("disposition") String disposition, @Param("clid") String clid,
			PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr where calldate>=:start_date and calldate<=:end_date and disposition=:disposition", nativeQuery = true)
	public List<CustomCdr> findAllAnsweredCustomCdrWithoutClidNumber(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("disposition") String disposition, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr where calldate>=:start_date and calldate<=:end_date and clid=:clid", nativeQuery = true)
	public List<CustomCdr> findAllAnsweredCustomCdrWithoutDispositon(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("clid") String clid, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr where calldate>=:start_date and calldate<=:end_date", nativeQuery = true)
	public List<CustomCdr> findAllAnsweredCustomCdrWithStartAndEndDate(@Param("start_date") String start_date,
			@Param("end_date") String end_date, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr where calldate like %:start_date%", nativeQuery = true)
	public List<CustomCdr> findAllAnsweredCustomCdrWithStartDate(@Param("start_date") String start_date,
			PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr where calldate>=:start_date and calldate<=:end_date", nativeQuery = true)
	public List<CustomCdr> findCustomCdrWithCurrentStartEndDate(@Param("start_date") String start_date,
			@Param("end_date") String end_date, PageRequest page);

	/*************************************************************
	 * start For Recording Management
	 **********************/
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "SELECT calldate, clid,src, COUNT(clid),FLOOR(DATEDIFF(:endDate,:startDate)/7) as weekCount,FLOOR(sum(billsec)) FROM custom_cdr  where calldate>=:startDate and calldate<=:endDate and clid=:clid or src=:clid or dst=:clid GROUP BY clid", nativeQuery = true)
	List<Object[]> findAllByStartAndEndDateAndClidForRecordingManagement(@Param("startDate") String startDate,
			@Param("endDate") String endDate, @Param("clid") String clid, PageRequest page);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "SELECT calldate, clid,src, COUNT(clid),FLOOR(DATEDIFF(:endDate,:startDate)/7) as weekCount,FLOOR(sum(billsec)) FROM custom_cdr  where calldate>=:startDate and calldate<=:endDate GROUP BY clid", nativeQuery = true)
	List<Object[]> getCliCountByStartAndEndDate(@Param("startDate") String startDate, @Param("endDate") String endDate,
			PageRequest page);
	// for Src Count Query

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "SELECT src, COUNT(src), FLOOR(DATEDIFF(:endDate,:startDate)/7),FLOOR(sum(billsec)) FROM custom_cdr  where calldate>=:startDate and calldate<=:endDate and src=:clid GROUP BY src", nativeQuery = true)
	List<Object[]> getCountSrcNumberByCliNumber(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("clid") String clid, PageRequest page);

	/*************************************************************
	 * End For Recording Management
	 **********************/

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr c where  calldate>=:start_date and calldate<=:end_date and src=:src", nativeQuery = true)
	public List<CustomCdr> findAllCustomCdrBySourceNumber(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("src") String src);

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Query(value = "select * from custom_cdr c where  calldate>=:start_date and calldate<=:end_date and src=:src", nativeQuery = true)
	public List<CustomCdr> findAllCustomCdrByCliNumber(@Param("start_date") String start_date,
			@Param("end_date") String end_date, @Param("src") String src);
}
