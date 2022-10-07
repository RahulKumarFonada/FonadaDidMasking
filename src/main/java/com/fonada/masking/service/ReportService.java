package com.fonada.masking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fonada.masking.bean.CustomCdrReportBean;
import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.bean.RecordingResponseBean;
import com.fonada.masking.common.Constants;
import com.fonada.masking.common.StringUtils;
import com.fonada.masking.entity.CustomCdr;
import com.fonada.masking.entity.UnAnsweredCdr;
import com.fonada.masking.repository.CustomCdrRepository;
import com.fonada.masking.repository.UnAnsweredCdrRepository;
import com.fonada.masking.utils.Utils;
import com.google.gson.Gson;

@Service
public class ReportService {

	public static final Logger logger = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private CustomCdrRepository customCdrRepository;

	@Autowired
	private UnAnsweredCdrRepository unAnsCdrRepo;

	/**
	 * 
	 * @param customCdrReportBean
	 * @return
	 */
	public String customCdrReport(CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportService.customCdrReport() Request::" + customCdrReportBean.toString());
		DataContainer data = new DataContainer();
		List<CustomCdr> customCdr = null;
		String startTime = " 00:00:00";
		String endTime = " 23:59:00";
		try {
			/**
			 * If clid is null
			 */

			if (customCdrReportBean.getcLid() == null && customCdrReportBean.getDisposition() != null
					&& customCdrReportBean.getEndDate() != null && customCdrReportBean.getStartDate() != null) {
				customCdr = customCdrRepository
						.findAllAnsweredCustomCdrWithoutClidNumber(customCdrReportBean.getStartDate() + startTime,
								customCdrReportBean.getEndDate() + endTime, customCdrReportBean.getDisposition(),
								PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()))
						.parallelStream().filter(cdr -> cdr.getDisposition() != null).collect(Collectors.toList());

			}
			/**
			 * If disposition is null
			 */

			else if (customCdrReportBean.getDisposition() == null && customCdrReportBean.getcLid() != null
					&& customCdrReportBean.getEndDate() != null && customCdrReportBean.getStartDate() != null) {
				customCdr = customCdrRepository
						.findAllAnsweredCustomCdrWithoutDispositon(customCdrReportBean.getStartDate() + startTime,
								customCdrReportBean.getEndDate() + endTime, customCdrReportBean.getDisposition(),
								PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()))
						.parallelStream().filter(cdr -> cdr.getDisposition() != null).collect(Collectors.toList());

			}
			/**
			 * if clid and disposition is null
			 */
			else if (customCdrReportBean.getcLid() == null && customCdrReportBean.getDisposition() == null
					&& customCdrReportBean.getStartDate() != null && customCdrReportBean.getEndDate() != null) {
				customCdr = customCdrRepository.findAllAnsweredCustomCdrWithStartAndEndDate(
						customCdrReportBean.getStartDate() + startTime, customCdrReportBean.getEndDate() + endTime,
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));
			}
			/**
			 * if clid, disposition and endDate is null
			 */
			else if (customCdrReportBean.getStartDate() != null && customCdrReportBean.getcLid() == null
					&& customCdrReportBean.getDisposition() == null && customCdrReportBean.getEndDate() == null) {
				customCdr = customCdrRepository.findAllAnsweredCustomCdrWithStartDate(
						customCdrReportBean.getStartDate(),
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));
			}
			/**
			 * if clid, disposition ,endDate and startDate is null
			 */
			else if (customCdrReportBean.getcLid() == null && customCdrReportBean.getDisposition() == null
					&& customCdrReportBean.getEndDate() == null && customCdrReportBean.getStartDate() == null) {

				customCdr = customCdrRepository.findCustomCdrWithCurrentStartEndDate(
						Utils.getDateInString() + startTime, Utils.getDateInString() + endTime,
						PageRequest.of(Constants.ZERO_INT, Integer.MAX_VALUE));
			}
			/**
			 * if clid, disposition, endDate and StartDate is NOT NULL
			 */
			else {
				customCdr = customCdrRepository
						.findAllAnsweredCustomCdr(customCdrReportBean.getStartDate(), customCdrReportBean.getEndDate(),
								customCdrReportBean.getDisposition(), customCdrReportBean.getcLid(),
								PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()))
						.parallelStream().filter(cdr -> cdr.getDisposition() != null).collect(Collectors.toList());

			}
			if (customCdr.size() > 0) {
				data.setMsg(Constants.SUCCESS_MSG + "::" + " Total Record:: " + customCdr.size());
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(customCdr);
			} else {
				data.setMsg("Record Not Found.");
				data.setStatus(Constants.NOT_FOUND);
			}
			logger.info("***** Success ReportService.customCdrReport() Response::" + data.toString());
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.NOT_FOUND);
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	/**
	 * 
	 * @param customCdrReportBean
	 * @return
	 */
	public String unAnsCdrReport(CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportService.customCdrReport() Request::" + customCdrReportBean.toString());
		DataContainer data = new DataContainer();
		List<UnAnsweredCdr> unAnsCdr = null;
		try {
			/**
			 * If clid is null and Start ,End Date is Not Null
			 */
			if (customCdrReportBean.getcLid() == null && customCdrReportBean.getStartDate() != null
					&& customCdrReportBean.getEndDate() != null) {
				unAnsCdr = unAnsCdrRepo.findAllUnAnsweredCustomCdrWithOutCLID(customCdrReportBean.getStartDate(),
						customCdrReportBean.getEndDate(),
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));

			}
			/**
			 * If Start Date is not null and clid and end is Null
			 */
			else if (customCdrReportBean.getStartDate() != null && customCdrReportBean.getEndDate() == null
					&& customCdrReportBean.getcLid() == null) {
				unAnsCdr = unAnsCdrRepo.findAllUnAnsweredCustomCdrByStartDate(customCdrReportBean.getStartDate(),
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));

			}
			/**
			 * If End is Not Null and Clid and Start is NUll
			 */
			else if (customCdrReportBean.getStartDate() == null && customCdrReportBean.getEndDate() != null
					&& customCdrReportBean.getcLid() == null) {
				unAnsCdr = unAnsCdrRepo.findAllUnAnsweredCustomCdrByEndDate(customCdrReportBean.getEndDate(),
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));

			}
			/**
			 * if Clid, Start And End Date is Null
			 */
			else if (customCdrReportBean.getStartDate() == null && customCdrReportBean.getEndDate() == null
					&& customCdrReportBean.getcLid() == null) {
				unAnsCdr = unAnsCdrRepo.findAllUnAnsweredCustomCdrByStartAndEndCurrentDate(Utils.getDateInString(),
						Utils.getDateInString(),
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));

			}
			/**
			 * If Clid ,Start And End Date Is Not Null
			 */
			else {
				unAnsCdr = unAnsCdrRepo.findAllUnAnsweredCustomCdrReport(customCdrReportBean.getStartDate(),
						customCdrReportBean.getEndDate(), customCdrReportBean.getcLid(),
						PageRequest.of(customCdrReportBean.getPage() - 1, customCdrReportBean.getSize()));

			}
			if (unAnsCdr.size() > 0) {
				data.setMsg(Constants.SUCCESS_MSG + "::" + " Total Record:: " + unAnsCdr.size());
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(unAnsCdr);
			} else {
				data.setMsg("Record Not Found.");
				data.setStatus(Constants.NOT_FOUND);
			}
			logger.info("***** Success ReportService.customCdrReport() Response::" + data.toString());
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.NOT_FOUND);
			e.printStackTrace();
		}
		return new Gson().toJson(data).toString();
	}

	/**
	 * 
	 * @param customCdrReportBean
	 * @return
	 */
	@Async("processExecutor")
	public CompletableFuture<DataContainer> getRecordingManagementFormCustomerCDR(
			CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportService.getRecordingManagementFormCustomerCDR() Request::"
				+ customCdrReportBean.toString());

		DataContainer data = new DataContainer();
		RecordingResponseBean recordingResponseBean = null;
		List<RecordingResponseBean> recordingResponseBeanList = new ArrayList<RecordingResponseBean>();
		List<RecordingResponseBean> recordingResponseList = new ArrayList<RecordingResponseBean>();

		List<Object[]> cliCountObjects = null;
		List<Object[]> srcCountObjects = null;

		String sortingColumn = returnSortingColumn(customCdrReportBean.getSortingColumn());
		Sort sorting = getDefaultSorting();

		if (StringUtils.isNotBlank(sortingColumn) && Objects.nonNull(customCdrReportBean.getDirection())) {
			sorting = Sort.by(customCdrReportBean.getDirection(), sortingColumn);
		}
		try {
			String startDate = " 00:00:00";
			String endDate = " 23:59:00";
			if (customCdrReportBean.getcLid() != null && customCdrReportBean.getStartDate() != null
					&& customCdrReportBean.getEndDate() != null) {
				cliCountObjects = customCdrRepository.findAllByStartAndEndDateAndClidForRecordingManagement(
						customCdrReportBean.getStartDate() + startDate, customCdrReportBean.getEndDate() + endDate,
						customCdrReportBean.getcLid(),
						PageRequest.of(customCdrReportBean.getPage(), customCdrReportBean.getSize(), sorting));
			} else if (customCdrReportBean.getcLid() == null && customCdrReportBean.getStartDate() != null
					&& customCdrReportBean.getEndDate() != null) {
				cliCountObjects = customCdrRepository.getCliCountByStartAndEndDate(
						customCdrReportBean.getStartDate() + startDate, customCdrReportBean.getEndDate() + endDate,
						PageRequest.of(customCdrReportBean.getPage(), customCdrReportBean.getSize(), sorting));

			} else if (customCdrReportBean.getcLid() == null && customCdrReportBean.getStartDate() == null
					&& customCdrReportBean.getEndDate() == null) {
				cliCountObjects = customCdrRepository.getCliCountByStartAndEndDate(
						Utils.getLastWeekDateByCurrentDate() + startDate, Utils.getDateInString() + endDate,
						PageRequest.of(customCdrReportBean.getPage(), customCdrReportBean.getSize(), sorting));

			}
			if (Objects.nonNull(cliCountObjects)) {
				for (Object[] cliCount : cliCountObjects) {
					recordingResponseBean = new RecordingResponseBean();
					recordingResponseBean.setCalldate(String.valueOf(cliCount[0]));
					recordingResponseBean.setClid(String.valueOf(cliCount[1]));
					recordingResponseBean.setSrc(String.valueOf(cliCount[2]));
					recordingResponseBean.setClidCount(String.valueOf(cliCount[3]));
					recordingResponseBean.setWeekCount(String.valueOf(cliCount[4]));
					recordingResponseBean.setTotalBillCount(String.valueOf(cliCount[5]));
					recordingResponseBeanList.add(recordingResponseBean);

				}

				for (RecordingResponseBean recordingResponse : recordingResponseBeanList) {
					if (customCdrReportBean.getcLid() == null && customCdrReportBean.getStartDate() == null
							&& customCdrReportBean.getEndDate() == null) {
						srcCountObjects = customCdrRepository.getCountSrcNumberByCliNumber(
								Utils.getLastWeekDateByCurrentDate() + startDate, Utils.getDateInString() + endDate,
								recordingResponse.getSrc(),
								PageRequest.of(customCdrReportBean.getPage(), customCdrReportBean.getSize(), sorting));

					} else {
						srcCountObjects = customCdrRepository.getCountSrcNumberByCliNumber(
								customCdrReportBean.getStartDate() + startDate,
								customCdrReportBean.getEndDate() + endDate, recordingResponse.getSrc(),
								PageRequest.of(customCdrReportBean.getPage(), customCdrReportBean.getSize(), sorting));
					}
					for (Object[] srcCount : srcCountObjects) {
						recordingResponse.setSrcNumber(String.valueOf(srcCount[0]));
						recordingResponse.setSrcCount(String.valueOf(srcCount[1]));
						recordingResponse.setSrcWeekCount(String.valueOf(srcCount[2]));
						recordingResponse.setSrcBillCount(String.valueOf(srcCount[3]));

					}
					recordingResponseList.add(recordingResponseBean);
				}

				data.setMsg(Constants.SUCCESS_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(recordingResponseList);
			} else {
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				data.setStatus(Constants.RECORD_NOT_EXISTS);
			}
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.REQUEST_FAILED);
			e.printStackTrace();
		}
		logger.info("***** Success ReportService.getRecordingManagementFormCustomerCDR() Response::"
				+ customCdrReportBean.toString());

		return CompletableFuture.completedFuture(data);
	}

	public Sort getDefaultSorting() {
		return Sort.by(Direction.DESC, "calldate");
	}

	public String returnSortingColumn(String sortingColumn) {
		String byDefaultsortingColumn = "calldate";
		if (StringUtils.isNotBlank(sortingColumn)) {
			byDefaultsortingColumn = sortingColumn;
		}
		return byDefaultsortingColumn;
	}

	/**
	 * purpose:: get record based on calldate from to To date by src number
	 * 
	 * @param customCdrReportBean
	 * @return
	 */
	public String getRecordByStartAndEndDateWithSrcNumber(CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportService.getRecordByStartAndEndDateWithSrcNumber() Request::"
				+ customCdrReportBean.toString());

		DataContainer data = new DataContainer();
		String startDate = customCdrReportBean.getStartDate() + " 00:00:00";
		String endDate = customCdrReportBean.getEndDate() + " 23:59:00";

		List<CustomCdr> customCdrList = null;
		try {
			customCdrList = customCdrRepository.findAllCustomCdrBySourceNumber(startDate, endDate,
					customCdrReportBean.getSrcNo());
			if (customCdrList.size() > 0) {
				data.setMsg(Constants.SUCCESS_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(customCdrList);
			} else {
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				data.setStatus(Constants.RECORD_NOT_EXISTS);
			}
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.REQUEST_SUCCESS);
			e.printStackTrace();
		}
		logger.info("***** Success ReportService.getRecordByStartAndEndDateWithSrcNumber() Response::"
				+ customCdrReportBean.toString());
		return new Gson().toJson(data).toString();
	}

	public String getRecordByStartAndEndDateWithCliNumber(CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportService.getRecordByStartAndEndDateWithCliNumber() Request::"
				+ customCdrReportBean.toString());
		DataContainer data = new DataContainer();
		String startDate = customCdrReportBean.getStartDate() + " 00:00:00";
		String endDate = customCdrReportBean.getEndDate() + " 23:59:00";
		List<CustomCdr> customCdrList = null;
		try {
			customCdrList = customCdrRepository.findAllCustomCdrByCliNumber(startDate, endDate,
					customCdrReportBean.getcLid());
			if (customCdrList.size() > 0) {
				data.setMsg(Constants.SUCCESS_MSG);
				data.setStatus(Constants.REQUEST_SUCCESS);
				data.setData(customCdrList);
			} else {
				data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
				data.setStatus(Constants.RECORD_NOT_EXISTS);
			}
		} catch (Exception e) {
			data.setMsg("Got Exception::" + e.getMessage());
			data.setStatus(Constants.REQUEST_SUCCESS);
			e.printStackTrace();
		}
		logger.info("***** Success ReportService.getRecordByStartAndEndDateWithCliNumber() Response::"
				+ customCdrReportBean.toString());
		return new Gson().toJson(data).toString();
	}
}
