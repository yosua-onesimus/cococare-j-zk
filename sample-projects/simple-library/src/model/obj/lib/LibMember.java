package model.obj.lib;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "lib_members")
@CCTypeConfig(label = "Member", uniqueKey = "fullName")
public class LibMember implements CCEntity {
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
	@Column(length = 32)
	@CCFieldConfig(componentId = "txtFullName", accessible = Accessible.MANDATORY, maxLength = 32, requestFocus = true)
	private String fullName;
	@Temporal(value = TemporalType.DATE)
	@CCFieldConfig(componentId = "dtpBirthDate", accessible = Accessible.MANDATORY, type = Type.DATE_PAST, maxLength = 12, visible = false)
	private Date birthDate;
	@CCFieldConfig(componentId = "cmbGender", accessible = Accessible.MANDATORY, optionSource = "model.obj.lib.LibEnum$Gender", optionReflectKey = "gender", visible = false)
	private Integer genderIndex;
	@Column(length = 8)
	@CCFieldConfig(maxLength = 8, visible = false, visible2 = false)
	private String gender;
	@Column()
	@CCFieldConfig(componentId = "txtAddress", visible = false)
	private String address;
	@Column(length = 48)
	@CCFieldConfig(tooltiptext = "Kabupaten", componentId = "cmbRegency", maxLength = 48, optionSource = "IndonesiaCities.txt", optionReflectKey = "regency", visible = false)
	private String regency;
	@Column(length = 48)
	@CCFieldConfig(tooltiptext = "Propinsi", componentId = "cmbProvince", maxLength = 48, optionSource = "IndonesiaProvinces.txt", optionReflectKey = "province", visible = false)
	private String province;
	@Column(length = 16)
	@CCFieldConfig(componentId = "txtPhone", type = Type.PHONE_NUMBER, maxLength = 16)
	private String phone;
	@Column(length = 16)
	@CCFieldConfig(componentId = "txtKtp", maxLength = 16)
	private String ktp;
	@CCFieldConfig(label = "S", tooltiptext = "Suspend", componentId = "chkSuspend", maxLength = 4)
	private Boolean suspend = false;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getGenderIndex() {
		return genderIndex;
	}

	public void setGenderIndex(Integer genderIndex) {
		this.genderIndex = genderIndex;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegency() {
		return regency;
	}

	public void setRegency(String regency) {
		this.regency = regency;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getKtp() {
		return ktp;
	}

	public void setKtp(String ktp) {
		this.ktp = ktp;
	}

	public Boolean getSuspend() {
		return suspend;
	}

	public void setSuspend(Boolean suspend) {
		this.suspend = suspend;
	}
}