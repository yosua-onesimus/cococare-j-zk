package model.obj.lib;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Entity
@Table(name = "lib_returning_items")
@CCTypeConfig(label = "Returning Item", uniqueKey = "borrowingItem.book.title")
public class LibReturningItem implements CCEntity {
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

	@ManyToOne
	@CCFieldConfig(componentId = "bndReturning", accessible = Accessible.MANDATORY, maxLength = 12, uniqueKey = "code")
	private LibReturning returning;
	@ManyToOne
	@CCFieldConfig(label = "Book", componentId = "bndBorrowingItem", accessible = Accessible.MANDATORY, maxLength = 12, uniqueKey = "book.title", requestFocus = true)
	private LibBorrowingItem borrowingItem;
	@CCFieldConfig(label = "Fine", tooltiptext = "Borrowing Fine", componentId = "txtBorrowingFine", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMBER_FORMAT, maxLength = 24)
	private Double borrowingFine;

	public LibReturning getReturning() {
		return returning;
	}

	public void setReturning(LibReturning returning) {
		this.returning = returning;
	}

	public LibBorrowingItem getBorrowingItem() {
		return borrowingItem;
	}

	public void setBorrowingItem(LibBorrowingItem borrowingItem) {
		this.borrowingItem = borrowingItem;
	}

	public Double getBorrowingFine() {
		return borrowingFine;
	}

	public void setBorrowingFine(Double borrowingFine) {
		this.borrowingFine = borrowingFine;
	}
}