package com.fonada.masking.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.InComingDidEntity;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.service.DidMappingUploadService;
import com.fonada.masking.service.IncomindDidUploadService;
import com.fonada.masking.service.TimeGroupUploadService;
import com.fonada.masking.service.UploadBlockedNumberService;
import com.fonada.masking.service.UploadHolidayManagementService;
import com.fonada.masking.service.UploadOffHourService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

	public static final Logger Logger = LoggerFactory.getLogger(FileUploadController.class);
	@Autowired
	private IncomindDidUploadService fileService;
	@Autowired
	private DidMappingUploadService didMappingUploadService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TimeGroupUploadService timeGroupUploadService;

	@Autowired
	private UploadBlockedNumberService uploadBlockedNumberService;

	@Autowired
	private UploadHolidayManagementService uploadHolidayManagementService;

	@Autowired
	private UploadOffHourService uploadOffHourService;

	@PostMapping(value = "/uploadDidMaskingNumberFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String uploadInComingDidFile(@RequestPart("file") MultipartFile file, @RequestParam("userId") Integer userId)
			throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.uploadInComingDidFile() *****");

		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got User Not Found*****"
					+ data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = fileService.saveIncomingDidCSV(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Success*****"
							+ data.toString());
				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Faild*****"
							+ data.toString());
				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!");
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Exception*****"
						+ data.toString());
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Invalid Format*****"
					+ data.toString());
		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		Logger.info("**** Successfully Executed Inside  FileUploadController.uploadInComingDidFile() *****");
		return new Gson().toJson(data).toString();

	}

	@PostMapping(value = "/uploadDidMappingFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String uploadDidMappingFile(@RequestPart("file") MultipartFile file, @RequestParam("userId") Integer userId)
			throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.uploadDidMappingFile() *****");
		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info("**** Inside  FileUploadController.uploadDidMappingFile() Got User Not Found*****"
					+ data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = didMappingUploadService.saveDidMappingCSV(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.uploadDidMappingFile() Got Success*****"
							+ data.toString());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.uploadDidMappingFile() Got Faild*****"
							+ data.toString());

				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!" + e.getMessage());
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
				Logger.info("**** Inside  FileUploadController.uploadDidMappingFile() Got Exception*****"
						+ data.toString());

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info("**** Inside  FileUploadController.uploadDidMappingFile() Got Invalid Format*****"
					+ data.toString());

		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		return new Gson().toJson(data).toString();

	}

	@PostMapping(value = "/deleteDidMappingFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String deleteDidMappingFile(@RequestPart("file") MultipartFile file, @RequestParam("userId") Integer userId)
			throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.deleteDidMappingFile() *****");
		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info("**** Inside  FileUploadController.deleteDidMappingFile() Got User Not Found*****"
					+ data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = didMappingUploadService.deleteDidMappingByCSV(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.deleteDidMappingFile() Got Success*****"
							+ data.toString());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.deleteDidMappingFile() Got Faild*****"
							+ data.toString());

				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!" + e.getMessage());
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
				Logger.info("**** Inside  FileUploadController.deleteDidMappingFile() Got Exception*****"
						+ data.toString());

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info("**** Inside  FileUploadController.deleteDidMappingFile() Got Invalid Format*****"
					+ data.toString());

		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		return new Gson().toJson(data).toString();

	}

	@PostMapping(value = "/timeGroupUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String timeGroupUpload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Integer userId)
			throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.timeGroupUpload() *****");
		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info(
					"**** Inside  FileUploadController.timeGroupUpload() Got User Not Found*****" + data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = timeGroupUploadService.saveTimeGroupUploadFile(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info(
							"**** Inside  FileUploadController.timeGroupUpload() Got Success*****" + data.toString());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.timeGroupUpload() Got Faild*****" + data.toString());

				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!" + e.getMessage());
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
				Logger.info("**** Inside  FileUploadController.timeGroupUpload() Got Exception*****" + data.toString());

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info(
					"**** Inside  FileUploadController.timeGroupUpload() Got Invalid Format*****" + data.toString());

		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		return new Gson().toJson(data).toString();

	}

	@PostMapping(value = "/didBlackListNumberUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String didBlackListNumberUpload(@RequestPart("file") MultipartFile file,
			@RequestParam("userId") Integer userId) throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.deleteDidMappingFile() *****");
		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info("**** Inside  FileUploadController.didBlackListNumberUpload() Got User Not Found*****"
					+ data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = uploadBlockedNumberService.saveDidBlackListNumberUploadFile(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.didBlackListNumberUpload() Got Success*****"
							+ data.toString());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.didBlackListNumberUpload() Got Faild*****"
							+ data.toString());

				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!" + e.getMessage());
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
				Logger.info("**** Inside  FileUploadController.didBlackListNumberUpload() Got Exception*****"
						+ data.toString());

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info("**** Inside  FileUploadController.didBlackListNumberUpload() Got Invalid Format*****"
					+ data.toString());

		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		return new Gson().toJson(data).toString();

	}

	// @GetMapping("/incomingDidList")
	public ResponseEntity<List<InComingDidEntity>> getAllIncoming() {
		try {
			List<InComingDidEntity> incomingDid = fileService.getAllIncoming();

			if (incomingDid.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(incomingDid, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/holidayFileUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String holidayFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Integer userId)
			throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.holidayFileUpload() *****");
		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info(
					"**** Inside  FileUploadController.holidayFileUpload() Got User Not Found*****" + data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = uploadHolidayManagementService.saveHolidayUploadFile(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info(
							"**** Inside  FileUploadController.holidayFileUpload() Got Success*****" + data.toString());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info(
							"**** Inside  FileUploadController.holidayFileUpload() Got Faild*****" + data.toString());

				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!" + e.getMessage());
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
				Logger.info(
						"**** Inside  FileUploadController.holidayFileUpload() Got Exception*****" + data.toString());

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info(
					"**** Inside  FileUploadController.holidayFileUpload() Got Invalid Format*****" + data.toString());

		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		return new Gson().toJson(data).toString();

	}

	@PostMapping(value = "/offHourFileUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String offHourFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Integer userId)
			throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.offHourFileUpload() *****");
		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		Optional<Users> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("User Not Found.");
			Logger.info(
					"**** Inside  FileUploadController.offHourFileUpload() Got User Not Found*****" + data.toString());
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = uploadOffHourService.saveOffHour(file, user);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info(
							"**** Inside  FileUploadController.offHourFileUpload() Got Success*****" + data.toString());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info(
							"**** Inside  FileUploadController.offHourFileUpload() Got Faild*****" + data.toString());

				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!" + e.getMessage());
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
				Logger.info(
						"**** Inside  FileUploadController.offHourFileUpload() Got Exception*****" + data.toString());

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info(
					"**** Inside  FileUploadController.offHourFileUpload() Got Invalid Format*****" + data.toString());

		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		return new Gson().toJson(data).toString();

	}

	// @GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "IncomingDidMasking.csv";
		InputStreamResource file = new InputStreamResource(fileService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

}
