package com.fonada.masking.service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.HolidayEntity;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.HolidayRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

/**
 * 
 * @author Rahul
 *
 */
@Service
public class HolidayService {

	public static final Logger logger = LoggerFactory.getLogger(HolidayService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HolidayRepository holidayRepository;

	public String createHolidayDidNumber(HolidayEntity holidayEntity, Integer userId) {
		logger.info("***** Inside HolidayService.createHolidayDidNumber() *****");
		Optional<Users> user = null;
		DataContainer data = new DataContainer();
		HolidayEntity savedHoliday = null;
		try {
			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				logger.info("***** Inside HolidayService.createHolidayDidNumber() Response::" + data.toString());

				return new Gson().toJson(data).toString();
			}
			if (StringUtils.isBlank(holidayEntity.getDidNo()) || Objects.isNull(holidayEntity.getDidNo())) {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg("Did Number Cannot Be Empty.");
				logger.info("***** Inside HolidayService.createHolidayDidNumber() Response::" + data.toString());
				return new Gson().toJson(data).toString();
			}

			holidayEntity.setUsersId(userId);
			holidayEntity.setCreatedBy(user.get().getUsername());
			holidayEntity.setModifyBy(user.get().getUsername());
			holidayEntity.setModifyDate(Utils.convertDateToString(new Date()));
			holidayEntity.setCreatedDate(Utils.convertDateToString(new Date()));
			savedHoliday = holidayRepository.save(holidayEntity);
			if (savedHoliday.getId() != null) {
				data.setData(savedHoliday);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESSADD_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.DATANOTSAVED);
			}

		} catch (Exception e) {
			data.setMsg("Got Exception:: " + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside HolidayService.createHolidayDidNumber() Response::" + data.toString());

			e.printStackTrace();
		}
		logger.info("***** Inside HolidayService.createHolidayDidNumber() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}

	/**
	 * 
	 * @param holidayEntity
	 * @param userId
	 * @return
	 */
	public String updateHolidayNumber(HolidayEntity holidayEntity, Integer userId) {
		logger.info("***** Inside HolidayService.updateHolidayNumber() *****");
		HolidayEntity exitsHoliday = null;
		DataContainer data = new DataContainer();
		Optional<Users> user = null;

		try {
			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				logger.info("*****HolidayService.updateHolidayNumber() *****" + data.toString());
				return new Gson().toJson(data).toString();
			}
			if (StringUtils.isBlank(holidayEntity.getDidNo()) || Objects.isNull(holidayEntity.getDidNo())) {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg("Did Number Cannot Be Empty.");
				logger.info("*****HolidayService.updateHolidayNumber() *****" + data.toString());
				return new Gson().toJson(data).toString();
			}
			exitsHoliday = holidayRepository.findOneById(holidayEntity.getId());
			if (Objects.nonNull(exitsHoliday)) {
				if (StringUtils.isBlank(holidayEntity.getDidNo()) || Objects.isNull(holidayEntity.getDidNo())) {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg("Did Number Cannot Be Empty.");
					logger.info("*****HolidayService.updateHolidayNumber() *****" + data.toString());
					return new Gson().toJson(data).toString();
				}
				if (!exitsHoliday.getDidNo().equals(holidayEntity.getDidNo())) {
					exitsHoliday.setDidNo(holidayEntity.getDidNo());
				}
				if (!exitsHoliday.getHolidayDate().equals(holidayEntity.getHolidayDate())) {
					exitsHoliday.setHolidayDate(holidayEntity.getHolidayDate());
				}
				if (!exitsHoliday.getIsGlobal().equals(holidayEntity.getIsGlobal())) {
					exitsHoliday.setIsGlobal(holidayEntity.getIsGlobal());
				}
				holidayEntity.setUsersId(userId);
				holidayEntity.setModifyBy(user.get().getUsername());
				holidayEntity.setModifyDate(Utils.convertDateToString(new Date()));
				holidayRepository.save(holidayEntity);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.RECORDS_UPDATED);
				data.setData(holidayEntity);
				logger.info("*****HolidayService.updateHolidayNumber() *****" + data.toString());
			} else {
				data.setStatus(Constants.RECORD_NOT_EXISTS);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				logger.info("*****HolidayService.updateHolidayNumber() *****" + data.toString());
			}
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("*****HolidayService.updateHolidayNumber() *****" + data.toString());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

}
