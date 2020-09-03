package com.content.parser.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * This is the request patient object
 * 
 * @author suriya
 *
 */
@XmlRootElement(name = "patient")
public class PatientRequest {

	@ApiModelProperty(example = "1")
	private long id;

	@ApiModelProperty(example = "m")
	private String gender;

	@ApiModelProperty(example = "Suriya")
	private String name;

	@ApiModelProperty(example = "Michigan")
	private String state;

	@ApiModelProperty(example = "01/01/1990")
	private String dateOfBirth;

	@XmlElement
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@XmlElement
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
