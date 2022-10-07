package com.fonada.masking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fonada.masking.common.Constants;
import com.fonada.masking.common.DidNumberMappingType;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.DidMappingEntity;
import com.fonada.masking.entity.InComingDidEntity;
import com.fonada.masking.entity.LogsUser;
import com.fonada.masking.entity.MappingDidCallFlowNumber;
import com.fonada.masking.entity.Users;
import com.fonada.masking.exceptions.BadRequestException;
import com.fonada.masking.repository.DidMappingRepository;
import com.fonada.masking.repository.InComingDidRepository;
import com.fonada.masking.repository.LogsUserRepository;
import com.fonada.masking.repository.MappingDidNumberCallFlowNumberRepository;
import com.fonada.masking.utils.CSVHelper;
import com.fonada.masking.utils.CSVUtils;
import com.fonada.masking.utils.DidNumberPreperedLocation;
import com.fonada.masking.utils.Status;
import com.fonada.masking.utils.Utils;

@Service
public class DidMappingUploadService {

	@Value("${ring.Time}")
	String ringTime;

	public static final Logger Logger = LoggerFactory.getLogger(DidMappingUploadService.class);

	@Autowired
	private LogsUserRepository logUserRepo;
	@Autowired
	private InComingDidRepository inComingDidRepository;
	@Autowired
	private MappingDidNumberCallFlowNumberRepository mappingDidNumberClowFlowNumberRepository;
	@Autowired
	private DidMappingRepository didMappingRepository;

