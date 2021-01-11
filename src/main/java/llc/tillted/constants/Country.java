package llc.tillted.constants;

public enum Country {
    UNITED_STATES("united-states");

    String rawValue;

    Country(String stringValue) {
        this.rawValue = stringValue;
    }

    public String getRawValue() {
        return rawValue;
    }
}
