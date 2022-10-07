package com.fonada.masking.bean;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class CustomCdrReportBean {

	private String startDate;
	private String endDate;
	private String disposition;
	private String cLid;
	private Integer page;
	private Integer size;
	private Direction direction;
	private String srcNo;
	private String sortingColumn;

	public String getSortingColumn() {
		return sortingColumn;
	}

	public void setSortingColumn(String sortingColumn) {
		this.sortingColumn = sortingColumn;
	}

	public String getSrcNo() {
		return srcNo;
	}

	public void setSrcNo(String srcNo) {
		this.srcNo = srcNo;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getcLid() {
		return cLid;
	}

	public void setcLid(String cLid) {
		this.cLid = cLid;
	}

	@Override
	public String toString() {
		return "CustomCdrReportBean []";
	}

}
