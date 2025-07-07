package com.TP.review_service.models.enums;

public enum Rate {

    UNUSABLE(0.0, "Unusable / Worthless"),
    TERRIBLE(0.5, "Terrible"),
    VERY_POOR(1.0, "Very Poor"),
    POOR(1.5, "Poor"),
    BELOW_AVERAGE(2.0, "Below Average"),
    FAIR(2.5, "Fair / Mediocre"),
    AVERAGE(3.0, "Average"),
    ABOVE_AVERAGE(3.5, "Decent / Above Average"),
    GOOD(4.0, "Good"),
    VERY_GOOD(4.5, "Very Good"),
    EXCELLENT(5.0, "Excellent / Perfect");

    public final double value;
    public final String label;

    Rate(double value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Rate fromValue(double value) {
        for (Rate rate : values()) {
            if (Double.compare(rate.value, value) == 0) {
                return rate;
            }
        }
        throw new IllegalArgumentException("Invalid rate value: " + value);
    }

    @Override
    public String toString() {
        return label;
    }
}