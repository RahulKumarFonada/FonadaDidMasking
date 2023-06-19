package com.fonada.masking.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import com.fonada.masking.entity.TimeGroupEntity;
import com.fonada.masking.repository.TimeGroupRepository;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/timeGroupController")
public class TimeGroupController {
	public static final Logger logger = LoggerFactory.getLogger(TimeGroupController.class);
	@Autowired
	private TimeGroupRepository timeGroupRepository;

	@RequestMapping(value = "/timeGroupList", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String findAllTimeGroupList() {
		logger.info("***** Inside TimeGroupController.findAllTimeGroupList() ****");
		List<TimeGroupEntity> timeGroupEntity = null;
		DataContainer data = new DataContainer();
		timeGroupEntity = timeGroupRepository.findAll();
		if (timeGroupEntity.size() > 0) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(timeGroupEntity);
			logger.info("***** Inside TimeGroupController.findAllTimeGroupList() Response::" + data.toString());

		} else {
			data.setMsg(Constants.INVALID_REQUEST_STRING);
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside TimeGroupController.findAllTimeGroupList() Response::" + data.toString());

		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/createTimeGroup", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String saveTimeGroup(@RequestBody TimeGroupEntity timeGroupEntity) {
		logger.info("***** Inside TimeGroupController.findAllTimeGroupList() Request::" + timeGroupEntity.toString());

		DataContainer data = new DataContainer();
		try {
			if (!timeGroupEntity.getTimeGroup().equals("") && !timeGroupEntity.getTimeGroup().equals("")) {
				timeGroupEntity.setCreatedDate(Utils.convertDateToString(new Date()));
				timeGroupEntity = timeGroupRepository.save(timeGroupEntity);
				data.setMsg(Constants.SUCCESS_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(timeGroupEntity);
				logger.info("***** Inside TimeGroupController.saveTimeGroup() Response::" + data.toString());
			} else {
				data.setMsg(Constants.INVALID_REQUEST_STRING);
				data.setStatus(Constants.REQUEST_FAILED);
				logger.info("***** Inside TimeGroupController.saveTimeGroup() Response::" + data.toString());
			}
		} catch (Exception e) {
			data.setMsg(Constants.FAIL_MSG);
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside TimeGroupController.saveTimeGroup() Response::" + data.toString());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteTimeGroupById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteTimeGroupById(@RequestParam("groupId") Long id) {
		logger.info("***** Inside TimeGroupController.deleteTimeGroupById() Request::" + id);
		DataContainer data = new DataContainer();
		Optional<TimeGroupEntity> timeGroupEntity = timeGroupRepository.findById(id);
		if (timeGroupEntity.isPresent()) {
			timeGroupRepository.deleteById(id);
			data.setMsg(Constants.DELETE_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			logger.info("***** Inside TimeGroupController.deleteTimeGroupById() Response::" + data.toString());
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.NOT_FOUND);
			logger.info("***** Inside TimeGroupController.deleteTimeGroupById() Response::" + data.toString());
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/updateTimeGroup", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String updateTimeGroup(@RequestBody TimeGroupEntity timeGroupEntity) {
		DataContainer data = new DataContainer();
		logger.info("***** Inside TimeGroupController.deleteTimeGroupById() Request::" + timeGroupEntity.toString());

		TimeGroupEntity exitTimeGroup = null;
		try {
			exitTimeGroup = timeGroupRepository.findOneById(timeGroupEntity.getId());
			if (Objects.nonNull(exitTimeGroup)) {
				logger.info("*****ExitTimeGroup TimeGroupController.deleteTimeGroupById() Response::"
						+ exitTimeGroup.toString());
				if (!exitTimeGroup.getTimeGroup().equals(timeGroupEntity.getTimeGroup())) {
					exitTimeGroup.setTimeGroup(timeGroupEntity.getTimeGroup());
				}
				if (!exitTimeGroup.getTimeGroupName().equals(timeGroupEntity.getTimeGroupName())) {
					exitTimeGroup.setTimeGroupName(timeGroupEntity.getTimeGroupName());
				}
				timeGroupEntity = timeGroupRepository.save(exitTimeGroup);
				data.setMsg(Constants.SUCCESS_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(timeGroupEntity);
				logger.info("*****TimeGroupController.deleteTimeGroupById() Response::" + data.toString());

			} else {
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				data.setStatus(Constants.REQUEST_FAILED);
				logger.info("*****TimeGroupController.deleteTimeGroupById() Response::" + data.toString());

			}
		} catch (Exception e) {
			data.setMsg(Constants.FAIL_MSG);
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside TimeGroupController.deleteTimeGroupById() Response::" + data.toString());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/getAllTimeGroupDetails", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String getAllTimeGroupDetails() {
		logger.info("***** Inside TimeGroupController.getAllTimeGroupDetails() *****");

		DataContainer data = new DataContainer();
		List<TimeGroupEntity> exitTimeGroup = null;
		try {
			exitTimeGroup = timeGroupRepository.findAll();
			if (exitTimeGroup.size() > 0) {
				data.setMsg(Constants.SUCCESS_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(exitTimeGroup);
				logger.info(
						"***** Inside TimeGroupController.getAllTimeGroupDetails() Response::*****" + data.toString());
			} else {
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				data.setStatus(Constants.REQUEST_FAILED);
				logger.info(
						"***** Inside TimeGroupController.getAllTimeGroupDetails() Response::*****" + data.toString());
			}
		} catch (Exception e) {
			data.setMsg("Got Exceptions:: " + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
			logger.info("***** Inside TimeGroupController.getAllTimeGroupDetails() Response::*****" + data.toString());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}
}
