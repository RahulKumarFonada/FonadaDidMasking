package com.fonada.masking.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.bean.DidCallFlowNumberDto;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.DidMappingEntity;
import com.fonada.masking.entity.MappingDidCallFlowNumber;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.DidMappingRepository;
import com.fonada.masking.repository.MappingDidNumberCallFlowNumberRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.service.DidCallFlowNumberMappingService;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/didMapping")
public class DidCallFlowNumberMappingController {
	@Value("${api.key}")
	String getApiKey;
	@Autowired
	private DidCallFlowNumberMappingService didCallFlowNumberMappingService;

	@Autowired
	private DidMappingRepository didMappingRepository;
	@Autowired
	private MappingDidNumberCallFlowNumberRepository mappingDidNumberCallFlowNumberRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/createDidMapping", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String createDidMapping(@RequestBody DidCallFlowNumberDto didCallFlowNumberDto,
			@RequestParam("userId") Integer userId, @RequestParam("apiKey") String apiKey) {

		if (!apiKey.equals(getApiKey)) {
			DataContainer data = new DataContainer();
			data.setMsg(Constants.UNAUTHORIZED);
			data.setStatus(Constants.INVALID_REQUEST);
			return new Gson().toJson(data).toString();
		}
		return new Gson().toJson(didCallFlowNumberMappingService.createDidMapping(didCallFlowNumberDto, userId))
				.toString();
	}

	@RequestMapping(value = "/getDidMappingList", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String findAllDidMapping() {
		DataContainer data = new DataContainer();
		List<DidMappingEntity> didMappingList = null;
		didMappingList = didMappingRepository.findAll().parallelStream().collect(Collectors.toList());
		if (didMappingList.size() > 0) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(didMappingList);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/getDidMappingByDidNumber", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String findByDidNumber(@RequestParam("didNumber") String didNumber) {
		DataContainer data = new DataContainer();
		DidMappingEntity didMappingEntity = didMappingRepository.findbyDidNumber(didNumber);
		if (Objects.nonNull(didMappingEntity)) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(didMappingEntity);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}

	/*
	 * This Api is useful for getting Did Mapping Call Flow Number List which is
	 * mapped on Did Number inside CallFlowNumber Table
	 */
	@RequestMapping(value = "/getDidMappingCallFlowNumberListByDidNumber", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String getAllCallFlowNumberByDidNumber(@RequestParam("didNumber") String didNumber) {
		DataContainer data = new DataContainer();
		List<MappingDidCallFlowNumber> mappingDidCallFlowNumberList = mappingDidNumberCallFlowNumberRepository
				.findAllByDidNumber(didNumber);
		if (mappingDidCallFlowNumberList.size() > Constants.ZERO_INT) {
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(mappingDidCallFlowNumberList);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}

	/**
	 * This Api is Useful If Check clowFlowNumber mapping then delete did_mapping
	 * and call_flow_number table
	 * 
	 * @param didNumber
	 * @return
	 */
	@RequestMapping(value = "/deleteCallFlowNumberDidMappingByDidNumber", method = RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteCallFlowNumberDidMappingByDidNumber(@RequestParam("didNumber") String didNumber) {
		DataContainer data = new DataContainer();
		List<MappingDidCallFlowNumber> mappingDidCallFlowNumberList = null;
		mappingDidCallFlowNumberList = mappingDidNumberCallFlowNumberRepository.findAllByDidNumber(didNumber);
		if (mappingDidCallFlowNumberList.size() > Constants.ZERO_INT) {
			didMappingRepository.deleteByDidNumber(didNumber);
			mappingDidNumberCallFlowNumberRepository.deleteAllByDidNumber(didNumber);
			data.setMsg(Constants.DELETE_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}

	// @RequestMapping(value = "/updateCallFlowNumberDidMappingById", method =
	// RequestMethod.PUT)
	@CrossOrigin("*")
	public String updateCallFlowNumberDidMappingById(@RequestBody MappingDidCallFlowNumber mappingDidCallFlowNumber,
			@RequestParam("apiKey") String apiKey) {
		DataContainer data = new DataContainer();
		if (!apiKey.equals(getApiKey)) {
			data.setMsg(Constants.UNAUTHORIZED);
			data.setStatus(Constants.INVALID_REQUEST);
			return new Gson().toJson(data).toString();
		}
		Optional<MappingDidCallFlowNumber> mappingDidCallFlowNumberList = mappingDidNumberCallFlowNumberRepository
				.findById(mappingDidCallFlowNumber.getId());
		if (mappingDidCallFlowNumberList.isPresent()) {
			if (!mappingDidCallFlowNumberList.get().getDidNumber().equals(mappingDidCallFlowNumber.getDidNumber())) {
				mappingDidCallFlowNumberList.get().setDidNumber(mappingDidCallFlowNumber.getDidNumber());
			}
			if (!mappingDidCallFlowNumberList.get().getDnd().equals(mappingDidCallFlowNumber.getDnd())) {
				mappingDidCallFlowNumberList.get().setDnd(mappingDidCallFlowNumber.getDnd());
			}
			if (!mappingDidCallFlowNumberList.get().getMappingNumber()
					.equals(mappingDidCallFlowNumber.getMappingNumber())) {
				mappingDidCallFlowNumberList.get().setMappingNumber(mappingDidCallFlowNumber.getMappingNumber());
			}
			if (!mappingDidCallFlowNumberList.get().getNumberType().equals(mappingDidCallFlowNumber.getNumberType())) {
				mappingDidCallFlowNumberList.get().setNumberType(mappingDidCallFlowNumber.getNumberType());
			}
			mappingDidCallFlowNumberList.get()
					.setModifiedBy(userRepository.findById(mappingDidCallFlowNumber.getUsersId()).get().getUsername());
			mappingDidCallFlowNumberList.get().setModifiedDate(Utils.convertDateToString(new Date()));
			mappingDidCallFlowNumberList.get().setUsersId(mappingDidCallFlowNumber.getUsersId());

			/// mappingDidNumberCallFlowNumberRepository.deleteByDidNumber( );
			data.setMsg(Constants.RECORDS_UPDATED);
			data.setStatus(Constants.REQUEST_SUCCESS);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/updateCallFlowNumberDidMapping", method = RequestMethod.PUT)
	@CrossOrigin("*")
	public String updateCallFlowNumberDidMapping(@RequestBody DidCallFlowNumberDto didCallFlowNumberDto,
			@RequestParam("userId") Integer userId, @RequestParam("apiKey") String apiKey) {
		DataContainer data = new DataContainer();
		Optional<Users> user = null;
		if (!apiKey.equals(getApiKey)) {
			data.setMsg(Constants.UNAUTHORIZED);
			data.setStatus(Constants.INVALID_REQUEST);
			return new Gson().toJson(data).toString();
		}
		user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setMsg(Constants.USER_DOSE_NOT_AVAILABLE);
			data.setStatus(Constants.NOT_FOUND);
			return new Gson().toJson(data).toString();
		}
		DidMappingEntity didMappingEntity = null;

		try {
			didMappingEntity = didMappingRepository.findbyDidNumber(didCallFlowNumberDto.getDidNo());
			if (Objects.nonNull(didMappingEntity)) {
				if (!didMappingEntity.getDidNumber().equals(didCallFlowNumberDto.getDidNo())) {
					didMappingEntity.setDidNumber(didCallFlowNumberDto.getDidNo());
				}
				if (!didMappingEntity.getDidCli().equals(didCallFlowNumberDto.getDidCli())) {
					didMappingEntity.setDidCli(didCallFlowNumberDto.getDidCli());
				}
				if (!didMappingEntity.getDidStatus().equals(didCallFlowNumberDto.getDidStatus())) {
					didMappingEntity.setDidStatus(didCallFlowNumberDto.getDidStatus());
				}
				if (!didMappingEntity.getTimeGroupId().equals(didCallFlowNumberDto.getTimeGroupId())) {
					didMappingEntity.setTimeGroupId(didCallFlowNumberDto.getTimeGroupId());
				}
				if (!didMappingEntity.getHolidayCheck().equals(didCallFlowNumberDto.getHolidayCheck())) {
					didMappingEntity.setHolidayCheck(didCallFlowNumberDto.getHolidayCheck());
				}
				if (!didMappingEntity.getRingTime().equals(didCallFlowNumberDto.getRingTime())) {
					didMappingEntity.setRingTime(didCallFlowNumberDto.getRingTime());
				}
				if (!didMappingEntity.getHolidayDestinationType()
						.equals(didCallFlowNumberDto.getHolidayDestinationType())) {
					didMappingEntity.setHolidayDestinationType(didCallFlowNumberDto.getHolidayDestinationType());
				}
				if (!didMappingEntity.getHolidayDestinationTypeValue()
						.equals(didCallFlowNumberDto.getHolidayDestinationType())) {
					didMappingEntity
							.setHolidayDestinationTypeValue(didCallFlowNumberDto.getHolidayDestinationTypeValue());
				}
				if (!didMappingEntity.getOfftimeDestinationType()
						.equals(didCallFlowNumberDto.getOfftimeDestinationType())) {
					didMappingEntity.setOfftimeDestinationType(didCallFlowNumberDto.getOfftimeDestinationType());
				}
				if (!didMappingEntity.getOfftimeDestinationTypeValue()
						.equals(didCallFlowNumberDto.getOfftimeDestinationTypeValue())) {
					didMappingEntity
							.setOfftimeDestinationTypeValue(didCallFlowNumberDto.getOfftimeDestinationTypeValue());
				}
				didCallFlowNumberMappingService.updateCallFlowNumber(didCallFlowNumberDto, user);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setMsg(Constants.SUCCESS_MSG);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			}
		} catch (Exception e) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.FAIL_MSG + "::" + e.getMessage());
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	// @RequestMapping(value = "/deleteCallFlowNumberDidMapping", method =
	// RequestMethod.POST)
	@CrossOrigin("*")
	public String deleteCallFlowNumberDidMapping(@RequestBody DidCallFlowNumberDto didCallFlowNumberDto) {
		DataContainer data = new DataContainer();
		List<MappingDidCallFlowNumber> mappingDidCallFlowNumberList = null;
		DidMappingEntity didMappingEntity = null;
		didMappingEntity = didMappingRepository.findbyDidNumber(didCallFlowNumberDto.getDidNo());
		if (Objects.nonNull(didMappingEntity)) {
			mappingDidCallFlowNumberList = mappingDidNumberCallFlowNumberRepository
					.findAllByDidNumber(didCallFlowNumberDto.getDidNo());
			if (mappingDidCallFlowNumberList.size() > Constants.ZERO_INT) {
				mappingDidNumberCallFlowNumberRepository.deleteAllByDidNumber(didCallFlowNumberDto.getDidNo());
				didMappingRepository.deleteByDidNumber(didCallFlowNumberDto.getDidNo());
				data.setMsg(Constants.DELETE_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
			} else {
				data.setStatus(Constants.REQUEST_FAILED);
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			}
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}

	@CrossOrigin("*")
	@RequestMapping(value = "/countMappingNumberByDidMapping", method = RequestMethod.GET)
	public String countMappingNumberByDidMapping(@RequestParam("didNumber") String didNumber) {
		DataContainer data = new DataContainer();
		HashMap<String, Integer> mappingCount = new HashMap<String, Integer>();
		Integer numberMappingCount = 0;
		numberMappingCount = mappingDidNumberCallFlowNumberRepository.countByDidNumber(didNumber);
		if (numberMappingCount != 0) {
			mappingCount.put("totalCountDidCallFlowNumber", numberMappingCount);
			data.setMsg(Constants.SUCCESS_MSG);
			data.setStatus(Constants.REQUEST_SUCCESS);
			data.setData(mappingCount);
		} else {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
		}
		return new Gson().toJson(data).toString();
	}
}
