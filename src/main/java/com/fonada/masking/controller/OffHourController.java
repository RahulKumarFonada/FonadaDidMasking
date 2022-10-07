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
import com.fonada.masking.entity.OffHourManagement;
import com.fonada.masking.repository.OffHourRepository;
import com.fonada.masking.service.OffHourService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/offHour")
public class OffHourController {
	public static final Logger logger = LoggerFactory.getLogger(OffHourController.class);
	@Autowired
	private OffHourRepository offHourRepository;
	@Autowired
	private OffHourService offouHourService;

	@RequestMapping(value = "/createOffHourList", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String createDidBlockNumber(@RequestBody OffHourManagement offHourManagement,
			@RequestParam("userId") Integer userId) {
		logger.info("***** Inside OffHourController.createDidBlocakNubmer() Request::" + offHourManagement.toString());
		return offouHourService.createOffHour(offHourManagement, userId);
	}

	@RequestMapping(value = "/getOffHourList", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String getOffHourList() {
		logger.info("***** Inside OffHourController.getOffHourList() *****");
		DataContainer data = new DataContainer();
		List<OffHourManagement> OffHourList = null;
		OffHourList = offHourRepository.findAll();
		if (OffHourList.size() > 0) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(OffHourList);
			logger.info("***** Inside OffHourController.getOffHourList() Response::" + data.toString());

		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside OffHourController.getOffHourList() Response::" + data.toString());
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/updatedOffHourManagementById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String updatedOffHourManagementById(@RequestBody OffHourManagement offHourManagement,
			@RequestParam("userId") Integer userId) {
		logger.info("***** Inside OffHourController.updatedOffHourManagementById() Request::"
				+ offHourManagement.toString());

		return offouHourService.updateOffHour(offHourManagement, userId);
	}

	@RequestMapping(value = "/deleteHolidayNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteOffHourManagementById(@RequestParam("id") Long id) {
		logger.info("***** Inside OffHourController.updatedOffHourManagementById() Request::" + id);
		DataContainer data = new DataContainer();
		Optional<OffHourManagement> holiday = null;
		holiday = offHourRepository.findById(id);
		if (holiday.isPresent()) {
			offHourRepository.deleteById(id);
			data.setMsg(Constants.DELETE_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			logger.info("***** Inside OffHourController.updatedOffHourManagementById() Response::" + data.toString());

		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside OffHourController.updatedOffHourManagementById() Response::" + data.toString());

		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findOneHolidayNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String findOneDidBlockNumberById(@RequestParam("id") Long id) {
		logger.info("***** Inside OffHourController.findOneDidBlockNumberById() Request::" + id);
		DataContainer data = new DataContainer();
		Optional<OffHourManagement> holiday = null;
		holiday = offHourRepository.findById(id);
		if (holiday.isPresent()) {
			data.setData(holiday);
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			logger.info("***** Inside OffHourController.findOneDidBlockNumberById() Response::" + data.toString());
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside OffHourController.findOneDidBlockNumberById() Response::" + data.toString());
		}
		return new Gson().toJson(data).toString();
	}

}
