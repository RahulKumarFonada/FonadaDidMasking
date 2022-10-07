package com.fonada.masking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.UnAnsweredCdr;
import com.fonada.masking.repository.UnAnsweredCdrRepository;
import com.google.gson.Gson;

@RestController(value = "/unAnsCdr")
public class UnAnsCdrController {

	@Autowired
	private UnAnsweredCdrRepository unAnsweredCdrRepository;

	@RequestMapping(value = "/getAllUnAnsCdrDetails", method = RequestMethod.GET)
	public String getAllUnAnsCdrDetails() {
		DataContainer data = new DataContainer();
		List<UnAnsweredCdr> customCdr = unAnsweredCdrRepository.findAll();
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
}
