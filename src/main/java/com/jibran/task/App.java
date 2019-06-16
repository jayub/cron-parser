package com.jibran.task;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Hello world!
 */
public final class App {
	private App() {
	}

	/**
	 * @param args The arguments of the program.
	 */
	public static void main(String[] args) {

		if (args.length != 6) {
			showUsage();
			return;
		}

		try {
			String minute = parseTimeValue(TimeType.MINUTE, args[0]);
			String hour = parseTimeValue(TimeType.HOUR, args[1]);
			String dayOfMonth = parseTimeValue(TimeType.DAY_OF_MONTH, args[2]);
			String month = parseTimeValue(TimeType.MONTH, args[3]);
			String dayOfWeek = parseTimeValue(TimeType.DAY_OF_WEEK, args[4]);
			String command = args[5];

			printResult(minute, hour, dayOfMonth, month, dayOfWeek, command);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("Please provide the valid values.");
		}
	}

	/**
	 * It takes the value for one type of time (e.g. day of week) and returns the
	 * space-separated string for the given value.
	 * 
	 * @param timeType Type of the item (from enum TimeType) which we are parsing
	 *                 e.g. MINUTE, HOUR etc
	 * @param value    String value from the program args for this type of time e.g.
	 *                 5,13,17-19,*
	 * @return space-separated String for the given value
	 */
	protected static String parseTimeValue(TimeType timeType, String value) {
		Set<Integer> returnValues = new TreeSet<>();

		// split the given value using comma
		String[] individualValues = value.trim().split(",");

		// now parse each of these values to
		for (String individualValue : individualValues) {
			returnValues.addAll(parseIndividualTimeValue(timeType, individualValue));
		}

		// convert to string
		return convertToString(returnValues);
	}

	/**
	 * It parses the given value as per the crontab rules and returns the set of all
	 * the possible integer values based on the given value
	 * 
	 * @param timeType Type of the item (from enum TimeType) which we are parsing
	 *                 (e.g. MINUTE, HOUR etc)
	 * @param value    individual item from the comma-separated list for given type
	 *                 (e.g 5-8)
	 * @return
	 */
	private static Set<Integer> parseIndividualTimeValue(TimeType timeType, String value) {

		// get the lower/upper limts and the name of the type of time which we are
		// parsing
		int lowerLimit = timeType.getLowerLimit();
		int upperLimit = timeType.getUpperLimit();
		String name = timeType.getName();

		Set<Integer> returnValues = new HashSet<>();
		int intValue;

		if (value.matches("^\\d+$")) {
			// value is a simple number
			intValue = Integer.parseInt(value);

			// make sure value is within allowed range
			if (intValue < lowerLimit || intValue > upperLimit) {
				throw new IllegalArgumentException(value + " is not a valid value for " + name);
			}
			returnValues.add(intValue);

		} else if (value.equals("*")) {
			// value is just an asterisk
			// add all possible values for this unit of time
			for (int i = lowerLimit; i <= upperLimit; i++) {
				returnValues.add(i);
			}

		} else if (value.matches("^\\d+-\\d+$")) {
			// value is a range
			// add all values in the range
			String[] range = value.split("-");
			int numberFrom = Integer.parseInt(range[0]);
			int numberTo = Integer.parseInt(range[1]);

			// number should be within the allowed range
			if (numberFrom < lowerLimit || numberFrom > upperLimit || numberTo < lowerLimit || numberTo > upperLimit
					|| numberFrom > numberTo) {
				throw new IllegalArgumentException(value + " is not a valid value for " + name);
			}

			for (int i = numberFrom; i <= numberTo; i++) {
				returnValues.add(i);
			}
		} else {

			Pattern p = Pattern.compile("^\\*/(\\d+)$");
			Matcher m = p.matcher(value);

			if (m.find()) {
				// value is in "*/number" format
				int baseNo = Integer.parseInt(m.group(1));

				// number should be within the allowed range
				if (baseNo < 1 || baseNo > upperLimit) {
					throw new IllegalArgumentException(value + " is not a valid value for " + name);
				}

				int multiplier = 0;
				int multipliedValue = 0;

				while (multipliedValue <= upperLimit) {

					// for those values which where 0 is acceptable value (e.g. minutes, hours, day
					// of week),
					// we need to include 0 in the output
					// we need to exclude the upper limit from the output
					// for those values which where 0 is not acceptable value (e.g. day of month,
					// month),
					// we need to exclude 0 from the output
					// we need to include the upper limit in the output
					if (multipliedValue >= lowerLimit || (multipliedValue == upperLimit && lowerLimit > 0)) {
						returnValues.add(multipliedValue);
					}

					multiplier++;
					multipliedValue = baseNo * multiplier;
				}

			} else {
				// invalid input
				throw new IllegalArgumentException(value + " is not a valid value for " + name);
			}
		}

		return returnValues;
	}

	/**
	 * It converts the given set of Integer values in a space-separated string
	 * 
	 * @param valueSet - Set of Integer values
	 * @return space-separated String consisting of the value from the given set
	 */
	private static String convertToString(Set<Integer> valueSet) {

		Set<String> stringValueSet = new LinkedHashSet<>(valueSet.size());

		for (Integer value : valueSet) {
			stringValueSet.add(Integer.toString(value));
		}
		return String.join(" ", stringValueSet);
	}

	/**
	 * It prints the output of the program on the screen
	 * 
	 * @param minute     - String containing minute values
	 * @param hour       - String containing hour values
	 * @param month      - String containing month values
	 * @param dayOfMonth - String containing day of month values
	 * @param dayOfWeek  - String containing day of week values
	 * @param command    - String containing the command
	 */
	private static void printResult(String minute, String hour, String month, String dayOfMonth, String dayOfWeek,
			String command) {
		System.out.println("");
		System.out.println("minute         " + minute);
		System.out.println("hour           " + hour);
		System.out.println("day of month   " + month);
		System.out.println("month          " + dayOfMonth);
		System.out.println("day of week    " + dayOfWeek);
		System.out.println("command        " + command);
	}

	/**
	 * It prints the help text for the user on the screen
	 */
	private static void showUsage() {
		System.out.println("Please pass the arguments as shown below.");
		System.out.println("program-name <minute> <hour> <day_of_month> <month> <day_of_week> <command>");
		System.out.println("e.g. cron-parser 30 5 * * 1 /user/bin/find");
	}
}
