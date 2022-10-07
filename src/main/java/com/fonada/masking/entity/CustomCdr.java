package com.fonada.masking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "custom_cdr")
public class CustomCdr {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	@Column(name = "calldate")
	private Date calldate;
	@Column(name = "clid")
	private String clid;
	@Column(name = "src")
	private String src;
	@Column(name = "dst")
	private String dst;
	@Column(name = "mapped_dst")
	private String mapped_dst;
	@Column(name = "fallback_dst")
	private String fallback_dst;
	@Column(name = "offtime_dst")
	private String offtime_dst;
	@Column(name = "holiday_dst")
	private String holiday_dst;
	@Column(name = "dcontext")
	private String dcontext;
	@Column(name = "lastapp")
	private String lastapp;
	@Column(name = "lastdata")
	private String lastdata;
	@Column(name = "duration")
	private Double duration;
	@Column(name = "")
	private Double billsec;
	@Column(name = "disposition")
	private String disposition;
	@Column(name = "HangUpCause")
	private String HangUpCause;
	@Column(name = "HangUpCauseCode")
	private Integer HangUpCauseCode;
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
	@Column(name = "recording")
	private String recording;
	@Column(name = "dialstatus")
	private String dialstatus;
	@Column(name = "listen")
	private String listen;
	@Column(name = "hangup_by")
	private String hangup_by;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getMapped_dst() {
		return mapped_dst;
	}

	public void setMapped_dst(String mapped_dst) {
		this.mapped_dst = mapped_dst;
	}

	public String getFallback_dst() {
		return fallback_dst;
	}

	public void setFallback_dst(String fallback_dst) {
		this.fallback_dst = fallback_dst;
	}

	public String getOfftime_dst() {
		return offtime_dst;
	}

	public void setOfftime_dst(String offtime_dst) {
		this.offtime_dst = offtime_dst;
	}

	public String getHoliday_dst() {
		return holiday_dst;
	}

	public void setHoliday_dst(String holiday_dst) {
		this.holiday_dst = holiday_dst;
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

	public String getHangUpCause() {
		return HangUpCause;
	}

	public void setHangUpCause(String hangUpCause) {
		HangUpCause = hangUpCause;
	}

	public Integer getHangUpCauseCode() {
		return HangUpCauseCode;
	}

	public void setHangUpCauseCode(Integer hangUpCauseCode) {
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

	public String getRecording() {
		return recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}

	public String getDialstatus() {
		return dialstatus;
	}

	public void setDialstatus(String dialstatus) {
		this.dialstatus = dialstatus;
	}

	public String getListen() {
		return listen;
	}

	public void setListen(String listen) {
		this.listen = listen;
	}

	public String getHangup_by() {
		return hangup_by;
	}

	public void setHangup_by(String hangup_by) {
		this.hangup_by = hangup_by;
	}

}
