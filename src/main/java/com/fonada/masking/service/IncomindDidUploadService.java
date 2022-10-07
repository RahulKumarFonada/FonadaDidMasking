package com.fonada.masking.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fonada.masking.bean.CommonFildsSetter;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.InComingDidEntity;
import com.fonada.masking.entity.LogsUser;
import com.fonada.masking.entity.Users;
import com.fonada.masking.exceptions.FileUploadException;
import com.fonada.masking.repository.InComingDidRepository;
import com.fonada.masking.repository.LogsUserRepository;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.utils.CSVHelper;
import com.fonada.masking.utils.CSVUtils;
import com.fonada.masking.utils.Utils;

@Service
public class IncomindDidUploadService {
	CommonFildsSetter c = new CommonFildsSetter();
	public static final Logger Logger = LoggerFactory.getLogger(IncomindDidUploadService.class);
	@Autowired
	InComingDidRepository repository;

	@Autowired
	LogsUserRepository logUserRepo;
	@Autowired
	UserRepository userRepository;

	@Async("processExecutor")
	public CompletableFuture<String> saveIncomingDidCSV(MultipartFile file, Optional<Users> userID)
			throws FileUploadException {
		Logger.info("***** Inside InComingDidUploadService.saveIncomingDidCSV() ******");

		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData;
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** InComingDidUploadService.saveIncomingDidCSV() Inside CSV ******");

				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.DID_MASKING_UPLOAD_SCHEMA_LENGTH);
			} else {
				Logger.info("***** InComingDidUploadService.saveIncomingDidCSV() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.DID_MASKING_UPLOAD_SCHEMA_LENGTH);
				response = CSVUtils.validateExcel(excelData, Constants.DID_MASKING_UPLOAD_SCHEMA_LENGTH);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains(Constants.SUCCESS_MSG)) {
				if (dataList.size() > 0) {
					response = createIncomingDidNo(dataList, file, userID);
				}
			}

		} catch (Exception ee) {
			response = "Got Exception::" + ee.getMessage();
			ee.printStackTrace();
			// throw new RuntimeException("Fail To Store CSV Data: " + ee.getMessage());
		}
		Logger.info("***** Successfully Executed InComingDidUploadService.saveIncomingDidCSV() And Got Response::******"
				+ response);
		return CompletableFuture.completedFuture(response);

	}

	private List<List<String>> parseCsvFile(List<String[]> csvData) {
		return csvData.stream().map(row -> new ArrayList<>(Arrays.asList(row)))
				.filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	public String createIncomingDidNo(List<List<String>> dataList, MultipartFile file, Optional<Users> user) {
		Logger.info("**** Inside InComingDidUploadService.createIncomingDidNo() *****");
		LogsUser logUser = null;
		List<InComingDidEntity> saveList = null;
		InComingDidEntity incomingDid = null;
		String response = "";
		int rowNumber = 0;
		int invalidCount = 0;
		List<InComingDidEntity> incomingDidList = new ArrayList<InComingDidEntity>();
		try {

			for (List<String> row : dataList) {
				logUser = new LogsUser();
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					incomingDid = new InComingDidEntity();
					setCommonFields(incomingDid, user);
					incomingDid.setUsersId(user.get().getId());
					if (StringUtils.isNotBlank(row.get(0))) {
						incomingDid.setContext(row.get(0).trim());
					}
					if (StringUtils.isNotBlank(row.get(1))) {
						incomingDid.setExten(row.get(1).trim());
					}
					if (StringUtils.isNotBlank(row.get(2))) {
						incomingDid.setPriority(row.get(2).trim());
					}
					if (StringUtils.isNotBlank(row.get(3))) {
						incomingDid.setApp(row.get(3).trim());
					}
					if (StringUtils.isNotBlank(row.get(4))) {
						incomingDid.setAppData(row.get(4).trim());
					}
					incomingDidList.add(incomingDid);

					if (StringUtils.isBlank(row.get(0)) || StringUtils.isBlank(row.get(1))
							|| StringUtils.isBlank(row.get(2)) || StringUtils.isBlank(row.get(3))
							|| StringUtils.isBlank(row.get(4))) {
						invalidCount++;

					}
				}
			}
			if (invalidCount > 0) {
				Logger.info("**** Saved LogUser InSide InComingDidUploadService.createIncomingDidNo() *****");

				logUser.setCreatedDate(Utils.convertDateToString(new Date()));
				logUser.setTotalCount(incomingDidList.size());
				logUser.setValidRecord(incomingDidList.size() - invalidCount);
				logUser.setInvalidRecord(invalidCount);
				logUser.setFileName(file.getOriginalFilename());
				logUser.setUsersId(user.get().getId());
				logUserRepo.save(logUser);
			}
			saveList = repository.saveAll(incomingDidList);

			if (saveList.size() > 0) {
				response = "InComing Did Upload Successfully Added.";
			} else {
				response = "InComing Did Upload Failed.";
			}

		} catch (Exception e) {
			response = "Got Exception::" + e.getMessage();
			e.printStackTrace();
		}
		return response;
	}

	public static <T> List<T> getListFromIterator(Iterator<T> iterator) {
		Iterable<T> iterable = () -> iterator;

		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}

	public ByteArrayInputStream load() {
		List<InComingDidEntity> incomingDid = repository.findAll();
		ByteArrayInputStream in = CSVHelper.writeInCSV(incomingDid);
		return in;
	}

	public List<InComingDidEntity> getAllIncoming() {
		return repository.findAll();
	}

	private List<List<String>> parseExcelFile(List<List<String>> csvData) {
		return csvData.stream().filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	public void setCommonFields(InComingDidEntity inComingDidEntity, Optional<Users> user) {
		inComingDidEntity.setIsActive("1");
		inComingDidEntity.setModifiedDate(Utils.convertDateToString(new Date()));
		inComingDidEntity.setModifiedBy(user.get().getUsername());
		inComingDidEntity.setCreatedBy(user.get().getUsername());
		inComingDidEntity.setCreatedDate(Utils.convertDateToString(new Date()));

	}
}
