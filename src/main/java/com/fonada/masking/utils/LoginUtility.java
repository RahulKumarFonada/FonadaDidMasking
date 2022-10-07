package com.fonada.masking.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.bean.PasswordMangerResponse;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.PasswordValidator;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.UserRepository;
import com.google.gson.Gson;

/**
 * 
 * @author Rahul Kumar
 *
 */
@Component
public class LoginUtility {
	public static final Logger LOGGER = LoggerFactory.getLogger(LoginUtility.class);

	@Autowired
	private UserRepository userRepository;
	private EncryptionUtil encUtil = new EncryptionUtil();
	private PasswordUtil pwdUtil = new PasswordUtil();

	// Reset Password
	public PasswordMangerResponse addUser(Users user) {

		PasswordMangerResponse response = new PasswordMangerResponse();
		String password = pwdUtil.generatePassword(8);
		user.setPassword(encUtil.encrypt(password, user.getPasswordsalt()));
		user.setPwdResetDate(Utils.convertDateToString(new Date()));
		userRepository.save(user);
		response.setResult(Constants.SUCCESS_MSG);
		response.setMessage(password);
		response.setStatusCode(Integer.parseInt(Constants.DEFAULT_ERROR));
		response.setUserName(user.getUsername());
		return response;

	}

	/**
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public DataContainer doLogin(String userName, String password) {
		LOGGER.info("***** Started LoginUtility.doLogin ***** ::UserName=>" + userName + "And Password" + password);

		DataContainer data = new DataContainer();
		Users user = null;
		String lastLoginTime = null;
		try {
			user = userRepository.findByUsername(userName);

			if (Objects.nonNull(user)) {
				String dbPassword = encUtil.decrypt(user.getPassword(), user.getPasswordsalt());

				lastLoginTime = user.getUserLastloginDatetime();
				Date currentTimestamp = new Date(System.currentTimeMillis());
				if (user.getIsActive().equals(String.valueOf(Status.ACTIVE.getId()))) {

					LOGGER.info("***** Inside LoginUtility.doLogin() Users Existing Detais ***** ::" + user.toString());
					// user = checkPassword(user, password, currentTimestamp, lastLoginTime);
					if (password.equals(dbPassword)) {
						user.setUserLastloginDatetime(Utils.convertDateToString(currentTimestamp));
						user = userRepository.save(user);
						data.setData(user);
						data.setMsg(Constants.RECORD_FOUND);
						data.setStatus(Constants.REQUEST_SUCCESS);
						LOGGER.info(
								"***** Inside LoginUtility.doLogin() Users Existing Detais With Correct Password *****::");

					} else {
						user.setUserLastloginDatetime(lastLoginTime);
						user = userRepository.save(user);
						data.setMsg(Constants.INVALID_PASSWORD);
						data.setStatus(Constants.INVALID_OLD_PASSWORD);
						LOGGER.info("***** Inside LoginUtility.doLogin() INVALID OLD PASSWORD *****::");

					}
				} else {
					user.setUserLastloginDatetime(lastLoginTime);
					user = userRepository.save(user);
					data.setMsg(Constants.USER_NOT_ACTIVE);
					data.setStatus(Constants.BLOCKED_USER);

					LOGGER.info("***** Inside LoginUtility.doLogin() User Is Not Active *****");
				}
			} else {
				data.setMsg(Constants.USER_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				LOGGER.info("*****Inside LoginUtility.doLogin() User Not Available *****");

			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("***** Inside LoginUtility.doLogin() Got Exception ***** " + e.getMessage());
			data.setMsg("Got Exception" + e.getMessage());
			data.setStatus(Constants.NOT_FOUND);

		}
		LOGGER.info("***** Successfully Executed LoginUtility.doLogin() And Response *****::"
				+ new Gson().toJson(data).toString());
		return data;
	}

	public Users checkPassword(Users user, String password, Date currentTimestamp, String lastLoginTime) {
		user.setUserLastloginDatetime(lastLoginTime);
		LOGGER.info("***** Started LoginUtility.checkPassword()*****");

		String dbPassword = encUtil.decrypt(user.getPassword(), user.getPasswordsalt());
		if (password.equals(dbPassword)) {
			user.setUserLastloginDatetime(Utils.convertDateToString(currentTimestamp));
			user = userRepository.save(user);
		} else {

			user = userRepository.save(user);
		}
		LOGGER.info("*****  Successfully Executed LoginUtility.checkPassword()*****::");

		return user;
	}

	// to change the user password
	public PasswordMangerResponse changePassword(String userId, String newPassword) {
		LOGGER.info("***** Started LoginUtility.changePassword()*****");

		PasswordMangerResponse response = new PasswordMangerResponse();
		Users user = null;
		try {
			if (Boolean.FALSE.equals(PasswordValidator.validate((newPassword)))) {
				response.setStatusCode(Constants.NOT_FOUND);
				response.setResult(Constants.PASSWORD_MSG);
				return response;
			}
			user = userRepository.findByUsername(userId);
			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, 30);
			Date date = c1.getTime();
			if (Objects.nonNull(user)) {
				String encNewPassword = encUtil.encrypt(newPassword, user.getPasswordsalt());
				if (user.isActive.equals(Integer.toString(Status.ACTIVE.getId()))) {
					user.setPassword(encNewPassword);
					user.setPwdResetDate(Utils.convertDateToString(new Date()));
					userRepository.save(user);
					response.setResult(Constants.RECORDS_UPDATED);
					response.setUserName(userId);
					response.setStatusCode(Constants.REQUEST_SUCCESS);
				} else {
					response.setResult(Constants.USER_NOT_ACTIVE);
					response.setUserName(userId);
					response.setStatusCode(Constants.BLOCKED_USER);
					LOGGER.info("User deleted " + user.getUsername());
				}
			} else {
				response.setResult(Constants.RECORD_NOT_EXISTS_STRING);
				response.setUserName(userId);
				response.setStatusCode(Constants.RECORD_NOT_EXISTS);
			}
		} catch (Exception e) {
			LOGGER.info("User Got Exception::" + e.getMessage());
			response.setMessage("Exception::" + e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("***** Successfully Executed LoginUtility.changePassword()*****" + response.toString());

		return response;
	}
}
