package model.obj.lib;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import cococare.common.CCFieldConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.Type;
import cococare.common.CCTypeConfig;
import cococare.database.CCEntity;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
@CCTypeConfig(label = "Library Module", tooltiptext = "Borrowing Cost, Borrowing Limit, Borrowing Fine, etc")
public class LibConfig implements CCEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 32)
	private String logCreatedBy;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date logCreatedOn;
	@Column(length = 32)
	private String logChangedBy;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date logChangedOn;
	@Version
	private Integer logSaveTimes = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogCreatedBy() {
		return logCreatedBy;
	}

	public void setLogCreatedBy(String logCreatedBy) {
		this.logCreatedBy = logCreatedBy;
	}

	public Date getLogCreatedOn() {
		return logCreatedOn;
	}

	public void setLogCreatedOn(Date logCreatedOn) {
		this.logCreatedOn = logCreatedOn;
	}

	public String getLogChangedBy() {
		return logChangedBy;
	}

	public void setLogChangedBy(String logChangedBy) {
		this.logChangedBy = logChangedBy;
	}

	public Date getLogChangedOn() {
		return logChangedOn;
	}

	public void setLogChangedOn(Date logChangedOn) {
		this.logChangedOn = logChangedOn;
	}

	public Integer getLogSaveTimes() {
		return logSaveTimes;
	}

	public void setLogSaveTimes(Integer logSaveTimes) {
		this.logSaveTimes = logSaveTimes;
	}

	@CCFieldConfig(group = "Default Value of Borrowing", label = "Cost (Rp)", tooltiptext = "Borrowing Cost", componentId = "txtBorrowingCost", accessible = Accessible.MANDATORY, type = Type.NUMBER_FORMAT, maxLength = 24, visible = false)
	private Double borrowingCost;
	@CCFieldConfig(group = "Default Value of Borrowing", label = "Limit (days)", tooltiptext = "Borrowing Limit", componentId = "txtBorrowingLimit", accessible = Accessible.MANDATORY, type = Type.NUMERIC, maxLength = 2, visible = false)
	private Integer borrowingLimit;
	@CCFieldConfig(group = "Default Value of Borrowing", label = "Fine (Rp)", tooltiptext = "Borrowing Fine", componentId = "txtBorrowingFine", accessible = Accessible.MANDATORY, type = Type.NUMBER_FORMAT, maxLength = 24, visible = false)
	private Double borrowingFine;

	public Double getBorrowingCost() {
		return borrowingCost;
	}

	public void setBorrowingCost(Double borrowingCost) {
		this.borrowingCost = borrowingCost;
	}

	public Integer getBorrowingLimit() {
		return borrowingLimit;
	}

	public void setBorrowingLimit(Integer borrowingLimit) {
		this.borrowingLimit = borrowingLimit;
	}

	public Double getBorrowingFine() {
		return borrowingFine;
	}

	public void setBorrowingFine(Double borrowingFine) {
		this.borrowingFine = borrowingFine;
	}
}