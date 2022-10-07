package com.fonada.masking.common;

public enum DidNumberMappingType {

	OFFICETIME(1), FALLBACKNO(2), NONOFFICETIME(3), HOLIDAYNO(4);

	private Integer id;

	DidNumberMappingType(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
