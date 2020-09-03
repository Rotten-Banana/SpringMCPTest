package com.content.parser.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.parser.config.ApplicationPropertiesConfig;
import com.content.parser.model.PatientRequest;
import com.content.parser.model.PatientResponse;

/**
 * This services handles the actual parsing logic. Have also included a junit
 * sample for the logic.
 * 
 * <p>
 * The application property {@link ApplicationPropertiesConfig} is injected to read the state and the date format
 * information without affecting the application code.
 * 
 * </p>
 * 
 * @author suriya
 *
 */
@Service
public class PatientService {

	private ApplicationPropertiesConfig applicationPropertiesConfig;

	@Autowired
	public PatientService(ApplicationPropertiesConfig applicationPropertiesConfig) {
		this.applicationPropertiesConfig = applicationPropertiesConfig;
	}

	/**
	 * 
	 * This methods reads the list of patients and converts them to a list of json
	 * object.
	 * 
	 * @param patientRequestList
	 * @return
	 */
	public List<PatientResponse> parsePatientXMLIntoJson(List<PatientRequest> patientRequestList) {
		List<PatientResponse> patientResponseList = new ArrayList<>();
		patientRequestList.stream().forEach(patientRequest -> {
			PatientResponse patientResponse = new PatientResponse();
			patientResponse.setPatientid(patientRequest.getId());
			patientResponse.setName(patientRequest.getName());
			patientResponse.setSex(getSex(patientRequest.getGender()));
			patientResponse.setState(getStateAbvr(patientRequest.getState()));
			patientResponse.setAge(calculateAge(patientRequest.getDateOfBirth()));
			patientResponseList.add(patientResponse);
		});
		return patientResponseList;
	}

	/**
	 * This calculates the age based on the given date string.
	 * 
	 * @param dateOfBirth
	 * @return
	 */
	private int calculateAge(String dateOfBirth) {
		int age = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(applicationPropertiesConfig.getDateFormat());
			Date d = sdf.parse(dateOfBirth);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			LocalDate l1 = LocalDate.of(year, month, date);
			LocalDate now1 = LocalDate.now();
			Period diff1 = Period.between(l1, now1);
			age = diff1.getYears();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return age;
	}

	/**
	 * This returns the state abvr for the given state string. More state map can be
	 * added to the yml. The input state infomation is converted to lowercase to
	 * avoid key mismatch
	 * 
	 * @param state
	 * @return
	 */
	private String getStateAbvr(String state) {
		Map<String, String> statesAbvrMap = applicationPropertiesConfig.getStates();
		return statesAbvrMap.get(state.toLowerCase());
	}

	/**
	 * This identifies the sex information based on the gender
	 * 
	 * @param gender
	 * @return
	 */
	private String getSex(String gender) {
		String sex = null;
		if ("m".equalsIgnoreCase(gender)) {
			sex = "male";
		} else if ("f".equalsIgnoreCase(gender)) {
			sex = "female";
		}
		return sex;
	}

}
