package com.fonada.masking.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.HolidayEntity;
import com.fonada.masking.repository.HolidayRepository;
import com.fonada.masking.service.HolidayService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/holiday")
public class HoliDayController {

	public static final Logger logger = LoggerFactory.getLogger(HoliDayController.class);

	@Autowired
	private HolidayRepository holiRepository;
	@Autowired
	private HolidayService holidayService;

	@RequestMapping(value = "/createBlockNumber", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String createDidBlockNumber(@RequestBody HolidayEntity holidayEntity,
			@RequestParam("userId") Integer userId) {
		logger.info("***** HoliDayController.createDidBloackNumber()::" + holidayEntity.toString());
		return holidayService.createHolidayDidNumber(holidayEntity, userId);
	}

	@RequestMapping(value = "/getHolidayNumberList", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String getHolidayNumberList() {
		logger.info("***** Inside HoliDayController.getHolidayNumberList() *****");
		DataContainer data = new DataContainer();
		List<HolidayEntity> holidayList = null;
		holidayList = holiRepository.findAll();
		if (holidayList.size() > 0) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(holidayList);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
		}
		logger.info("***** Inside HoliDayController.getHolidayNumberList() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/updatedDidBlockNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String updatedDidBlockNumberById(@RequestBody HolidayEntity holidayEntity,
			@RequestParam("userId") Integer userId) {
		logger.info("***** Inside HoliDayController.updatedDidBlockNumberById() Request::" + holidayEntity.toString());
		return holidayService.updateHolidayNumber(holidayEntity, userId);
	}

	@RequestMapping(value = "/deleteHolidayNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteDidBlockNumberById(@RequestParam("id") Long id) {
		logger.info("***** Inside HoliDayController.deleteDidBlockNumberById() Request::" + id);
		DataContainer data = new DataContainer();
		Optional<HolidayEntity> holiday = null;
		holiday = holiRepository.findById(id);
		if (holiday.isPresent()) {
			holiRepository.deleteById(id);
			data.setMsg(Constants.DELETE_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
		}
		logger.info("***** Success HoliDayController.deleteDidBlockNumberById() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findOneHolidayNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String findOneDidBlockNumberById(@RequestParam("id") Long id) {
		logger.info("***** Success HoliDayController.deleteDidBlockNumberById() Request::" + id);
		DataContainer data = new DataContainer();
		Optional<HolidayEntity> holiday = null;
		holiday = holiRepository.findById(id);
		if (holiday.isPresent()) {
			data.setData(holiday);
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
		}
		logger.info("***** Success HoliDayController.deleteDidBlockNumberById() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}
}
