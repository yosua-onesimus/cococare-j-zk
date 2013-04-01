package model.obj.inv;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cococare.common.CCFieldConfig;
import cococare.common.CCTypeConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.database.CCEntity;

@Entity
@Table(name = "inv_employees")
@CCTypeConfig(label = "Employee", uniqueKey = "name")
public class InvEmployee implements CCEntity {

	// <editor-fold defaultstate="collapsed" desc=" entity base ">
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 32)
	private String logCreatedBy;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date logCreatedOn;
	@Column(length = 32)
	private String logChangedBy;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date logChangedOn;
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

	// </editor-fold>
	@Column(length = 16)
	@CCFieldConfig(componentId = "txtCode", accessible = Accessible.MANDATORY, maxLength = 16, sequence = "EMP/[yyMMdd]/000", unique = true)
	private String code;
	@CCFieldConfig(componentId = "txtName", accessible = Accessible.MANDATORY)
	private String name;
	// <editor-fold defaultstate="collapsed" desc=" cascade ">
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "employee")
	private List<InvOwnership> ownerships;

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc=" getter-setter ">
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// </editor-fold>
}