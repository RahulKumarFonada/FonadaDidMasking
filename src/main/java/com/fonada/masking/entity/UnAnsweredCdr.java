package com.fonada.masking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unans_cdr")
public class UnAnsweredCdr {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "calldate")
	private Date calldate;
	@Column(name = "clid")
	private String clid;
	@Column(name = "src")
	private String src;
	@Column(name = "dst")
	private String dst;
	@Column(name = "dcontext")
	private String dcontext;
	@Column(name = "lastapp")
	private String lastapp;
	@Column(name = "lastdata")
	private String lastdata;
	@Column(name = "duration")
	private Double duration;
	@Column(name = "billsec")
	private Double billsec;
	@Column(name = "disposition")
	private String disposition;
	@Column(name = "dialstatus")
	private String dialstatus;
	@Column(name = "report_disposition")
	private String report_disposition;
	@Column(name = "HangUpCause")
	private String HangUpCause;
	@Column(name = "HangUpCauseCode")
	private String HangUpCauseCode;
	@Column(name = "HangUpCauseCodeint")
	private Integer HangUpCauseCodeint;
	@Column(name = "channel")
	private String channel;
	@Column(name = "dstchannel")
	private String dstchannel;
	@Column(name = "amaflags")
	private String amaflags;
	@Column(name = "accountcode")
	private String accountcode;
	@Column(name = "uniqueid")
	private String uniqueid;
	@Column(name = "userfield")
	private Double userfield;
	@Column(name = "start")
	private Date start;
	@Column(name = "answer")
	private Date answer;
	@Column(name = "end")
	private Date end;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCalldate() {
		return calldate;
	}

	public void setCalldate(Date calldate) {
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

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getDcontext() {
		return dcontext;
	}

	public void setDcontext(String dcontext) {
		this.dcontext = dcontext;
	}

	public String getLastapp() {
		return lastapp;
	}

	public void setLastapp(String lastapp) {
		this.lastapp = lastapp;
	}

	public String getLastdata() {
		return lastdata;
	}

	public void setLastdata(String lastdata) {
		this.lastdata = lastdata;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getBillsec() {
		return billsec;
	}

	public void setBillsec(Double billsec) {
		this.billsec = billsec;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getDialstatus() {
		return dialstatus;
	}

	public void setDialstatus(String dialstatus) {
		this.dialstatus = dialstatus;
	}

	public String getReport_disposition() {
		return report_disposition;
	}

	public void setReport_disposition(String report_disposition) {
		this.report_disposition = report_disposition;
	}

	public String getHangUpCause() {
		return HangUpCause;
	}

	public void setHangUpCause(String hangUpCause) {
		HangUpCause = hangUpCause;
	}

	public String getHangUpCauseCode() {
		return HangUpCauseCode;
	}

	public void setHangUpCauseCode(String hangUpCauseCode) {
		HangUpCauseCode = hangUpCauseCode;
	}

	public Integer getHangUpCauseCodeint() {
		return HangUpCauseCodeint;
	}

	public void setHangUpCauseCodeint(Integer hangUpCauseCodeint) {
		HangUpCauseCodeint = hangUpCauseCodeint;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDstchannel() {
		return dstchannel;
	}

	public void setDstchannel(String dstchannel) {
		this.dstchannel = dstchannel;
	}

	public String getAmaflags() {
		return amaflags;
	}

	public void setAmaflags(String amaflags) {
		this.amaflags = amaflags;
	}

	public String getAccountcode() {
		return accountcode;
	}

	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public Double getUserfield() {
		return userfield;
	}

	public void setUserfield(Double userfield) {
		this.userfield = userfield;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getAnswer() {
		return answer;
	}

	public void setAnswer(Date answer) {
		this.answer = answer;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "UnAnsweredCdr [id=" + id + ", calldate=" + calldate + ", clid=" + clid + ", src=" + src + ", dst=" + dst
				+ ", dcontext=" + dcontext + ", lastapp=" + lastapp + ", lastdata=" + lastdata + ", duration="
				+ duration + ", billsec=" + billsec + ", disposition=" + disposition + ", dialstatus=" + dialstatus
				+ ", report_disposition=" + report_disposition + ", HangUpCause=" + HangUpCause + ", HangUpCauseCode="
				+ HangUpCauseCode + ", HangUpCauseCodeint=" + HangUpCauseCodeint + ", channel=" + channel
				+ ", dstchannel=" + dstchannel + ", amaflags=" + amaflags + ", accountcode=" + accountcode
				+ ", uniqueid=" + uniqueid + ", userfield=" + userfield + ", start=" + start + ", answer=" + answer
				+ ", end=" + end + "]";
	}

}
