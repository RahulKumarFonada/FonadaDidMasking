/**
 *
 */
package com.fonada.masking.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fonada.masking.bean.CommonFildsSetter;
import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.bean.PasswordMangerResponse;
import com.fonada.masking.bean.UserDto;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.CommonFields;
import com.fonada.masking.entity.Roles;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.RolesRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.utils.EncryptionUtil;
import com.fonada.masking.utils.PasswordUtil;
import com.fonada.masking.utils.Status;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@Service
public class UserService {
	public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	CommonFildsSetter c = new CommonFildsSetter();
	private EncryptionUtil encUtil = new EncryptionUtil();
	private PasswordUtil pwdUtil = new PasswordUtil();

	@Autowired
	public RolesRepository rolesRepository;

	@Autowired
	private UserRepository userrepositories;

	/*
	 * @Autowired private ServiceManager serviceManager;
	 */
	public DataContainer addNewUsers(UserDto user) {
		LOGGER.info("**** Inside UserService.addnewuser() *****");
		Users users = null;
		PasswordMangerResponse response = new PasswordMangerResponse();
		DataContainer data = new DataContainer();
		try {
			/**
			 * This Condition For Checking Duplicate
			 */
			if (Objects.isNull(userrepositories.findByUsername(user.getUserName()))) {
				if (Objects.isNull(userrepositories.findByEmail(user.getEmail()))) {
					users = new Users();

					users.setUsername(user.getUserName());
					users.setFirstname(user.getFristName());
					users.setLastname(user.getLastName());
					users.setEmail(user.getEmail());
					users.setPhoneNumber(user.getPhoneNumber());
					users.setRoleId(user.getRoleId());
					// users.setCommonFields(c.create_Record(null));

					users.setIsActive("1");
					users.setIsDeleted("0");
					users.setModifiedBy(users.getUsername());
					users.setModifiedDate(Utils.convertDateToString(new Date()));
					users.setCreatedBy(users.getUsername());
					users.setCreatedDate(Utils.convertDateToString(new Date()));

					users.setUserLastloginDatetime(Utils.convertDateToString(new Date()));
					Calendar c1 = new GregorianCalendar();
					c1.add(Calendar.DATE, 30);
					Date date = c1.getTime();

					users.setPasswordsalt(pwdUtil.generateSalt(16));
					// String password = pwdUtil.generatePassword(8);
					users.setPassword(encUtil.encrypt(user.getPassword(), users.getPasswordsalt()));
					users.setPwdResetDate(Utils.convertDateToString(new Date()));
					userrepositories.save(users);

					data.setData(users);
					data.setMsg(Constants.SUCCESSADD_MSG);
					data.setStatus(Constants.REQUEST_SUCCESS);
				} else {
					data.setMsg("Email Already Registered Another User.");
					data.setStatus(Constants.RECORD_EXISTS);
					data.setRequest_status(Constants.FAIL_MSG);

				}
			} else {
				data.setMsg("User Already Registered.");
				data.setStatus(Constants.RECORD_EXISTS);
				data.setRequest_status(Constants.FAIL_MSG);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			data.setMsg("Got Exception:: " + ex.getMessage());
			data.setStatus(Constants.RECORD_EXISTS);
			data.setRequest_status(Constants.FAIL_MSG);

			ex.printStackTrace();
			return data;
		}
		LOGGER.info("**** Successfully Executed UserService.addnewuser() *****" + users);

		return data;
	}

	// add new roles
	public DataContainer addNewRoles(Roles roles) {
		LOGGER.info("**** Started UserService.addNewRoles() *****");

		DataContainer data = new DataContainer();
		CommonFields commonField = new CommonFields();
		Roles exitingRole = null;
		try {
			exitingRole = rolesRepository.findByName(roles.getName());

			if (Objects.isNull(exitingRole)) {
				commonField.setCreatedBy("admin");
				commonField.setModifiedBy("admin");

				commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));
				commonField.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));
				commonField.setCreatedDate(new Date());
				commonField.setModifiedDate(new Date());
				roles.setCommonFields(commonField);
				Roles role = rolesRepository.save(roles);
				if (Objects.nonNull(role)) {
					data.setMsg(Constants.SUCCESSADD_MSG);
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setData(role);
				} else {
					data.setMsg(Constants.FAIL_MSG);
					data.setStatus(Constants.NOT_FOUND);
				}
				// Logger.info("Roles is got after save using repo ... " + roles.getId());
			} else {

				data.setMsg(Constants.RECORD_ALREADY_EXISTS_STRING);
				data.setStatus(Constants.RECORD_EXISTS);
			}

		} catch (

		Exception ee) {
			ee.printStackTrace();
			data.setMsg("Got Exception." + ee.getMessage());
		}
		LOGGER.info("**** Successfully Executed UserService.addNewRoles() *****");
		return data;
	}

	public DataContainer updatePasswordByUserIsActive(String userName, String password) {
		DataContainer data = new DataContainer();
		Users users1 = null;
		users1 = userrepositories.findByUsername(userName);

		try {
			LOGGER.info("***** Enter into updatePassword controller *****" + users1);
			if (Objects.nonNull(users1)) {
				if (users1.getIsActive().equals(String.valueOf(Status.ACTIVE.getId()))) {
					users1.setPassword(encUtil.encrypt(password, users1.getPasswordsalt()));
					users1.setPwdResetDate(Utils.convertDateToString(new Date()));
					// users1.getCommonFields().setModifiedBy("");
					users1.setModifiedDate(Utils.convertDateToString(new Date()));
					Users users2 = userrepositories.save(users1);
					if (Objects.nonNull(users2)) {
						data.setMsg(Constants.RECORDS_UPDATED);
						data.setStatus(Constants.REQUEST_SUCCESS);
						data.setData(users2);
					}
				} else {
					data.setMsg(Constants.USER_NOT_ACTIVE);
					data.setStatus(Constants.BLOCKED_USER);
					LOGGER.info("***** User Is Not Active *****");
				}
			} else {
				data.setMsg(Constants.USER_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				LOGGER.info("***** User Not Available ***** ::" + new Gson().toJson(data).toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
			data.setMsg("**** Got Exception updatePassword() ******" + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
		}
		return data;
	}
}
