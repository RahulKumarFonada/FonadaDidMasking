package com.fonada.masking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.bean.DidCallFlowNumberDto;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.DidNumberMappingType;
import com.fonada.masking.entity.DidMappingEntity;
import com.fonada.masking.entity.InComingDidEntity;
import com.fonada.masking.entity.MappingDidCallFlowNumber;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.DidMappingRepository;
import com.fonada.masking.repository.MappingDidNumberCallFlowNumberRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.utils.DidNumberPreperedLocation;
import com.fonada.masking.utils.Status;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@Service
public class DidCallFlowNumberMappingService {

	public static Logger Logger = LoggerFactory.getLogger(DidCallFlowNumberMappingService.class);

	@Value("${api.key}")
	String apiKey;

	@Autowired
	private MappingDidNumberCallFlowNumberRepository mappingDidNumberClowFlowNumberRepository;
	@Autowired
	private DidMappingRepository didMappingRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public DataContainer createDidMapping(DidCallFlowNumberDto didCallFlowNumberDto, Integer userId) {

		Logger.info("***** Inside DidCallFlowNumberMappingService.createDidMapping() *****");
		DidMappingEntity didMappingEntity = null;
		DataContainer data = new DataContainer();
		List<DidMappingEntity> didMappingList = new ArrayList<DidMappingEntity>();
		List<DidMappingEntity> saveMappingList = new ArrayList<DidMappingEntity>();
		Optional<Users> user = null;
		try {
			user = userRepository.findById(userId);
			if (!user.isPresent()) {
				data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
				data.setStatus(Constants.NOT_FOUND);
				return data;
			}
			/**
			 * To Check Mobile Length
			 */
			/*
			 * if (didCallFlowNumberDto.getDidNo().length() == Constants.MOBILE_LENGTH) {
			 * Logger.info("Request::: "+new
			 * Gson().toJson(didCallFlowNumberDto).toString()); inComingDidEntity =
			 * inComingDidRepository.findbyExtenContaining(didCallFlowNumberDto.getDidNo().
			 * trim());
			 * 
			 * } else { data.setMsg(Constants.INVALID_MOBILE);
			 * data.setStatus(Constants.NOT_FOUND); return data; }
			 */
			// if (inComingDidEntity != null) {
			didMappingEntity = new DidMappingEntity();
			didMappingEntity.setUsersId(user.get().getId());
			didMappingEntity.setDidNumber(didCallFlowNumberDto.getDidNo());
			/**
			 * To Check Ring Time
			 */
			if (didCallFlowNumberDto.getRingTime() <= 45 && didCallFlowNumberDto.getRingTime() >= 10) {
				didMappingEntity.setRingTime(didCallFlowNumberDto.getRingTime());
			} else {
				data.setMsg("Ring Time Should Be Greater Than 45 And Less Than 10 Secs.");
				data.setStatus(Constants.NOT_FOUND);
				return data;
			}

			if (Objects.isNull(didCallFlowNumberDto.getDidCli())) {
				didMappingEntity.setDidCli(null);
			} else {
				didMappingEntity.setDidCli(didCallFlowNumberDto.getDidCli());
			}
			if (Objects.isNull(didCallFlowNumberDto.getCallRecording())) {
				didMappingEntity.setCallRecording(null);
			} else {
				didMappingEntity.setCallRecording(didCallFlowNumberDto.getCallRecording());
			}
			if (Objects.isNull(didCallFlowNumberDto.getHolidayCheck())) {
				didMappingEntity.setHolidayCheck("0");
			} else {
				didMappingEntity.setHolidayCheck(didCallFlowNumberDto.getHolidayCheck());
			}
			if (Objects.isNull(didCallFlowNumberDto.getOfftimeDestinationType())) {
				didMappingEntity.setOfftimeDestinationType(null);
			} else {
				didMappingEntity.setOfftimeDestinationType(didCallFlowNumberDto.getOfftimeDestinationType());
			}
			if (Objects.isNull(didCallFlowNumberDto.getOfftimeDestinationTypeValue())) {
				didMappingEntity.setOfftimeDestinationTypeValue(null);
			} else {
				didMappingEntity.setOfftimeDestinationTypeValue(didCallFlowNumberDto.getOfftimeDestinationTypeValue());
			}
			if (Objects.isNull(didCallFlowNumberDto.getHolidayDestinationType())) {
				didMappingEntity.setHolidayDestinationType(null);
			} else {
				didMappingEntity.setHolidayDestinationType(didCallFlowNumberDto.getHolidayDestinationType());
			}
			if (Objects.isNull(didCallFlowNumberDto.getHolidayDestinationTypeValue())) {
				didMappingEntity.setHolidayDestinationTypeValue(null);
			} else {
				didMappingEntity.setHolidayDestinationTypeValue(didCallFlowNumberDto.getHolidayDestinationTypeValue());
			}
			if (Objects.isNull(didCallFlowNumberDto.getTimeGroupId())) {
				didMappingEntity.setTimeGroupId(null);
			} else {
				didMappingEntity.setTimeGroupId(didCallFlowNumberDto.getTimeGroupId());
			}

			/**
			 * This method is useful for set common fileds
			 */
			setCommonFields(didMappingEntity, user);
			/**
			 * send detail to create did call flow number mapping
			 */
			createCallFlowNumber(didCallFlowNumberDto, user);

			/*
			 * } else { data.setMsg("Invalid Did Number."); data.setStatus(204); return
			 * data; }
			 */
			didMappingList.add(didMappingEntity);

			saveMappingList = didMappingRepository.saveAll(didMappingList);
			if (saveMappingList.size() > 0) {
				data.setMsg(Constants.SUCCESSADD_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				Logger.info("***** DidCallFlowNumberMappingService.createDidMapping() Success Response******::"
						+ data.toString());
			} else {
				data.setMsg(Constants.FAIL_MSG);
				data.setStatus(Constants.REQUEST_FAILED);
				Logger.info("***** DidCallFlowNumberMappingService.createDidMapping() Failed Response******::"
						+ data.toString());
			}
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.NOT_FOUND);
			Logger.info("***** DidCallFlowNumberMappingService.createDidMapping() Exception Response******::"
					+ data.toString());

			e.printStackTrace();
		}
		return data;

	}

