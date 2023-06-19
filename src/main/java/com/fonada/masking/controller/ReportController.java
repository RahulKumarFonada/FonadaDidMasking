package com.fonada.masking.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fonada.masking.bean.CustomCdrReportBean;
import com.fonada.masking.bean.DataContainer;
import com.fonada.masking.service.ReportService;

/**
 * 
 * @author Rahul
 *
 */
@RestController
@RequestMapping(value = "/reportController")
public class ReportController {

	public static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	/**
	 * 
	 * @param customCdrReportBean
	 * @return
	 */
	@CrossOrigin("*")
	@RequestMapping(value = "/customCdrReport", method = RequestMethod.POST)
	public String customCdrReport(@RequestBody CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportController.customCdrReport() *****");
		return reportService.customCdrReport(customCdrReportBean);
	}

	/**
	 * 
	 * @param customCdrReportBean
	 * @return
	 */
	@CrossOrigin("*")
	@RequestMapping(value = "/unAnsCdrReport", method = RequestMethod.POST)
	public String unAnsCdrReport(@RequestBody CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportController.unAnsCdrReport() *****");
		return reportService.unAnsCdrReport(customCdrReportBean);
	}

	@CrossOrigin("*")
	@RequestMapping(value = "/getCountRecordFromCustomCdr", method = RequestMethod.POST)
	public CompletableFuture<DataContainer> getCountRecordFromCustomCdr(
			@RequestBody CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportController.getCountRecordFromCustomCdr() *****");
		return reportService.getRecordingManagementFormCustomerCDR(customCdrReportBean);
	}

	@CrossOrigin("*")
	@RequestMapping(value = "/getRecordByStartAndEndDateWithSrcNumber", method = RequestMethod.POST)
	public String getRecordByStartAndEndDateWithSrcNumber(@RequestBody CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportController.getRecordByStartAndEndDateWithSrcNumber() *****");
		return reportService.getRecordByStartAndEndDateWithSrcNumber(customCdrReportBean);
	}

	@CrossOrigin("*")
	@RequestMapping(value = "/getRecordByStartAndEndDateWithCliNumber", method = RequestMethod.POST)
	public String getRecordingManagementFormCustomerCDR(@RequestBody CustomCdrReportBean customCdrReportBean) {
		logger.info("***** Inside ReportController.getRecordingManagementFormCustomerCDR() *****");
		return reportService.getRecordByStartAndEndDateWithCliNumber(customCdrReportBean);
	}

}
