package model.obj.inv;

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

import cococare.common.CCFieldConfig;
import cococare.common.CCTypeConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.database.CCEntity;

@Entity
@Table(name = "inv_ownerships")
@CCTypeConfig(label = "Ownership")
public class InvOwnership implements CCEntity {

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
	@ManyToOne
	@CCFieldConfig(componentId = "bndEmployee", accessible = Accessible.MANDATORY, uniqueKey = "name")
	private InvEmployee employee;
	@ManyToOne
	@CCFieldConfig(componentId = "bndInventory", accessible = Accessible.MANDATORY, uniqueKey = "name")
	private InvInventory inventory;

	// <editor-fold defaultstate="collapsed" desc=" getter-setter ">
	public InvEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(InvEmployee employee) {
		this.employee = employee;
	}

	public InvInventory getInventory() {
		return inventory;
	}

	public void setInventory(InvInventory inventory) {
		this.inventory = inventory;
	}
	// </editor-fold>
}