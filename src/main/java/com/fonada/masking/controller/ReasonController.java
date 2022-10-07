package com.fonada.masking.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.ReasonEntity;
import com.fonada.masking.repository.ReasonRepository;
import com.google.gson.Gson;

@RestController
public class ReasonController {

	@Autowired
	private ReasonRepository reasonRepository;

	@RequestMapping(value = "/createReason", method = RequestMethod.POST)
	public String createReason(@RequestParam("reason") String reason) {
		DataContainer data = new DataContainer();
		ReasonEntity reasonEntity = null;
		try {
			if (Objects.nonNull(reason)) {
				reasonEntity = new ReasonEntity();
				reasonEntity.setReason(reason);
				reasonEntity = reasonRepository.save(reasonEntity);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESSADD_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.INVALID_REQUEST_STRING);
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/getReasonList", method = RequestMethod.GET)
	public String createReason() {
		DataContainer data = new DataContainer();
		List<ReasonEntity> reasonEntity = null;
		try {
			reasonEntity = reasonRepository.findAll();
			if (reasonEntity.size() > 0) {
				data.setData(reasonEntity);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESS_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/getReasonById", method = RequestMethod.GET)
	public String getReasonById(@RequestParam("id") Integer id) {
		DataContainer data = new DataContainer();
		Optional<ReasonEntity> reasonEntity = null;
		try {
			reasonEntity = reasonRepository.findById(id);
			if (reasonEntity.isPresent()) {
				data.setData(reasonEntity);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESS_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteReasonById", method = RequestMethod.DELETE)
	public String deleteReasonById(@RequestParam("id") Integer id) {
		DataContainer data = new DataContainer();
		Optional<ReasonEntity> reasonEntity = null;
		try {
			reasonEntity = reasonRepository.findById(id);
			if (reasonEntity.isPresent()) {
				reasonRepository.deleteById(id);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.DELETE_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public String deleteReasonById() {
		DataContainer data = new DataContainer();
		List<ReasonEntity> reasonEntity = null;
		try {
			reasonEntity = reasonRepository.findAll();
			if (reasonEntity.size() > 0) {
				reasonRepository.deleteAll();
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.DELETE_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}
}
