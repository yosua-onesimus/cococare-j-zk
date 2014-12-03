package model.obj.lib;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import cococare.common.CCFieldConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.OnDelete;
import cococare.common.CCFieldConfig.Type;
import cococare.common.CCTypeConfig;
import cococare.database.CCEntity;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
@Entity
@Table(name = "lib_borrowings")
@CCTypeConfig(label = "Borrowing", uniqueKey = "code")
public class LibBorrowing implements CCEntity {
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

	@Column(length = 12)
	@CCFieldConfig(componentId = "txtCode", accessible = Accessible.MANDATORY, maxLength = 12, sequence = "[yyMMdd]/000", unique = true)
	private String code;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "date_")
	@CCFieldConfig(componentId = "dtpDate", accessible = Accessible.MANDATORY, maxLength = 12)
	private Date date = new Date();
	@ManyToOne
	@CCFieldConfig(componentId = "bndMember", accessible = Accessible.MANDATORY, maxLength = 32, uniqueKey = "fullName", requestFocus = true)
	private LibMember member_;
	@CCFieldConfig(label = "T. Item", tooltiptext = "Total Item", componentId = "txtTotalItem", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMERIC, maxLength = 2)
	private Integer totalItem;
	@CCFieldConfig(label = "T. Cost", tooltiptext = "Total Cost", componentId = "txtTotalCost", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMBER_FORMAT, maxLength = 24)
	private Double totalCost;
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "borrowing")
	@CCFieldConfig(onDelete = OnDelete.CASCADE)
	private List<LibBorrowingItem> borrowingItems;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LibMember getMember_() {
		return member_;
	}

	public void setMember_(LibMember member_) {
		this.member_ = member_;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
}