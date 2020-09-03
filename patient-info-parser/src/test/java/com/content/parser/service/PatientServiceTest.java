package com.content.parser.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.content.parser.config.ApplicationPropertiesConfig;
import com.content.parser.model.PatientRequest;
import com.content.parser.model.PatientResponse;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	  @Mock
	  private ApplicationPropertiesConfig applicationPropertiesConfig;

	  @InjectMocks
	  private PatientService patientService;
	  
	  private List<PatientRequest> patientRequestList;

	  @BeforeEach
	  public void init() {
		  PatientRequest patientRequest1 = new PatientRequest(); 
		  patientRequest1.setId(1);
		  patientRequest1.setName("Suriya");
		  patientRequest1.setGender("m");
		  patientRequest1.setState("Michigan");
		  patientRequest1.setDateOfBirth("8/24/1971");
		  
		  PatientRequest patientRequest2 = new PatientRequest(); 
		  patientRequest2.setId(2);
		  patientRequest2.setName("Renu");
		  patientRequest2.setGender("f");
		  patientRequest2.setState("Ohio");
		  patientRequest2.setDateOfBirth("8/24/1975");
		  
		  patientRequestList = new ArrayList<>();
		  patientRequestList.add(patientRequest1);
		  patientRequestList.add(patientRequest2);
	  }
	  
	  @Test
	  public void parsePatientXMLIntoJsonTestCorrectData() {
		
		  Map<String,String> states = new HashMap<>();
		  states.put("michigan","MI");
		  states.put("ohio","OH");
		  
		  when(applicationPropertiesConfig.getDateFormat()).thenReturn("MM/dd/yyyy");
		  when(applicationPropertiesConfig.getStates()).thenReturn(states);
		  
		  List<PatientResponse> patientResponseList = patientService.parsePatientXMLIntoJson(patientRequestList);
		  assertSame("MI", patientResponseList.get(0).getState());
		  assertSame("OH", patientResponseList.get(1).getState());
	  
	  }
	
}
