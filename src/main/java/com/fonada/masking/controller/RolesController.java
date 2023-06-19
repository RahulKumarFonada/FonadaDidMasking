package com.fonada.masking.controller;

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
import com.fonada.masking.entity.Roles;
import com.fonada.masking.repository.RolesRepository;
import com.fonada.masking.service.UserService;
import com.fonada.masking.utils.LoginUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/roles")
public class RolesController {

	public static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);

	@Autowired
	private UserService userservice;
	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	LoginUtility loginUtility;

	// Add new Roles
	@RequestMapping(value = "/addroles", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String addnewroles(@RequestBody Roles role) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = null;
		LOGGER.info("**** Started RolesController.addnewroles() *****");

		data = userservice.addNewRoles(role);

		LOGGER.info("**** Successfully Executed RolesController.addnewroles Resposne*****::" + data.toString());
		return gson.toJson(data).toString();

	}

	@RequestMapping(value = "/findRolesList", method = RequestMethod.GET)
	public String getAllRoles() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		Iterable<Roles> rolesList = this.rolesRepository.findAll();
		if (Objects.nonNull(rolesList)) {
			data.setMsg(Constants.SUCCESSADD_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(rolesList);
		} else {
			data.setMsg(Constants.FAIL_MSG);
			data.setStatus(Constants.NOT_FOUND);
		}
		return gson.toJson(data).toString();

	}

	@RequestMapping(value = "/findByRolesName", method = RequestMethod.GET)
	public String getByRolesName(@RequestParam("rolesName") String rolesName) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		Roles roles = this.rolesRepository.findByName(rolesName);
		if (Objects.nonNull(roles)) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(roles);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.NOT_FOUND);
		}
		return gson.toJson(data).toString();

	}

	@RequestMapping(value = "/findByRolesId", method = RequestMethod.GET)
	public String getByRoleId(@RequestParam("id") int id) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		Optional<Roles> roles = this.rolesRepository.findById(id);
		if (roles.isPresent()) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(roles);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.NOT_FOUND);
		}
		return gson.toJson(data).toString();

	}

	@RequestMapping(value = "/deleteById", method = RequestMethod.DELETE)
	public String deleteById(@RequestParam("id") int id) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		Optional<Roles> roles = this.rolesRepository.findById(id);
		if (roles.isPresent()) {
			rolesRepository.deleteById(id);
			data.setMsg(Constants.DELETE_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(roles);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(Constants.NOT_FOUND);
		}
		return gson.toJson(data).toString();

	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public String deleteAll() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		rolesRepository.deleteAll();
		data.setMsg(Constants.DELETE_MSG);
		data.setStatus(Constants.REQUEST_SUCCESS);
		return gson.toJson(data).toString();
	}
}
