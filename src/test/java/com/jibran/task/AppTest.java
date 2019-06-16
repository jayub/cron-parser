package com.jibran.task;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void parseTimeValue_AsteriskValueForMinute() {
		StringBuilder expectedValue = new StringBuilder();
		for (int i = 0; i <= 59; i++) {
			expectedValue.append(i);
			if (i != 59) {
				expectedValue.append(" ");
			}
		}

		assertEquals(expectedValue.toString(), App.parseTimeValue(TimeType.MINUTE, "*"));
	}

	@Test
	public void parseTimeValue_AsteriskValueForHour() {
		StringBuilder expectedValue = new StringBuilder();
		for (int i = 0; i <= 23; i++) {
			expectedValue.append(i);
			if (i != 23) {
				expectedValue.append(" ");
			}
		}

		assertEquals(expectedValue.toString(), App.parseTimeValue(TimeType.HOUR, "*"));
	}

	@Test
	public void parseTimeValue_AsteriskValueForDayOfMonth() {
		StringBuilder expectedValue = new StringBuilder();
		for (int i = 1; i <= 31; i++) {
			expectedValue.append(i);
			if (i != 31) {
				expectedValue.append(" ");
			}
		}
		assertEquals(expectedValue.toString(), App.parseTimeValue(TimeType.DAY_OF_MONTH, "*"));
	}

	@Test
	public void parseTimeValue_AsteriskValueForMonth() {
		StringBuilder expectedValue = new StringBuilder();
		for (int i = 1; i <= 12; i++) {
			expectedValue.append(i);
			if (i != 12) {
				expectedValue.append(" ");
			}
		}
		assertEquals(expectedValue.toString(), App.parseTimeValue(TimeType.MONTH, "*"));
	}

	@Test
	public void parseTimeValue_AsteriskValueForDayOfWeek() {
		StringBuilder expectedValue = new StringBuilder();
		for (int i = 0; i <= 6; i++) {
			expectedValue.append(i);
			if (i != 6) {
				expectedValue.append(" ");
			}
		}
		assertEquals(expectedValue.toString(), App.parseTimeValue(TimeType.DAY_OF_WEEK, "*"));
	}

	@Test
	public void parseTimeValue_SimpleNumberValueForHour() {
		String expectedValue = "11";

		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "11"));
	}

	@Test
	public void parseTimeValue_SingleNumberValueForHour() {
		String expectedValue = "11";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "11"));
	}

	@Test
	public void parseTimeValue_MultipleNumberValueForHour() {
		String expectedValue = "3 5 9";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "3,5,9"));
	}

	@Test
	public void parseTimeValue_RangeValueForHour() {
		String expectedValue = "4 5 6 7 8 9";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "4-9"));
	}

	@Test
	public void parseTimeValue_RecurringValueForHour() {
		String expectedValue = "0 4 8 12 16 20";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "*/4"));
	}

	@Test
	public void parseTimeValue_MutlipleRecurringValueForHour() {
		String expectedValue = "0 4 5 8 10 12 15 16 20";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "*/4,*/5"));
	}

	@Test
	public void parseTimeValue_MutipleRangeValueForHour() {
		String expectedValue = "3 4 5 8 9 10 21 22 23";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "3-5,21-23,8-10"));
	}

	@Test
	public void parseTimeValue_CombinationValueForHour() {
		String expectedValue = "0 3 6 7 8 11 12 13 14 16 18";
		assertEquals(expectedValue, App.parseTimeValue(TimeType.HOUR, "*/6,3,7,11-14,*/8"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTimeValue_ValueExceedsUpperLimitForHour() {
		App.parseTimeValue(TimeType.HOUR, "24");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTimeValue_ValueBelowLowerLimitForMonth() {
		App.parseTimeValue(TimeType.MONTH, "0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTimeValue_RecurringValueExceedsUpperLimitForMonth() {
		App.parseTimeValue(TimeType.MONTH, "*/13");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTimeValue_RangeValueInWrongOrderForMonth() {
		App.parseTimeValue(TimeType.MONTH, "5-3");
	}
}
