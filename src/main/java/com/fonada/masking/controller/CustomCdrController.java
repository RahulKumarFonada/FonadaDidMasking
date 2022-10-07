package com.fonada.masking.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.CustomCdr;
import com.fonada.masking.repository.CustomCdrRepository;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/custom/cdr")
public class CustomCdrController {

	@Autowired
	private CustomCdrRepository customCdrRepository;

	@RequestMapping(value = "/getAllCustomerCdrList", method = RequestMethod.GET)
	public String getAllCustomCdrDetails() {
		DataContainer data = new DataContainer();
		List<CustomCdr> customCdr = customCdrRepository.findAll();
		if (customCdr.size() > 0) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(customCdr);
		} else {
			data.setMsg("Record Not Found.");
			data.setStatus(Constants.NOT_FOUND);
		}

		return new Gson().toJson(data).toString();
	}

	/*
	 * @RequestMapping(value = "/getReport", method = RequestMethod.POST) public
	 * String getReport(@RequestParam("startDate") Date
	 * startDate, @RequestParam("endDate") Date endDate,
	 * 
	 * @RequestParam("disposition") String disposition, @RequestParam("cLid") String
	 * cLid) { DataContainer data = new DataContainer(); List<CustomCdr> customCdr =
	 * null; customCdr = customCdrRepository.findAllAnsweredCustomCdr(startDate,
	 * endDate, disposition, cLid); if (customCdr.size() > 0) {
	 * data.setMsg(Constants.SUCCESS_MSG);
	 * data.setStatus(Constants.REQUEST_SUCCESS); data.setData(customCdr); } else {
	 * data.setMsg("Record Not Found."); data.setStatus(Constants.NOT_FOUND); }
	 * 
	 * return new Gson().toJson(data).toString(); }
	 */
}
