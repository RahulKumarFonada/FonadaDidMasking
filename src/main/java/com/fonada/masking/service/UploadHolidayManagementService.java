package com.fonada.masking.service;

import java.text.SimpleDateFormat;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.HolidayEntity;
import com.fonada.masking.entity.Users;
import com.fonada.masking.exceptions.BadRequestException;
import com.fonada.masking.repository.HolidayRepository;
import com.fonada.masking.utils.CSVHelper;
import com.fonada.masking.utils.CSVUtils;
import com.fonada.masking.utils.Utils;

@Service
public class UploadHolidayManagementService {

	@Autowired
	private HolidayRepository holidayRepository;

	public static final Logger Logger = LoggerFactory.getLogger(UploadHolidayManagementService.class);

	@Async("processExecutor")
	public CompletableFuture<String> saveHolidayUploadFile(MultipartFile file, Optional<Users> userId)
			throws BadRequestException {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started UploadHolidayManagementService.saveHolidayUploadFile() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** UploadHolidayManagementService.saveHolidayUploadFile() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.DID_BLACKLIST_NUMBER_SCHEMA_LENGHT);
			} else {
				Logger.info("***** UploadHolidayManagementService.saveHolidayUploadFile() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.DID_BLACKLIST_NUMBER_SCHEMA_LENGHT);
				response = CSVUtils.validateExcel(excelData, Constants.DID_BLACKLIST_NUMBER_SCHEMA_LENGHT);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info(
							"***** UploadHolidayManagementService.saveHolidayUploadFile() Got Size After ParseExcelFile******::"
									+ dataList.size());

					response = createHoliday(dataList, file, userId);
					System.out.println("response::" + response);
					Logger.info(
							"***** UploadHolidayManagementService.saveHolidayUploadFile() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.DID_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
		}
		Logger.info(
				"***** Successfully Executed UploadHolidayManagementService.saveHolidayUploadFile() Got Response ******"
						+ response);

		return CompletableFuture.completedFuture(response);

	}

	public String createHoliday(List<List<String>> dataList, MultipartFile file, Optional<Users> user)
			throws BadRequestException {
		Logger.info("***** Inside UploadHolidayManagementService.createHoliday() *****");
		int rowNumber = 0;
		HolidayEntity holiday = null;
		List<HolidayEntity> holidayList = new ArrayList<HolidayEntity>();
		List<HolidayEntity> saveholidayList = null;
		String response = "";
		try {
			for (List<String> row : dataList) {
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					holiday = new HolidayEntity();

					if (StringUtils.isNotBlank(row.get(0))) {
						holiday.setHolidayDate(row.get(0));
					}
					if (StringUtils.isNotBlank(row.get(1))) {
						holiday.setIsGlobal(Integer.valueOf(row.get(1)));
					}
					if (StringUtils.isNotBlank(row.get(2))) {
						holiday.setDidNo(row.get(2));
					}

					holiday.setUsersId(user.get().getId());
					holiday.setCreatedBy(user.get().getUsername());
					holiday.setModifyBy(user.get().getUsername());
					holiday.setModifyDate(Utils.convertDateToString(new Date()));
					holiday.setCreatedDate(Utils.convertDateToString(new Date()));
					holidayList.add(holiday);
				}

			}
			saveholidayList = holidayRepository.saveAll(holidayList);
			if (saveholidayList.size() > 0) {
				response = Constants.SUCCESSADD_MSG;
			} else {
				response = Constants.DATANOTSAVED;
			}
			Logger.info("***** Successfully Added UploadHolidayManagementService.createHoliday() *****::" + response);
		} catch (Exception e) {
			response = "Got Exception While Saving BalckList Number::" + e.getMessage();
			Logger.info("*****Got Exception UploadHolidayManagementService.createHoliday() *****::" + response);

			e.printStackTrace();
		}
		return response;
	}

	private List<List<String>> parseCsvFile(List<String[]> csvData) {
		return csvData.stream().map(row -> new ArrayList<>(Arrays.asList(row)))
				.filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	private List<List<String>> parseExcelFile(List<List<String>> csvData) {
		return csvData.stream().filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

}
