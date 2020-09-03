package com.content.parser.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * This acts as a container and helps mapping the xml data into the list of
 * patients
 * 
 * @author suriya
 *
 */
@XmlRootElement(name = "patients")
@JacksonXmlRootElement(localName = "patients")
public class PatientContainer {

	private List<PatientRequest> patientList;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "patient")
	public List<PatientRequest> getPatientList() {
		return patientList;
	}

	public void setPatientList(List<PatientRequest> patientList) {
		this.patientList = patientList;
	}

}
