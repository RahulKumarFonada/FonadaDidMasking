package com.fonada.masking.bean;

public class RecordingResponseBean {
	private String calldate;
	private String clid;
	private String src;
	private String clidCount;
	private String weekCount;
	private String totalBillCount;
	private String srcNumber;
	private String srcBillCount;
	private String srcWeekCount;
	private String srcCount;

	public String getSrcNumber() {
		return srcNumber;
	}

	public void setSrcNumber(String srcNumber) {
		this.srcNumber = srcNumber;
	}

	public String getSrcBillCount() {
		return srcBillCount;
	}

	public void setSrcBillCount(String srcBillCount) {
		this.srcBillCount = srcBillCount;
	}

	public String getSrcWeekCount() {
		return srcWeekCount;
	}

	public void setSrcWeekCount(String srcWeekCount) {
		this.srcWeekCount = srcWeekCount;
	}

	public String getSrcCount() {
		return srcCount;
	}

	public void setSrcCount(String srcCount) {
		this.srcCount = srcCount;
	}

	public String getCalldate() {
		return calldate;
	}

	public void setCalldate(String calldate) {
		this.calldate = calldate;
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getClidCount() {
		return clidCount;
	}

	public void setClidCount(String clidCount) {
		this.clidCount = clidCount;
	}

	public String getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(String weekCount) {
		this.weekCount = weekCount;
	}

	public String getTotalBillCount() {
		return totalBillCount;
	}

	public void setTotalBillCount(String totalBillCount) {
		this.totalBillCount = totalBillCount;
	}

	@Override
	public String toString() {
		return "RecordingResponseBean [calldate=" + calldate + ", clid=" + clid + ", src=" + src + ", clidCount="
				+ clidCount + ", weekCount=" + weekCount + ", totalBillCount=" + totalBillCount + ", srcBillCount="
				+ srcBillCount + ", srcWeekCount=" + srcWeekCount + ", srcCount=" + srcCount + "]";
	}

}
