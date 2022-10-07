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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.DidBlockedNumberEntity;
import com.fonada.masking.entity.Users;
import com.fonada.masking.exceptions.BadRequestException;
import com.fonada.masking.repository.DidBloackedNumberRepository;
import com.fonada.masking.utils.CSVHelper;
import com.fonada.masking.utils.CSVUtils;
import com.fonada.masking.utils.Utils;

@Service
public class UploadBlockedNumberService {

	@Autowired
	private DidBloackedNumberRepository blockedNumberRepository;

	public static final Logger Logger = LoggerFactory.getLogger(UploadBlockedNumberService.class);

	@Async("processExecutor")
	public CompletableFuture<String> saveDidBlackListNumberUploadFile(MultipartFile file, Optional<Users> userId)
			throws BadRequestException {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started UploadBlockedNumberService.saveDidBlackListNumberUploadFile() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** UploadBlockedNumberService.saveDidBlackListNumberUploadFile() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.DID_BLACKLIST_NUMBER_SCHEMA_LENGHT);
			} else {
				Logger.info("***** UploadBlockedNumberService.saveDidBlackListNumberUploadFile() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.DID_BLACKLIST_NUMBER_SCHEMA_LENGHT);
				response = CSVUtils.validateExcel(excelData, Constants.DID_BLACKLIST_NUMBER_SCHEMA_LENGHT);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info(
							"***** UploadBlockedNumberService.saveDidBlackListNumberUploadFile() Got Size After ParseExcelFile******::"
									+ dataList.size());

					response = createblackList(dataList, file, userId);
					System.out.println("response::" + response);
					Logger.info(
							"***** UploadBlockedNumberService.saveDidBlackListNumberUploadFile() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.DID_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
		}
		Logger.info(
				"***** Successfully Executed UploadBlockedNumberService.saveDidBlackListNumberUploadFile() Got Response ******"
						+ response);

		return CompletableFuture.completedFuture(response);

	}

	public String createblackList(List<List<String>> dataList, MultipartFile file, Optional<Users> user)
			throws BadRequestException {
		Logger.info("***** Inside UploadBlockedNumberService.createblackList() *****");
		int rowNumber = 0;
		DidBlockedNumberEntity didBlockedNumberEntity = null;
		List<DidBlockedNumberEntity> didBlockedNumberList = new ArrayList<DidBlockedNumberEntity>();
		List<DidBlockedNumberEntity> savedidBlockedNumberList = null;
		String response = "";
		try {
			for (List<String> row : dataList) {
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					didBlockedNumberEntity = new DidBlockedNumberEntity();

					if (StringUtils.isNotBlank(row.get(0))) {
						didBlockedNumberEntity.setCliNo(row.get(0));
					}
					if (StringUtils.isNotBlank(row.get(1))) {
						didBlockedNumberEntity.setDidNo(row.get(1));
					}
					if (StringUtils.isNotBlank(row.get(2))) {
						didBlockedNumberEntity.setIsActive(row.get(2));
					}
					if (StringUtils.isNotBlank(row.get(4))) {
						didBlockedNumberEntity.setDnd(Integer.valueOf(row.get(4)));
					}
					if (StringUtils.isNotBlank(row.get(3))) {
						didBlockedNumberEntity.setIsGlobal(Integer.valueOf(row.get(3)));
					}
					didBlockedNumberEntity.setUsersId(user.get().getId());
					didBlockedNumberEntity.setCreatedBy(user.get().getUsername());
					didBlockedNumberEntity.setModifyBy(user.get().getUsername());
					didBlockedNumberEntity.setModifyDate(Utils.convertDateToString(new Date()));
					didBlockedNumberEntity.setCreatedDate(Utils.convertDateToString(new Date()));
					didBlockedNumberList.add(didBlockedNumberEntity);
				}

			}
			savedidBlockedNumberList = blockedNumberRepository.saveAll(didBlockedNumberList);
			if (savedidBlockedNumberList.size() > 0) {
				response = Constants.SUCCESSADD_MSG;
			} else {
				response = Constants.DATANOTSAVED;
			}
			Logger.info("***** Successfully Added UploadBlockedNumberService.createblackList() *****::" + response);
		} catch (Exception e) {
			response = "Got Exception While Saving BalckList Number::" + e.getMessage();
			Logger.info("*****Got Exception UploadBlockedNumberService.createblackList() *****::" + response);

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
