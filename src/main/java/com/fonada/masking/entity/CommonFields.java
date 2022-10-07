package com.fonada.masking.entity; import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fonada.masking.common.Constants;

@Embeddable
public class CommonFields {

    @Temporal(TemporalType.TIMESTAMP)

    //@JsonFormat(pattern = Constants.defaultDateTimeFormat, timezone = Constants.defaultTimezone)
    private Date modifiedDate;

    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)

    //@JsonFormat(pattern = Constants.defaultDateTimeFormat, timezone = Constants.defaultTimezone) 
    private Date createdDate;
    @Column(name="is_deleted")
    private String isDeleted;

    @Column(name="is_active")
    public String isActive;

    private String modifiedBy;

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        if(createdDate==null) {
            this.createdDate = new Date();
        }else {
            this.createdDate = createdDate;
        }
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted=isDeleted;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        if (modifiedBy==null){
            this.modifiedBy=modifiedBy;
        }else {
            this.modifiedBy = modifiedBy;
        }
    }
}
