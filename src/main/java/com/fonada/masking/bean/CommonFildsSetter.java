package com.fonada.masking.bean;

import java.util.Date;

import com.fonada.masking.entity.CommonFields;
import com.fonada.masking.entity.Users;
import com.fonada.masking.utils.Status;

public class CommonFildsSetter {

	private CommonFields commonField = new CommonFields();

	public CommonFields create_Record(Users user) {
		Date date = new Date();
		if (user != null) {
			commonField.setCreatedBy(Integer.toString(user.getId()));
			commonField.setModifiedBy(Integer.toString(user.getId()));
		} else {
			commonField.setCreatedBy("admin");
			commonField.setModifiedBy("admin");
		}

		commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));
		commonField.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));
		commonField.setCreatedDate(date);
		commonField.setModifiedDate(date);
		return commonField;
	}

	public CommonFields Update_Record(Users user, CommonFields commonFields) {
		Date date = new Date();
		commonField = commonFields;

		if (user != null) {
			commonField.setModifiedBy(Integer.toString(user.getId()));
		} else {
			commonField.setModifiedBy("admin");
		}
		// commonField.setModifiedBy("admin");
		commonField.setModifiedDate(date);
		commonField.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));
		commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));
		return commonField;
	}

	/*
	 * public CommonFields Delete_Record(Users user, CommonFields commonFields) {
	 * Date date = new Date(); commonField = commonFields;
	 * commonField.setModifiedBy("admin"); commonField.setModifiedDate(date);
	 * commonField.setIsDeleted(Constants.ENTITY_IS_Deleted);
	 * commonField.setIsActive(Constants.ENTITY_IS_NOT_ACTIVE); return commonField;
	 * }
	 */

	public CommonFields Deactivate_Record(Users user, CommonFields commonFields) {
		Date date = new Date();
		commonField = commonFields;
		commonField.setModifiedBy("admin");
		commonField.setModifiedDate(date);
		commonField.setIsActive(String.valueOf((Status.INACTIVE.getId())));
		return commonField;
	}

	public CommonFields activate_Record(Users user, CommonFields commonFields) {
		Date date = new Date();
		commonField = commonFields;
		commonField.setModifiedBy("admin");
		commonField.setModifiedDate(date);
		commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));

		return commonField;
	}

	public CommonFields activate_user(Users user, CommonFields commonFields) {
		Date date = new Date();
		commonField = commonFields;
		commonField.setModifiedBy("admin");
		commonField.setModifiedDate(date);
		commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));
		commonField.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));

		return commonField;
	}

	public CommonFields getCommonFields() {
		return commonField;
	}

	// CommonFildsSetter
	public CommonFields deactiveAndUpdateRecord(Users user) {

		CommonFields commonField2 = new CommonFields();
		Date date = new Date();
		if (user != null) {
			commonField2.setCreatedBy(Integer.toString(user.getId()));
			commonField2.setModifiedBy(Integer.toString(user.getId()));
		} else {
			commonField2.setCreatedBy("admin");
			commonField2.setModifiedBy("admin");
		}

		commonField2.setIsActive(String.valueOf((Status.ACTIVE.getId())));
		commonField2.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));
		commonField2.setCreatedDate(date);
		commonField2.setModifiedDate(date);
		return commonField2;
	}

	public CommonFields createUser(Users user) {
		Date date = new Date();
		if (user != null) {
			commonField.setCreatedBy(Integer.toString(user.getId()));
			commonField.setModifiedBy(Integer.toString(user.getId()));
		} else {
			commonField.setCreatedBy("admin");
			commonField.setModifiedBy("admin");
		}

		commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));
		commonField.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));
		commonField.setCreatedDate(date);
		commonField.setModifiedDate(date);
		return commonField;
	}

	public CommonFields Delete_Record(Users user, CommonFields commonFields) {
		Date date = new Date();
		commonField = commonFields;

		if (user != null) {
			commonField.setModifiedBy(Integer.toString(user.getId()));
		} else {
			commonField.setModifiedBy("admin");
		}
		// commonField.setModifiedBy("admin");
		commonField.setModifiedDate(date);
		commonField.setIsDeleted(String.valueOf((Status.INACTIVE.getId())));
		commonField.setIsActive(String.valueOf((Status.ACTIVE.getId())));
		return commonField;
	}

}
