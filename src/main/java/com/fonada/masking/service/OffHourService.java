package com.fonada.masking.service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.OffHourManagement;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.OffHourRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

/**
 * 
 * @author Rahul
 *
 */
@Service
public class OffHourService {

	public static final Logger Logger = LoggerFactory.getLogger(OffHourService.class);
	@Autowired
	private OffHourRepository offHourRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param offHourManagement
	 * @param userId
	 * @return
	 */
	public String createOffHour(OffHourManagement offHourManagement, Integer userId) {
		Logger.info("***** Inside OffHourService.createOffHour() *****");
		Optional<Users> user = null;
		DataContainer data = new DataContainer();
		try {

			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				Logger.info("*****OffHourService.createOffHour() Resposne::*****" + data.toString());

				return new Gson().toJson(data).toString();
			}

			if (StringUtils.isNotBlank(offHourManagement.getOffHourEndDate())
					|| Objects.nonNull(offHourManagement.getOffHourEndDate())
							&& StringUtils.isNotBlank(offHourManagement.getOffHourEndDate())
					|| Objects.nonNull(offHourManagement.getOffHourEndDate())) {
				data.setStatus(Constants.INVALID_REQUEST);
				data.setMsg(Constants.INVALID_REQUEST_STRING);
				Logger.info("*****OffHourService.createOffHour() Response::*****" + data.toString());
				return new Gson().toJson(data).toString();
			}
			offHourManagement.setCreatedBy(user.get().getUsername());
			offHourManagement.setModifyBy(user.get().getUsername());
			offHourManagement.setModifyDate(Utils.convertDateToString(new Date()));
			offHourManagement.setCreatedDate(Utils.convertDateToString(new Date()));
			offHourRepository.save(offHourManagement);
			data.setMsg(Constants.SUCCESSADD_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(offHourManagement);
			Logger.info("*****OffHourService.createOffHour() Response::*****" + data.toString());

		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			Logger.info("*****OffHourService.createOffHour() Response::*****" + data.toString());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	/**
	 * 
	 * @param offHourManagement
	 * @param userId
	 * @return
	 */
	public String updateOffHour(OffHourManagement offHourManagement, Integer userId) {
		Logger.info("***** Inside OffHourService.updateOffHour() *****");
		Optional<Users> user = null;
		DataContainer data = new DataContainer();
		OffHourManagement offHourexist = null;
		try {
			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				Logger.info("*****OffHourService.updateOffHour() Response" + data.toString());
				return new Gson().toJson(data).toString();
			}
			offHourexist = offHourRepository.findOneById(offHourManagement.getId());

			if (Objects.nonNull(offHourexist)) {
				Logger.info("*****OffHourService.updateOffHour() Exist OffHour Recrod::" + data.toString());
				offHourexist.setOffHourEndDate(offHourManagement.getDescription());
				offHourexist.setOffHourstartDate(offHourManagement.getOffHourstartDate());
				offHourexist.setDescription(offHourManagement.getDescription());
				offHourexist.setIsGlobal(offHourManagement.getIsGlobal());
				offHourManagement.setModifyBy(user.get().getUsername());
				offHourManagement.setModifyDate(Utils.convertDateToString(new Date()));
				offHourRepository.save(offHourManagement);
				data.setMsg(Constants.RECORDS_UPDATED);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(offHourManagement);
				Logger.info("*****OffHourService.updateOffHour() Response" + data.toString());
				return new Gson().toJson(data).toString();
			} else {
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				data.setStatus(Constants.RECORD_EXISTS);
				Logger.info("*****OffHourService.updateOffHour() Response" + data.toString());
				return new Gson().toJson(data).toString();

			}
		} catch (Exception e) {
			data.setMsg("Got Exception:: " + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
			Logger.info("***** Inside OffHourService.updateOffHour() Response" + data.toString());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}
}
