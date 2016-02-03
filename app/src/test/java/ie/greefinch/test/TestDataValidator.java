package ie.greefinch.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.rec.utils.DataValidator;

public class TestDataValidator {

	@Test
	public void testValidInputValues() {
		String validValues = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";

		DataValidator reader = new DataValidator();

		assertTrue(reader.readGraphValues(validValues).length > 0);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidInputValues() {
		String invalidValues = "AB5, BC, CD8";
		DataValidator reader = new DataValidator();

		reader.readGraphValues(invalidValues);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputNullValue() {

		DataValidator reader = new DataValidator();

		reader.readGraphValues(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputLessThanTwo() {
		String invalidValues = "AB5, BC, CD8";
		DataValidator reader = new DataValidator();

		reader.readGraphValues(invalidValues);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputGreaterThanThree() {
		String invalidValues = "AB5, BC5D, CD8";
		DataValidator reader = new DataValidator();

		reader.readGraphValues(invalidValues);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputDataWithoutComma() {
		String invalidValues = "AB5 / AB5";
		DataValidator reader = new DataValidator();

		reader.readGraphValues(invalidValues);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputDataWrongValue() {
		String invalidValues = "AB5, A15";
		DataValidator reader = new DataValidator();

		reader.readGraphValues(invalidValues);
	}

}
