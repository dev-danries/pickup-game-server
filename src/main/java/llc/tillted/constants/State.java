package llc.tillted.constants;

public enum State {
    PENNSYLVANIA("pennsylvania");

    public String rawValue;

    State(String stringValue) {
        this.rawValue = stringValue;
    }

    public String getRawValue() {
        return rawValue;
    }


}
