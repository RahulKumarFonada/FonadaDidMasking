package com.fonada.masking.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.PromptEntity;
import com.fonada.masking.repository.PromptsRepository;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@RestController
public class PromptsController {

	@Autowired
	private PromptsRepository promptsRepository;

	@RequestMapping(value = "/createPrompt", method = RequestMethod.POST)
	public String createPrompt(@RequestParam("file") String file, @RequestParam("name") String name) {
		DataContainer data = new DataContainer();
		PromptEntity prompt = null;
		if (Objects.nonNull(name) && Objects.nonNull(file)) {
			prompt = new PromptEntity();
			prompt.setFile(file);
			prompt.setName(name);
			prompt.setCreateDate(Utils.convertDateToString(new Date()));
			prompt.setDeleteDate(Utils.convertDateToString(new Date()));
			promptsRepository.save(prompt);
			data.setData(prompt);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setMsg(Constants.SUCCESSADD_MSG);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.INVALID_REQUEST_STRING);
		}

		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/getAllPrompt", method = RequestMethod.GET)
	public String getAllPrompt() {
		DataContainer data = new DataContainer();
		List<PromptEntity> prompt = null;
		try {
			prompt = promptsRepository.findAll();
			if (prompt.size() > 0) {
				data.setData(prompt);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESSADD_MSG);
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
