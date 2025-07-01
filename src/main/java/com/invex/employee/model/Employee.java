package com.invex.employee.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Employee implements Serializable {

	private static final long serialVersionUID = -495619599305185379L;

	/** Employee ID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private Integer employeeId;

	/** Employee's first name */
	@Column(name = "FIRST_NAME")
	private String firstName;

	/** Employee's middle name */
	@Column(name = "MIDDLE_NAME")
	private String middleName;

	/** Employee's last name (paternal) */
	@Column(name = "LAST_NAME")
	private String lastName;

	/** Employee's second last name (maternal) */
	@Column(name = "SECOND_LAST_NAME")
	private String secondLastName;

	/** Employee's age */
	@Column(name = "AGE")
	private Integer age;

	/** Employee's gender */
	@Column(name = "GENDER")
	private String gender;

	/** Employee's date of birth (dd-mm-yyyy) */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	/** Employee's job position */
	@Column(name = "POSITION")
	private String position;

	/** System registration date (automatically assigned when persisted) */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGISTRATION_DATE", updatable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Date registrationDate;

	/** Active/inactive status of the employee */
	@Column(name = "ACTIVE")
	private Boolean active;
}
