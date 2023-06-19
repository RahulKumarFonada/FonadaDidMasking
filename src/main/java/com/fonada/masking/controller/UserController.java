package com.fonada.masking.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.CommonFildsSetter;
import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.bean.PasswordMangerResponse;
import com.fonada.masking.bean.UserDto;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.EmailValidator;
import com.fonada.masking.common.MobileNoValidator;
import com.fonada.masking.common.PasswordValidator;
import com.fonada.masking.entity.Roles;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.service.UserService;
import com.fonada.masking.utils.LoginUtility;
import com.fonada.masking.utils.Status;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.Api;

/**
 * Created by Rahul Kumar.
 */

@RestController
@RequestMapping(value = "/usercontroller")
@Api(value = "UserController", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	Date date = new Date();

	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	CommonFildsSetter c = new CommonFildsSetter();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userservice;
	@Autowired
	LoginUtility loginUtility;

	
	@RequestMapping(value = "/addnewuser", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String addnewuser(@RequestBody UserDto users) {
		PasswordMangerResponse response = new PasswordMangerResponse();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();

		try {
			LOGGER.info("**** Started UserController.addnewuser() And Request *****" + users);

			if (users.getUserName().isEmpty() || users.getUserName() == null) {
				data.setStatus(Constants.NOT_FOUND);
				data.setMsg("User Name Cannot Be Empty or Null.");
			} else if (users.getEmail().isEmpty() || users.getEmail() == null) {
				data.setStatus(Constants.NOT_FOUND);
				data.setMsg("Email Cannot be Empty or Null.");

				data.setStatus(Constants.NOT_FOUND);
			} else if (Boolean.FALSE.equals(EmailValidator.validate((users.getEmail())))) {
				data.setMsg(Constants.EMAIL_MSG);
			} else if (Boolean.FALSE.equals(PasswordValidator.validate((users.getPassword())))) {
				data.setStatus(Constants.NOT_FOUND);
				data.setMsg(Constants.PASSWORD_MSG);

			} else if (Boolean.FALSE.equals(MobileNoValidator.mobileNoValidate(users.getPhoneNumber()))) {
				data.setStatus(Constants.NOT_FOUND);
				data.setMsg(Constants.MOBILE_MSG);

			} else {
				data = userservice.addNewUsers(users);

			}
		} catch (Exception e) {
			LOGGER.error("****** User Got Exception" + e.getMessage());
			data.setMsg("Got Exception");
		}
		LOGGER.info("**** User Added SuccessFully UserController.addnewuser() And Response *****"
				+ gson.toJson(data).toString());
		return gson.toJson(data).toString();

	}

//	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String updateUser(@RequestBody Users users) {
		LOGGER.info("**** Inside UserController.updateUser() And Request *****" + users.toString());

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		Users user = null;
		try {
			user = userRepository.findById(users.getId()).get();
			if (Objects.nonNull(user)) {
				users.setModifiedBy(users.getUsername());
				users.setModifiedDate(Utils.convertDateToString(new Date()));
				userRepository.save(users);
				data.setMsg("Update Successfully");
				data.setStatus(Constants.REQUEST_SUCCESS);
			} else {
				data.setMsg(Constants.USER_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			data.setStatus(Constants.NOT_FOUND);
			data.setMsg("Got Exception::" + e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("**** Successfully Executed UserController.updateUser() And Got Response::*****" + data.toString());
		return gson.toJson(data).toString();
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String updateExistingUsers(@RequestBody Users users) {
		LOGGER.info("**** Inside UserController.updateExistingUsers() And Request *****" + users.toString());

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer data = new DataContainer();
		Users users1 = null;
		try {
			if (Boolean.FALSE.equals(MobileNoValidator.mobileNoValidate(users.getPhoneNumber()))) {
				data.setStatus(Constants.NOT_FOUND);
				data.setMsg("Mobile Number Not Valid For Ex:: 1234567890");
				return gson.toJson(data).toString();
			} else if (Boolean.FALSE.equals(EmailValidator.validate((users.getEmail())))) {
				data.setStatus(Constants.NOT_FOUND);
				data.setMsg(Constants.EMAIL_MSG);
				return gson.toJson(data).toString();
			} /*
				 * else if
				 * (Boolean.FALSE.equals(PasswordValidator.validate((users.getPassword())))) {
				 * data.setStatus(Constants.NOT_FOUND); data.setMsg(Constants.PASSWORD_MSG);
				 * return gson.toJson(data).toString();
				 * 
				 * }
				 */
			users1 = userRepository.findById(users.getId()).get();
			if (Objects.nonNull(users1)) {
				users1.setUsername(users.getUsername());
				users1.setFirstname(users.getFirstname());
				users1.setLastname(users.getLastname());
				users1.setRoleId(users.getRoleId());
				users1.setPhoneNumber(users.getPhoneNumber());
				users1.setEmail(users.getEmail());
				users1.setModifiedDate(Utils.convertDateToString(new Date()));
				Users users2 = userRepository.save(users1);
				data.setMsg(Constants.RECORDS_UPDATED);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(users2);
			} else {
				data.setStatus(Constants.RECORD_NOT_EXISTS);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				LOGGER.error("***** UserController.updateExistingUsers() Update Record Reponse *****::"
						+ Constants.RECORD_NOT_EXISTS_STRING);
				return gson.toJson(data).toString();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			data.setMsg(Constants.FAIL_MSG + "::" + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
			e.printStackTrace();
		}
		LOGGER.info(
				"**** Successfully Executed UserController.updateExistingUsers() And Response *****" + data.toString());
		return gson.toJson(data).toString();
	}

	@RequestMapping(value = "/getActiveUserList", method = RequestMethod.GET)
	@CrossOrigin("*")
	public String getUserList() {
		DataContainer dataContainer = new DataContainer();
		List<Users> userAll = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

		try {
			LOGGER.info("**** Started UserController.getUserList() *****");

			userAll = userRepository.findAllByIsActive(String.valueOf(Status.ACTIVE.getId()));
			if (!userAll.isEmpty()) {
				dataContainer.setData(userAll);
				dataContainer.setMsg(Constants.SUCCESS_MSG);
				dataContainer.setStatus(Constants.REQUEST_SUCCESS);
			} else {
				dataContainer.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				dataContainer.setStatus(Constants.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("***** Got Exception *****" + e.getMessage());
			dataContainer.setMsg("Got Exception::" + e.getMessage());
			dataContainer.setStatus(Constants.NOT_FOUND);
		}
		LOGGER.info("**** Successfully Executed UserController.getUserList() *****");
		return gson.toJson(dataContainer).toString();
	}

	@GetMapping("/login")
	@CrossOrigin("*")
	public String doLogin(@RequestParam("username") String userName, @RequestParam("password") String password) {
		LOGGER.info("**** Started UserController.doLogin() *****");

		DataContainer data = loginUtility.doLogin(userName, password);
		LOGGER.info("**** Successfully Executed UserController.doLogin Resposne*****::" + data.toString());

		return new Gson().toJson(data).toString();

	}

	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("username") String userName, @RequestParam("password") String password) {
		LOGGER.info("**** Started UserController.changePassword() *****");
		DataContainer data = new DataContainer();
		PasswordMangerResponse passwordMangerResponse = loginUtility.changePassword(userName, password);
		data.setMsg(passwordMangerResponse.getResult());
		data.setStatus(passwordMangerResponse.getStatusCode());
		LOGGER.info("**** Successfully Executed UserController.changePassword() Response*****::" + data.toString());
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteUserById", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteUserById(@RequestParam("id") Integer id) {
		LOGGER.info("**** Started UserController.deleteUserById() *****");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DataContainer dataContainer = new DataContainer();
		Optional<Users> users1 = null;
		try {
			users1 = userRepository.findById(id);
			if (users1.isPresent()) {
				if (users1.get().getIsActive().equals("1")) {
					users1.get().setIsActive(String.valueOf(Status.INACTIVE.getId()));
					users1.get().setIsDeleted("1");
					users1.get().setModifiedDate(Utils.convertDateToString(new Date()));
					userRepository.save(users1.get());
					dataContainer.setMsg("User Successfully De-Activate.");
					dataContainer.setStatus(Constants.REQUEST_SUCCESS);
					dataContainer.setData(users1);
				} else {
					dataContainer.setMsg("User Already De-Activate.");
					dataContainer.setStatus(Constants.NOT_FOUND);
				}
			} else {
				dataContainer.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				dataContainer.setStatus(Constants.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataContainer.setMsg("Got Exceptions." + e.getLocalizedMessage());
			dataContainer.setStatus(Constants.NOT_FOUND);
			LOGGER.info("***** Got Exception UserController.deleteUserById() *****" + e.getMessage());
		}
		LOGGER.info(
				"**** Successfully Executed UserController.deleteUserById() Response*****" + dataContainer.toString());
		return gson.toJson(dataContainer).toString();
	}

	@RequestMapping(value = "/findExitsUser", method = RequestMethod.GET)
	@CrossOrigin("*")
	public String getExistUser(@RequestParam("userName") String userName) {
		DataContainer dataContainer = new DataContainer();
		Users user = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

		try {
			LOGGER.info("**** Started UserController.getExistUser() *****");

			user = userRepository.findByUsername(userName);
			if (Objects.nonNull(user)) {
				if (Integer.valueOf(user.getIsActive()) == 1) {

					dataContainer.setData(user);
					dataContainer.setMsg(Constants.SUCCESS_MSG);
					dataContainer.setStatus(Constants.REQUEST_SUCCESS);
				} else {
					dataContainer.setMsg(Constants.USER_NOT_ACTIVE);
					dataContainer.setStatus(Constants.NOT_FOUND);
				}
			} else {
				dataContainer.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				dataContainer.setStatus(Constants.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("***** Got Exception getExistUser()*****" + e.getMessage());
			dataContainer.setMsg("Got Exception::" + e.getMessage());
			dataContainer.setStatus(Constants.NOT_FOUND);
		}
		LOGGER.info("**** Successfully Executed UserController.getExistUser() *****"
				+ gson.toJson(dataContainer).toString());
		return gson.toJson(dataContainer).toString();
	}
}
