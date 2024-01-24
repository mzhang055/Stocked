/*
 * a separate class is created for enums since they are used throughout the program
 */
package model;

public enum RiskLevel {
    VERY_LOW("Very Low Risk"),
    LOW("Low Risk"),
    MODERATE("Moderate Risk"),
    HIGH("High Risk"),
    VERY_HIGH("Very High Risk");

    private final String description;

    RiskLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
