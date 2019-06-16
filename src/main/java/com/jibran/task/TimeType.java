package com.jibran.task;

public enum TimeType {

	MINUTE("Minute", 0, 59),

	HOUR("Hour", 0, 23),

	DAY_OF_MONTH("Day of month", 1, 31),

	MONTH("Month", 1, 12),

	DAY_OF_WEEK("Day of week", 0, 6);

	private String name;
	private int lowerLimit;
	private int upperLimit;

	TimeType(String name, int lowerLimit, int upperLimit) {

		this.name = name;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}

	public int getUpperLimit() {
		return upperLimit;
	}

	public String getName() {
		return name;
	}

}