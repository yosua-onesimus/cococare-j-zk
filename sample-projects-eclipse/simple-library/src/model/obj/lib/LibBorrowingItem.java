package model.obj.lib;

import static cococare.common.CCFormat.getBoolean;

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
import cococare.common.CCTypeConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.Type;
import cococare.database.CCEntity;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
@Entity
@Table(name = "lib_borrowing_items")
@CCTypeConfig(label = "Borrowing Item", uniqueKey = "book.title")
public class LibBorrowingItem implements CCEntity {
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
	@CCFieldConfig(componentId = "bndBorrowing", accessible = Accessible.MANDATORY, maxLength = 12, uniqueKey = "code")
	private LibBorrowing borrowing;
	@ManyToOne
	@CCFieldConfig(componentId = "bndBook", accessible = Accessible.MANDATORY, maxLength = 32, uniqueKey = "title", requestFocus = true)
	private LibBook book;
	@CCFieldConfig(label = "Cost", tooltiptext = "Borrowing Cost", componentId = "txtBorrowingCost", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMBER_FORMAT, maxLength = 24)
	private Double borrowingCost;
	@Temporal(value = TemporalType.DATE)
	@CCFieldConfig(componentId = "dtpDateReturn", accessible = Accessible.MANDATORY_READONLY, maxLength = 12)
	private Date dateReturn;
	@CCFieldConfig(label = "Fine", tooltiptext = "Borrowing Fine", componentId = "txtBorrowingFine", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMBER_FORMAT, maxLength = 24, visible = false)
	private Double borrowingFine;
	@CCFieldConfig(label = "R", tooltiptext = "Returned", componentId = "chkReturned", maxLength = 4)
	private Boolean returned = false;

	public LibBorrowing getBorrowing() {
		return borrowing;
	}

	public void setBorrowing(LibBorrowing borrowing) {
		this.borrowing = borrowing;
	}

	public LibBook getBook() {
		return book;
	}

	public void setBook(LibBook book) {
		this.book = book;
	}

	public Double getBorrowingCost() {
		return borrowingCost;
	}

	public void setBorrowingCost(Double borrowingCost) {
		this.borrowingCost = borrowingCost;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public Double getBorrowingFine() {
		return borrowingFine;
	}

	public void setBorrowingFine(Double borrowingFine) {
		this.borrowingFine = borrowingFine;
	}

	public Boolean getReturned() {
		return returned;
	}

	public boolean isReturned() {
		return getBoolean(returned);
	}

	public void setReturned(Boolean returned) {
		this.returned = returned;
	}
}