	@Async("processExecutor")
	public CompletableFuture<String> saveDidMappingCSV(MultipartFile file, Optional<Users> userId)
			throws BadRequestException {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started DidMappingUploadService.saveDidMappingCSV() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** DidMappingUploadService.saveDidMappingCSV() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.DID_MAPPING_SCHEMA_LENGTH);
			} else {
				Logger.info("***** DidMappingUploadService.saveDidMappingCSV() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.DID_MAPPING_SCHEMA_LENGTH);
				response = CSVUtils.validateExcel(excelData, Constants.DID_MAPPING_SCHEMA_LENGTH);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info(
							"***** DidMappingUploadService.saveDidMappingCSV() Got Size After ParseExcelFile******::"
									+ dataList.size());

					response = createMappingDidNumberClowFlowNumber(dataList, file, userId);
					System.out.println("response::" + response);
					Logger.info(
							"***** DidMappingUploadService.saveDidMappingCSV() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.DID_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
			// throw new RuntimeException("Fail To Store Given File." + ee.getMessage());
		}
		Logger.info("***** Successfully Executed DidMappingUploadService.saveDidMappingCSV() Got Response ******"
				+ response);

		return CompletableFuture.completedFuture(response);

	}

	private List<List<String>> parseExcelFile(List<List<String>> csvData) {
		return csvData.stream().filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	private List<List<String>> parseCsvFile(List<String[]> csvData) {
		return csvData.stream().map(row -> new ArrayList<>(Arrays.asList(row)))
				.filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	/**
	 * This method is useful for saving didMapping and call_flow_number after parsing excel file
	 * 
	 * @param dataList
	 * @param file
	 * @param user
	 * @return
	 * @throws BadRequestException
	 */
	public String createMappingDidNumberClowFlowNumber(List<List<String>> dataList, MultipartFile file,
			Optional<Users> user) throws BadRequestException {
		Logger.info("***** Inside DidMappingUploadService.createMappingDidNumberClowFlowNumber() *****");
		LogsUser logUser = null;
		DidMappingEntity didMappingEntity = null;
		int rowNumber = 0;
		int invalidCount = 0;
		InComingDidEntity inComingDidEntity = null;
		MappingDidCallFlowNumber mappingDidNumberClowFlowNumber = null;
		List<MappingDidCallFlowNumber> mappingDidNumberClowFlowNumberList = null;
		List<DidMappingEntity> didMappingList = new ArrayList<DidMappingEntity>();
		List<DidMappingEntity> saveMappingList = new ArrayList<DidMappingEntity>();
		String response = "";
		try {
			for (List<String> row : dataList) {
				logUser = new LogsUser();
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					inComingDidEntity = inComingDidRepository.findbyExtenContaining(row.get(0));
					if (inComingDidEntity != null) {
						// Logger.info("***** Row Data After Converting *****" + new
						// Gson().toJson(row).toString());
						didMappingEntity = new DidMappingEntity();
						didMappingEntity.setUsersId(user.get().getId());
						didMappingEntity.setRingTime(Integer.parseInt(ringTime));

						/**
						 * This method is useful for set common fileds
						 */
						setCommonFields(didMappingEntity, user);

						if (StringUtils.isNotBlank(row.get(0))) {
							didMappingEntity.setDidNumber(row.get(0).trim());
						}
						if (StringUtils.isNotBlank(row.get(1))) {
							didMappingEntity.setDidStatus(Integer.valueOf(row.get(1)));
						}
						if (StringUtils.isNotBlank(row.get(2))) {
							didMappingEntity.setDidCli(row.get(2));
						}
						if (StringUtils.isNotBlank(row.get(3))) {
							didMappingEntity.setCallRecording(row.get(3));
						}
						if (StringUtils.isNotBlank(row.get(4))) {
							didMappingEntity.setHolidayCheck(row.get(4));
						}
						if (StringUtils.isNotBlank(row.get(5))) {
							didMappingEntity.setTimeGroupId(row.get(5));
						}
						if (StringUtils.isNotBlank(row.get(6))) {
							didMappingEntity.setOfftimeDestinationType(row.get(6));
						}
						if (StringUtils.isNotBlank(row.get(7))) {
							didMappingEntity.setOfftimeDestinationTypeValue(row.get(7));
						}
						if (StringUtils.isNotBlank(row.get(8))) {
							didMappingEntity.setHolidayDestinationType(row.get(8));
						}
						if (StringUtils.isNotBlank(row.get(9))) {
							didMappingEntity.setHolidayDestinationTypeValue(row.get(9));
						}
						mappingDidNumberClowFlowNumberList = new ArrayList<MappingDidCallFlowNumber>();

						/**
						 * This Condition for MappedNo
						 */
						if (StringUtils.isNotBlank(row.get(10))) {
							String mappedNo = row.get(10);
							String[] numberParts = mappedNo.split(";");
							for (String mapNo : numberParts) {
								mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
								mappingDidNumberClowFlowNumber.setDidNumber(row.get(0));
								mappingDidNumberClowFlowNumber.setMappingNumber(mapNo);
								mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.OFFICETIME.getId());
								setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user);
								mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
							}
						}
						/**
						 * This Condition for fallBack No
						 */
						if (StringUtils.isNotBlank(row.get(11))) {
							String fallbackNo = row.get(11);
							String[] fallbackNoList = fallbackNo.split(";");
							for (String fallBackNo : fallbackNoList) {
								mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
								mappingDidNumberClowFlowNumber.setDidNumber(row.get(0));
								mappingDidNumberClowFlowNumber.setMappingNumber(fallBackNo);
								mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.FALLBACKNO.getId());
								setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user);
								mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
							}
						}
						/**
						 * This Condition for OFF-HOUR Number
						 */
						if (StringUtils.isNotBlank(row.get(12))) {
							String offHourNo = row.get(12);
							String[] offHourNoList = offHourNo.split(";");
							for (String offHour : offHourNoList) {
								mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
								mappingDidNumberClowFlowNumber.setDidNumber(row.get(0));
								mappingDidNumberClowFlowNumber.setMappingNumber(offHour);
								mappingDidNumberClowFlowNumber
										.setNumberType(DidNumberMappingType.NONOFFICETIME.getId());
								setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user);
								mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
							}
						}
						/**
						 * This Condition For HOLIDAY NUMBER
						 */
						if (StringUtils.isNotBlank(row.get(13))) {
							String holidayNumberString = row.get(13);
							String[] holidayNumberList = holidayNumberString.split(";");
							for (String holidayNumber : holidayNumberList) {
								mappingDidNumberClowFlowNumber = new MappingDidCallFlowNumber();
								mappingDidNumberClowFlowNumber.setDidNumber(row.get(0));
								mappingDidNumberClowFlowNumber.setMappingNumber(holidayNumber);
								mappingDidNumberClowFlowNumber.setNumberType(DidNumberMappingType.HOLIDAYNO.getId());
								setCommonFieldForCallFlowNumber(mappingDidNumberClowFlowNumber, user);
								mappingDidNumberClowFlowNumberList.add(mappingDidNumberClowFlowNumber);
							}
						}
						mappingDidNumberClowFlowNumberRepository.saveAll(mappingDidNumberClowFlowNumberList);

						didMappingList.add(didMappingEntity);
						if (StringUtils.isBlank(row.get(0)) || StringUtils.isBlank(row.get(10))
								|| StringUtils.isBlank(row.get(11)) || StringUtils.isBlank(row.get(12))
								|| StringUtils.isBlank(row.get(13))) {
							invalidCount++;
						}
					} else {
						response = "Given Did No Does Not Exist::" + row.get(0);
						return response;
					}
				}
			}
			if (invalidCount > 0) {
				Logger.info(
						"***** DidMappingUploadService.createMappingDidNumberClowFlowNumber() Going To Save Log Users *****");
				logUser.setCreatedDate(Utils.convertDateToString(new Date()));
				logUser.setTotalCount(didMappingList.size());
				logUser.setValidRecord(didMappingList.size() - invalidCount);
				logUser.setInvalidRecord(invalidCount);
				logUser.setFileName(file.getOriginalFilename());
				logUser.setUsersId(user.get().getId());
				logUserRepo.save(logUser);
			}
			saveMappingList = didMappingRepository.saveAll(didMappingList);
			if (saveMappingList.size() > 0) {
				response = "Did Mapping Successfully Added.";
				Logger.info(
						"***** DidMappingUploadService.createMappingDidNumberClowFlowNumber() Success Response******::"
								+ response);
			} else {
				response = "Did Mapping Could Not Saved! Try Again.";
				Logger.info(
						"***** DidMappingUploadService.createMappingDidNumberClowFlowNumber() Failed Response******::"
								+ response);
			}
		} catch (Exception e) {
			response = "Got Exception::" + e.getMessage();
			e.printStackTrace();
		}
		return response;
	}

	public MappingDidCallFlowNumber setCommonFieldForCallFlowNumber(
			MappingDidCallFlowNumber mappingDidNumberCallFlowNumber, Optional<Users> user) {
		mappingDidNumberCallFlowNumber.setDnd(0);
		mappingDidNumberCallFlowNumber.setUsersId(user.get().getId());
		mappingDidNumberCallFlowNumber.setPreferredLoc(DidNumberPreperedLocation.INDIA.getId());
		mappingDidNumberCallFlowNumber.setCreatedDate(Utils.convertDateToString(new Date()));
		mappingDidNumberCallFlowNumber.setIsActive(String.valueOf(Status.ACTIVE.getId()));
		mappingDidNumberCallFlowNumber.setModifiedDate(Utils.convertDateToString(new Date()));
		mappingDidNumberCallFlowNumber.setModifiedBy(user.get().getUsername());
		mappingDidNumberCallFlowNumber.setCreatedBy(user.get().getUsername());
		mappingDidNumberCallFlowNumber.setCreatedDate(Utils.convertDateToString(new Date()));
		return mappingDidNumberCallFlowNumber;
	}

	public void setCommonFields(DidMappingEntity didMappingEntity, Optional<Users> user) {
		didMappingEntity.setIsActive(String.valueOf(Status.ACTIVE.getId()));
		didMappingEntity.setModifiedDate(Utils.convertDateToString(new Date()));
		didMappingEntity.setModifiedBy(user.get().getUsername());
		didMappingEntity.setCreatedBy(user.get().getUsername());
		didMappingEntity.setCreatedDate(Utils.convertDateToString(new Date()));

	}

	/**
	 * Delete Did Mapping From did_mapping and call_flow_number
	 * @param file
	 * @param userId
	 * @return
	 * @throws BadRequestException
	 */
	@Async("processExecutor")
	public CompletableFuture<String> deleteDidMappingByCSV(MultipartFile file, Optional<Users> userId)
			throws BadRequestException {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started DidMappingUploadService.deleteDidMappingByCSV() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** DidMappingUploadService.deleteDidMappingByCSV() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.DID_MAPPING_SCHEMA_LENGTH);
			} else {
				Logger.info("***** DidMappingUploadService.deleteDidMappingByCSV() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.DID_MAPPING_SCHEMA_LENGTH);
				response = CSVUtils.validateExcel(excelData, Constants.DID_MAPPING_SCHEMA_LENGTH);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info(
							"***** DidMappingUploadService.deleteDidMappingByCSV() Got Size After ParseExcelFile******::"
									+ dataList.size());

					deleteMappingDidNumberClowFlowNumber(dataList, file, userId);
					response = "SuccessFully Deleted";
					System.out.println("response::" + response);
					Logger.info(
							"***** DidMappingUploadService.deleteDidMappingByCSV() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.DID_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
			// throw new RuntimeException("Fail To Store Given File." + ee.getMessage());
		}
		Logger.info("***** Successfully Executed DidMappingUploadService.deleteDidMappingByCSV() Got Response ******"
				+ response);

		return CompletableFuture.completedFuture(response);

	}

	public void deleteMappingDidNumberClowFlowNumber(List<List<String>> dataList, MultipartFile file,
			Optional<Users> user) throws BadRequestException {
		Logger.info("***** Inside DidMappingUploadService.deleteMappingDidNumberClowFlowNumber() *****");
		int rowNumber = 0;
		List<MappingDidCallFlowNumber> mappingDidCallFlowNumberList = null;
		try {
			for (List<String> row : dataList) {
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					mappingDidCallFlowNumberList = mappingDidNumberClowFlowNumberRepository
							.findAllByDidNumber(row.get(0).trim());
					if (mappingDidCallFlowNumberList.size() > Constants.ZERO_INT) {
						mappingDidNumberClowFlowNumberRepository.deleteAllByDidNumber(row.get(0).trim());
						didMappingRepository.deleteAllByDidNumber(row.get(0).trim());
					}

				}

			}

			Logger.info(
					"***** Successfully Deleted DidMappingUploadService.deleteMappingDidNumberClowFlowNumber() *****");
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
