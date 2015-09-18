package model.obj.lib;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cococare.common.CCFieldConfig;
import cococare.common.CCTypeConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.OnDelete;
import cococare.common.CCFieldConfig.Type;
import cococare.database.CCEntity;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
@Entity
@Table(name = "lib_returnings")
@CCTypeConfig(label = "Returning", uniqueKey = "code")
public class LibReturning extends CCEntity {
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
	@CCFieldConfig(label = "T. Fine", tooltiptext = "Total Fine", componentId = "txtTotalFine", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMBER_FORMAT, maxLength = 24)
	private Double totalFine;
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "returning")
	@CCFieldConfig(onDelete = OnDelete.CASCADE)
	private List<LibReturningItem> returningItems;

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

	public Double getTotalFine() {
		return totalFine;
	}

	public void setTotalFine(Double totalFine) {
		this.totalFine = totalFine;
	}
}