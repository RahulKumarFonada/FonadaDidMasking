package com.fonada.masking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.fonada.masking.entity.OffHourManagement;
import com.fonada.masking.entity.Users;
import com.fonada.masking.exceptions.BadRequestException;
import com.fonada.masking.repository.OffHourRepository;
import com.fonada.masking.utils.CSVHelper;
import com.fonada.masking.utils.CSVUtils;

@Service
public class UploadOffHourService {

	@Autowired
	private OffHourRepository offHourRepository;
	public static final Logger Logger = LoggerFactory.getLogger(UploadOffHourService.class);

	@Async("processExecutor")
	public CompletableFuture<String> saveOffHour(MultipartFile file, Optional<Users> userId)
			throws BadRequestException {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started UploadOffHourService.saveOffHour() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** UploadOffHourService.saveOffHour() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.TIME_GROUP_LENGHT);
			} else {
				Logger.info("***** UploadOffHourService.saveOffHour() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.TIME_GROUP_LENGHT);
				response = CSVUtils.validateExcel(excelData, Constants.TIME_GROUP_LENGHT);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info("***** UploadOffHourService.saveOffHour() Got Size After ParseExcelFile******::"
							+ dataList.size());

					response = createOffHour(dataList, file, userId);
					System.out.println("response::" + response);
					Logger.info("***** UploadOffHourService.saveOffHour() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.DID_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
			// throw new RuntimeException("Fail To Store Given File." + ee.getMessage());
		}
		Logger.info("***** Successfully Executed UploadOffHourService.saveOffHour() Got Response ******" + response);

		return CompletableFuture.completedFuture(response);

	}

	public String createOffHour(List<List<String>> dataList, MultipartFile file, Optional<Users> user)
			throws BadRequestException {
		Logger.info("***** Inside UploadOffHourService.createOffHour() *****");
		int rowNumber = 0;
		OffHourManagement offHour = null;
		List<OffHourManagement> offHourList = new ArrayList<OffHourManagement>();
		List<OffHourManagement> savedOffHour = null;
		String response = "";
		try {
			for (List<String> row : dataList) {
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					offHour = new OffHourManagement();
					offHour.setIsGlobal(Integer.valueOf(row.get(0)));
					offHour.setOffHourstartDate(row.get(1));
					offHour.setOffHourEndDate(row.get(2));
					offHour.setDescription(row.get(3));
					offHourList.add(offHour);
				}

			}
			savedOffHour = offHourRepository.saveAll(offHourList);
			if (savedOffHour.size() > 0) {
				response = Constants.SUCCESSADD_MSG;
			} else {
				response = Constants.DATANOTSAVED;
			}
			Logger.info("***** Successfully Added UploadOffHourService.createOffHour() *****::" + response);
		} catch (Exception e) {
			response = "Got Exception While Saving Time Group::" + e.getMessage();
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