	/**
	 * This method is useful for creating Did Call Flow Number Mapping
	 * 
	 * @param didCallFlowNumberDto
	 * @param user
	 */
	public void createCallFlowNumber(DidCallFlowNumberDto didCallFlowNumberDto, Optional<Users> user) {
		Logger.info("***** Started DidCallFlowNumberMappingService.createCallFlowNumber() ******::");

		/**
		 * This Condition for officeNO
		 */
		MappingDidCallFlowNumber mappingDidNumberClowFlowNumber = null;
		List<MappingDidCallFlowNumber> mappingDidNumberClowFlowNumberList = new ArrayList<MappingDidCallFlowNumber>();
		System.out.println("......Office No size......." + didCallFlowNumberDto.getOfficeNo().size());
		for (String mapNo : didCallFlowNumberDto.getOfficeNo()) {
			mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
			mappingDidNumberClowFlowNumber.setDidNumber(didCallFlowNumberDto.getDidNo());
			mappingDidNumberClowFlowNumber.setMappingNumber(mapNo);
			mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.OFFICETIME.getId());
			setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user, didCallFlowNumberDto);
			mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
		}

		/**
		 * This Condition for fallBack No
		 */

		for (String fallBackNo : didCallFlowNumberDto.getFallbackNo()) {
			mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
			mappingDidNumberClowFlowNumber.setDidNumber(didCallFlowNumberDto.getDidNo());
			mappingDidNumberClowFlowNumber.setMappingNumber(fallBackNo);
			mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.FALLBACKNO.getId());
			setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user, didCallFlowNumberDto);
			mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
		}

		/**
		 * This Condition for OFF-HOUR Number
		 */

		for (String offHour : didCallFlowNumberDto.getNonOfficeNo()) {
			mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
			mappingDidNumberClowFlowNumber.setDidNumber(didCallFlowNumberDto.getDidNo());
			mappingDidNumberClowFlowNumber.setMappingNumber(offHour);
			mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.NONOFFICETIME.getId());
			setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user, didCallFlowNumberDto);
			mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
		}
		mappingDidNumberClowFlowNumberList = mappingDidNumberClowFlowNumberRepository
				.saveAll(mappingDidNumberClowFlowNumberList);
		Logger.info("***** Started DidCallFlowNumberMappingService.createCallFlowNumber() ******::"
				+ new Gson().toJson(mappingDidNumberClowFlowNumberList).toString());
	}

	public MappingDidCallFlowNumber setCommonFieldForCallFlowNumber(
			MappingDidCallFlowNumber mappingDidNumberCallFlowNumber, Optional<Users> user,
			DidCallFlowNumberDto didCallFlowNumberDto) {
		mappingDidNumberCallFlowNumber.setDnd(0);
		mappingDidNumberCallFlowNumber.setUsersId(user.get().getId());

		if (didCallFlowNumberDto.getCallPreferenceLocation().equalsIgnoreCase("INDIA")) {
			mappingDidNumberCallFlowNumber.setPreferredLoc(DidNumberPreperedLocation.INDIA.getId());
		} else {
			mappingDidNumberCallFlowNumber.setPreferredLoc(DidNumberPreperedLocation.INTERNATIONAL.getId());
		}

		mappingDidNumberCallFlowNumber.setCreatedDate(Utils.convertDateToString(new Date()));
		mappingDidNumberCallFlowNumber.setIsActive(String.valueOf(Status.ACTIVE.getId()));
		mappingDidNumberCallFlowNumber.setModifiedDate(Utils.convertDateToString(new Date()));
		mappingDidNumberCallFlowNumber.setModifiedBy(user.get().getUsername());
		mappingDidNumberCallFlowNumber.setCreatedBy(user.get().getUsername());
		return mappingDidNumberCallFlowNumber;
	}

	public void setCommonFields(DidMappingEntity didMappingEntity, Optional<Users> user) {
		didMappingEntity.setIsActive(String.valueOf(Status.ACTIVE.getId()));
		didMappingEntity.setModifiedDate(Utils.convertDateToString(new Date()));
		didMappingEntity.setModifiedBy(user.get().getUsername());
		didMappingEntity.setCreatedBy(user.get().getUsername());
		didMappingEntity.setCreatedDate(Utils.convertDateToString(new Date()));

	}

	public void updateCallFlowNumber(DidCallFlowNumberDto didCallFlowNumberDto, Optional<Users> user) {
		Logger.info("***** Started DidCallFlowNumberMappingService.updateCallFlowNumber() ******::");

		/**
		 * This Condition for officeNO
		 */
		MappingDidCallFlowNumber newMDNCFN = null;
		MappingDidCallFlowNumber mappingDidNumberClowFlowNumber = null;
		List<MappingDidCallFlowNumber> mappingDidNumberClowFlowNumberList = new ArrayList<MappingDidCallFlowNumber>();
		for (String mapNo : didCallFlowNumberDto.getOfficeNo()) {
			mappingDidNumberClowFlowNumber = mappingDidNumberClowFlowNumberRepository
					.findByDidNumberAndMappingNumber(didCallFlowNumberDto.getDidNo(), mapNo);
			if (Objects.nonNull(mappingDidNumberClowFlowNumber)) {
				mappingDidNumberClowFlowNumber.setDidNumber(didCallFlowNumberDto.getDidNo());
				mappingDidNumberClowFlowNumber.setMappingNumber(mapNo);
				mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.OFFICETIME.getId());
				updateCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user, didCallFlowNumberDto);
				mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
			} else {
				newMDNCFN = new MappingDidCallFlowNumber();
				newMDNCFN.setDidNumber(didCallFlowNumberDto.getDidNo());
				newMDNCFN.setMappingNumber(mapNo);
				newMDNCFN.setNumberType(DidNumberMappingType.OFFICETIME.getId());
				setCommonFieldForCallFlowNumber(newMDNCFN, user, didCallFlowNumberDto);
				mappingDidNumberClowFlowNumberList.add(newMDNCFN);
			}
		}

		/**
		 * This Condition for fallBack No
		 */

		for (String fallBackNo : didCallFlowNumberDto.getFallbackNo()) {
			mappingDidNumberClowFlowNumber = mappingDidNumberClowFlowNumberRepository
					.findByDidNumberAndMappingNumber(didCallFlowNumberDto.getDidNo(), fallBackNo);
			if (Objects.nonNull(mappingDidNumberClowFlowNumber)) {
				mappingDidNumberClowFlowNumber.setDidNumber(didCallFlowNumberDto.getDidNo());
				mappingDidNumberClowFlowNumber.setMappingNumber(fallBackNo);
				mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.OFFICETIME.getId());
				updateCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user, didCallFlowNumberDto);
				mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
			} else {
				newMDNCFN = new MappingDidCallFlowNumber();
				newMDNCFN.setDidNumber(didCallFlowNumberDto.getDidNo());
				newMDNCFN.setMappingNumber(fallBackNo);
				newMDNCFN.setNumberType(DidNumberMappingType.OFFICETIME.getId());
				setCommonFieldForCallFlowNumber(newMDNCFN, user, didCallFlowNumberDto);
				mappingDidNumberClowFlowNumberList.add(newMDNCFN);
			}
		}

		/**
		 * This Condition for OFF-HOUR Number
		 */

		for (String offHour : didCallFlowNumberDto.getNonOfficeNo()) {
			mappingDidNumberClowFlowNumber = mappingDidNumberClowFlowNumberRepository
					.findByDidNumberAndMappingNumber(didCallFlowNumberDto.getDidNo(), offHour);
			if (Objects.nonNull(mappingDidNumberClowFlowNumber)) {
				mappingDidNumberClowFlowNumber.setDidNumber(didCallFlowNumberDto.getDidNo());
				mappingDidNumberClowFlowNumber.setMappingNumber(offHour);
				mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.OFFICETIME.getId());
				updateCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user, didCallFlowNumberDto);
				mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
			} else {
				newMDNCFN = new MappingDidCallFlowNumber();
				newMDNCFN.setDidNumber(didCallFlowNumberDto.getDidNo());
				newMDNCFN.setMappingNumber(offHour);
				newMDNCFN.setNumberType(DidNumberMappingType.OFFICETIME.getId());
				setCommonFieldForCallFlowNumber(newMDNCFN, user, didCallFlowNumberDto);
				mappingDidNumberClowFlowNumberList.add(newMDNCFN);
			}
		}
		mappingDidNumberClowFlowNumberList = mappingDidNumberClowFlowNumberRepository
				.saveAll(mappingDidNumberClowFlowNumberList);
		Logger.info("***** Started DidCallFlowNumberMappingService.updateCallFlowNumber() ******::"
				+ new Gson().toJson(mappingDidNumberClowFlowNumberList).toString());
	}

	public MappingDidCallFlowNumber updateCommonFieldForCallFlowNumber(
			MappingDidCallFlowNumber mappingDidNumberCallFlowNumber, Optional<Users> user,
			DidCallFlowNumberDto didCallFlowNumberDto) {

		if (didCallFlowNumberDto.getCallPreferenceLocation().equalsIgnoreCase("INDIA")) {
			mappingDidNumberCallFlowNumber.setPreferredLoc(DidNumberPreperedLocation.INDIA.getId());
		} else {
			mappingDidNumberCallFlowNumber.setPreferredLoc(DidNumberPreperedLocation.INTERNATIONAL.getId());
		}

		mappingDidNumberCallFlowNumber.setModifiedDate(Utils.convertDateToString(new Date()));
		mappingDidNumberCallFlowNumber.setModifiedBy(user.get().getUsername());
		return mappingDidNumberCallFlowNumber;
	}
}
