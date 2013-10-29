package model.obj.inv;

//<editor-fold defaultstate="collapsed" desc=" import ">
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
import javax.persistence.Version;

import cococare.common.CCFieldConfig;
import cococare.common.CCTypeConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.OnDelete;
import cococare.common.CCFieldConfig.Type;
import cococare.database.CCEntity;

@Entity
@Table(name = "inv_inventories")
@CCTypeConfig(label = "Inventory", uniqueKey = "name")
public class InvInventory implements CCEntity {
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

	@Column(length = 16)
	@CCFieldConfig(componentId = "txtCode", accessible = Accessible.MANDATORY, maxLength = 16, sequence = "INV/[yyMMdd]/000", requestFocus = true, unique = true)
	private String code;
	@Column(length = 255)
	@CCFieldConfig(componentId = "txtName", accessible = Accessible.MANDATORY)
	private String name;
	@Column(length = Integer.MAX_VALUE)
	@CCFieldConfig(componentId = "txtDescription", maxLength = Integer.MAX_VALUE, visible = false)
	private String description;
	@CCFieldConfig(label = "QT", tooltiptext = "Quantity Total", componentId = "txtQuantityTotal", accessible = Accessible.MANDATORY, type = Type.NUMERIC, maxLength = 2)
	private Integer quantityTotal = 0;
	@CCFieldConfig(label = "QU", tooltiptext = "Quantity Used", componentId = "txtQuantityUsed", accessible = Accessible.READONLY, type = Type.NUMERIC, maxLength = 2)
	private Integer quantityUsed = 0;
	@CCFieldConfig(label = "QA", tooltiptext = "Quantity Available", componentId = "txtQuantityAvailable", accessible = Accessible.READONLY, type = Type.NUMERIC, maxLength = 2)
	private Integer quantityAvailable = 0;
	// <editor-fold defaultstate="collapsed" desc=" cascade ">
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "inventory")
	@CCFieldConfig(onDelete = OnDelete.RESTRICT)
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	// </editor-fold>
}