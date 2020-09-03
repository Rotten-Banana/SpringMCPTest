package com.content.parser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.content.parser.model.PatientContainer;
import com.content.parser.model.PatientResponse;
import com.content.parser.service.PatientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This is the main controller that takses care of patient information apis.
 * Currently is only has the parsing api as per the problem statement.
 * 
 * <p>
 * The {@link PatientService}} has the actual parsing logic.
 * </p>
 * 
 * @author suriya
 *
 */
@Api(value = "This controller handles the patient parsing api")
@RestController
public class PatientController {

	private PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@PostMapping(value = "/parse", consumes = { MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This api parses the xml and returns the patients Json data", response = List.class)
	public List<PatientResponse> parsePatientInfo(
			@ApiParam(value = "The patient container tag containing the list of patents information") @RequestBody PatientContainer patients) {
		return patientService.parsePatientXMLIntoJson(patients.getPatientList());
	}

}
