package br.com.rec.utils;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Ricardo Cabral
 * 
 * Class to validate some input data
 *
 */
@Component
public class DataValidator {

	public String[] readGraphValues(String value) throws IllegalArgumentException {

		if (value == null || value.equals("")) {
			throw new IllegalArgumentException(
					"Input must contain at least one valid value ");
		}

		String[] splitedValues = value.split("\\s*,\\s*"); // split and remove
															// spaces in both
															// sides

		validateSplitedValues(splitedValues);

		return splitedValues;

	}

	private void validateSplitedValues(String[] values)
			throws IllegalArgumentException {

		for (String value : values) {

			if (value.length() != 3
					|| (!value.substring(0, 2).matches("[a-zA-Z]+")) || (!value
							.substring(2, 3).matches("[\\d]"))) {
				throw new IllegalArgumentException(
						"The value typed is invalid, please follow the pattern given!");
			}
		}
	}
	
	public String[] readTownVertexes(String value) throws IllegalArgumentException {

		if (value == null || value.equals("")) {
			throw new IllegalArgumentException(
					"Input must contain valid value ");
		}

		String[] splitedValues = value.split("\\s*,\\s*"); // split and remove
															// spaces in both
															// sides
		
		return splitedValues;

	}
	
	

}
