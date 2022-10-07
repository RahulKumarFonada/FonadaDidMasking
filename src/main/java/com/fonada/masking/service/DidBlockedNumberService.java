package com.fonada.masking.service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.DidBlockedNumberEntity;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.DidBloackedNumberRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@Service
public class DidBlockedNumberService {
	public static final Logger Logger = LoggerFactory.getLogger(DidBlockedNumberService.class);

	@Autowired
	private DidBloackedNumberRepository bloackedNumberRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public String createBlockedNumber(DidBlockedNumberEntity didBloackedNumberEntity, Integer userId) {
		Logger.info("***** Inside DidBlockedNumberService.createBlockedNumber Request::"
				+ didBloackedNumberEntity.toString());
		DataContainer data = new DataContainer();
		DidBlockedNumberEntity saveDidBlocked = null;
		Optional<Users> user = null;
		try {
			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
				return new Gson().toJson(data).toString();
			}
			if (StringUtils.isBlank(didBloackedNumberEntity.getDidNo())
					|| Objects.isNull(didBloackedNumberEntity.getDidNo())) {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg("Did Number Cannot Be Empty.");
				Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
				return new Gson().toJson(data).toString();
			}
			if (StringUtils.isBlank(didBloackedNumberEntity.getCliNo())
					|| Objects.isNull(didBloackedNumberEntity.getCliNo())) {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg("Cli Number Cannot Be Empty.");
				Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
				return new Gson().toJson(data).toString();

			}
			didBloackedNumberEntity.setUsersId(userId);
			didBloackedNumberEntity.setCreatedBy(user.get().getUsername());
			didBloackedNumberEntity.setModifyBy(user.get().getUsername());
			didBloackedNumberEntity.setModifyDate(Utils.convertDateToString(new Date()));
			didBloackedNumberEntity.setCreatedDate(Utils.convertDateToString(new Date()));
			saveDidBlocked = bloackedNumberRepository.save(didBloackedNumberEntity);
			if (saveDidBlocked.getId() != null) {
				data.setData(saveDidBlocked);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESSADD_MSG);
				Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
				return new Gson().toJson(data).toString();

			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.DATANOTSAVED);
				Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
				return new Gson().toJson(data).toString();
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());

			e.printStackTrace();
		}
		/*
		 * if (Objects.nonNull(didBloackedNumberEntity.getReasonId())) {
		 * 
		 * } else { data.setStatus(Constants.REQUEST_FAILED);
		 * data.setMsg("ReasonId Cannot Be Empty."); }
		 */
		Logger.info("***** Successfully Executed DidBlockedNumberService.createBlockedNumber *****");
		return new Gson().toJson(data).toString();
	}

	@Transactional
	public String updateBlockedNUmber(DidBlockedNumberEntity didBloackedNumberEntity, Integer userId) {
		Logger.info("***** Inside DidBlockedNumberService.createBlockedNumber *****");
		DataContainer data = new DataContainer();
		DidBlockedNumberEntity exitBlockedNumber = null;
		Optional<Users> user = null;
		try {
			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
				return new Gson().toJson(data).toString();
			}
			exitBlockedNumber = bloackedNumberRepository.findOneById(didBloackedNumberEntity.getId());
			if (!exitBlockedNumber.getDidNo().equals(didBloackedNumberEntity.getDidNo())) {
				exitBlockedNumber.setDidNo(didBloackedNumberEntity.getDidNo());
			}
			if (!exitBlockedNumber.getCliNo().equals(didBloackedNumberEntity.getCliNo())) {
				exitBlockedNumber.setCliNo(didBloackedNumberEntity.getCliNo());
			}
			if (!exitBlockedNumber.getIsActive().equals(didBloackedNumberEntity.getIsActive())) {
				exitBlockedNumber.setIsActive(didBloackedNumberEntity.getIsActive());
			}
			if (!exitBlockedNumber.getIsGlobal().equals(didBloackedNumberEntity.getIsGlobal())) {
				exitBlockedNumber.setIsGlobal(didBloackedNumberEntity.getIsGlobal());
			}
			if (!exitBlockedNumber.getDnd().equals(didBloackedNumberEntity.getDnd())) {
				exitBlockedNumber.setDnd(didBloackedNumberEntity.getDnd());
			}

			exitBlockedNumber.setUsersId(userId);
			exitBlockedNumber.setModifyBy(user.get().getUsername());
			exitBlockedNumber.setModifyDate(Utils.convertDateToString(new Date()));
			exitBlockedNumber = bloackedNumberRepository.save(exitBlockedNumber);
			data.setData(exitBlockedNumber);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setMsg(Constants.RECORDS_UPDATED);

		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Got Exception::" + e.getMessage());
			e.printStackTrace();
		}
		/*
		 * if (Objects.nonNull(didBloackedNumberEntity.getReasonId())) {
		 * 
		 * } else { data.setStatus(Constants.REQUEST_FAILED);
		 * data.setMsg("ReasonId Cannot Be Empty."); }
		 */
		Logger.info("*****DidBlockedNumberService.createBlockedNumber Response::" + data.toString());
		return new Gson().toJson(data).toString();
	}
}
