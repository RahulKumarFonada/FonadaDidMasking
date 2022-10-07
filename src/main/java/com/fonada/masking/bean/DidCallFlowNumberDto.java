package com.fonada.masking.bean;

import java.io.Serializable;
import java.util.List;

public class DidCallFlowNumberDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String didNo;
	private List<String> officeNo;
	private List<String> nonOfficeNo;
	private List<String> fallbackNo;
	private Integer ringTime;
	private String callPreferenceLocation;
	private String didCli;

	public String callRecording;
	public String holidayCheck;
	public String timeGroupId;
	public String offtimeDestinationType;
	public String offtimeDestinationTypeValue;
	public String holidayDestinationType;
	public String holidayDestinationTypeValue;
	public Integer didStatus;

	public Integer getDidStatus() {
		return didStatus;
	}

	public void setDidStatus(Integer didStatus) {
		this.didStatus = didStatus;
	}

	public String getCallRecording() {
		return callRecording;
	}

	public void setCallRecording(String callRecording) {
		this.callRecording = callRecording;
	}

	public String getHolidayCheck() {
		return holidayCheck;
	}

	public void setHolidayCheck(String holidayCheck) {
		this.holidayCheck = holidayCheck;
	}

	public String getTimeGroupId() {
		return timeGroupId;
	}

	public void setTimeGroupId(String timeGroupId) {
		this.timeGroupId = timeGroupId;
	}

	public String getOfftimeDestinationType() {
		return offtimeDestinationType;
	}

	public void setOfftimeDestinationType(String offtimeDestinationType) {
		this.offtimeDestinationType = offtimeDestinationType;
	}

	public String getOfftimeDestinationTypeValue() {
		return offtimeDestinationTypeValue;
	}

	public void setOfftimeDestinationTypeValue(String offtimeDestinationTypeValue) {
		this.offtimeDestinationTypeValue = offtimeDestinationTypeValue;
	}

	public String getHolidayDestinationType() {
		return holidayDestinationType;
	}

	public void setHolidayDestinationType(String holidayDestinationType) {
		this.holidayDestinationType = holidayDestinationType;
	}

	public String getHolidayDestinationTypeValue() {
		return holidayDestinationTypeValue;
	}

	public void setHolidayDestinationTypeValue(String holidayDestinationTypeValue) {
		this.holidayDestinationTypeValue = holidayDestinationTypeValue;
	}

	public String getDidCli() {
		return didCli;
	}

	public void setDidCli(String didCli) {
		this.didCli = didCli;
	}

	public String getCallPreferenceLocation() {
		return callPreferenceLocation;
	}

	public void setCallPreferenceLocation(String callPreferenceLocation) {
		this.callPreferenceLocation = callPreferenceLocation;
	}

	public String getDidNo() {
		return didNo;
	}

	public void setDidNo(String didNo) {
		this.didNo = didNo;
	}

	public List<String> getOfficeNo() {
		return officeNo;
	}

	public void setOfficeNo(List<String> officeNo) {
		this.officeNo = officeNo;
	}

	public List<String> getNonOfficeNo() {
		return nonOfficeNo;
	}

	public void setNonOfficeNo(List<String> nonOfficeNo) {
		this.nonOfficeNo = nonOfficeNo;
	}

	public List<String> getFallbackNo() {
		return fallbackNo;
	}

	public void setFallbackNo(List<String> fallbackNo) {
		this.fallbackNo = fallbackNo;
	}

	public Integer getRingTime() {
		return ringTime;
	}

	public void setRingTime(Integer ringTime) {
		this.ringTime = ringTime;
	}

}
