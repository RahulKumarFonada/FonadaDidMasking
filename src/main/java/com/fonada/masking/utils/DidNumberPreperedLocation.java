/*
 * @author Rahul Kumar
 * @since 2022/06/20
 * @version 1.0
 */
package com.fonada.masking.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public enum DidNumberPreperedLocation {

	INTERNATIONAL(1), INDIA(2);

	private Integer id;

	DidNumberPreperedLocation(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public static DidNumberPreperedLocation fromId(Integer id) {
		if (Objects.isNull(id))
			return null;
		for (DidNumberPreperedLocation queue : DidNumberPreperedLocation.values()) {
			if (queue.getId().equals(id)) {
				return queue;
			}
		}
		return null;
	}

	public static DidNumberPreperedLocation fromString(String status) {
		if (Boolean.TRUE.equals(StringUtils.isBlank(status)))
			return null;
		for (DidNumberPreperedLocation queue : DidNumberPreperedLocation.values()) {
			if (queue.name().equalsIgnoreCase(status)) {
				return queue;
			}
		}
		return null;
	}

}
