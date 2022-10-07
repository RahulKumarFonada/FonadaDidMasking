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
import com.fonada.masking.entity.DidBlockedNumberEntity;
import com.fonada.masking.repository.DidBloackedNumberRepository;
import com.fonada.masking.service.DidBlockedNumberService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/didBlockNumber")
public class DidBlockedNumberController {
	public static final Logger logger = LoggerFactory.getLogger(DidBlockedNumberController.class);
	@Autowired
	private DidBlockedNumberService didBlockedNumberService;
	@Autowired
	private DidBloackedNumberRepository bloackedNumberRepository;

	@RequestMapping(value = "/createBlockNumber", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String createDidBlockNumber(@RequestBody DidBlockedNumberEntity didBloackedNumberEntity,
			@RequestParam("userId") Integer userId) {
		logger.info("***** Inside DidBlockedNumberController.createDidBlockNumber() Request::"
				+ didBloackedNumberEntity.toString());
		return didBlockedNumberService.createBlockedNumber(didBloackedNumberEntity, userId);
	}

	@RequestMapping(value = "/getDidBlockNumberList", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String getDidBlockNumberList() {
		logger.info("***** Inside DidBlockedNumberController.getDidBlockNumberList() *****");
		DataContainer data = new DataContainer();
		List<DidBlockedNumberEntity> didBloackedNumberEntity = null;
		didBloackedNumberEntity = bloackedNumberRepository.findAll();
		if (didBloackedNumberEntity.size() > 0) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(didBloackedNumberEntity);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
		}
		logger.info("***** Success DidBlockedNumberController.getDidBlockNumberList() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/updatedDidBlockNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String updatedDidBlockNumberById(@RequestBody DidBlockedNumberEntity didBloackedNumberEntity,
			@RequestParam("userId") Integer userId) {
		logger.info("***** Inside DidBlockedNumberController.updatedDidBlockNumberById() Request::"
				+ didBloackedNumberEntity.toString());
		return didBlockedNumberService.updateBlockedNUmber(didBloackedNumberEntity, userId);
	}

	@RequestMapping(value = "/deleteDidBlockNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteDidBlockNumberById(@RequestParam("blockNumberId") Long blockNumberId) {
		logger.info("***** Inside DidBlockedNumberController.deleteDidBlockNumberById() Request::" + blockNumberId);
		DataContainer data = new DataContainer();
		Optional<DidBlockedNumberEntity> didBloackedNumberEntity = null;
		didBloackedNumberEntity = bloackedNumberRepository.findById(blockNumberId);
		if (didBloackedNumberEntity.isPresent()) {
			bloackedNumberRepository.deleteById(blockNumberId);
			data.setMsg(Constants.DELETE_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
		}
		logger.info("***** Success DidBlockedNumberController.deleteDidBlockNumberById() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findOneDidBlockNumberById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String findOneDidBlockNumberById(@RequestParam("blockNumberId") Long blockNumberId) {
		DataContainer data = new DataContainer();
		logger.info("***** Inside DidBlockedNumberController.findOneDidBlockNumberById() Request::" + blockNumberId);
		Optional<DidBlockedNumberEntity> didBloackedNumberEntity = null;
		didBloackedNumberEntity = bloackedNumberRepository.findById(blockNumberId);
		if (didBloackedNumberEntity.isPresent()) {
			data.setData(didBloackedNumberEntity);
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
		}
		logger.info("***** Inside DidBlockedNumberController.findOneDidBlockNumberById() Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}
}
