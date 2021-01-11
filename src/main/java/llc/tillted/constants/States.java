package llc.tillted.constants;

public enum States {
    PENNSYLVANIA("pennsylvania");

    public String rawValue;

    States(String stringValue) {
        this.rawValue = stringValue;
    }

    public String getRawValue() {
        return rawValue;
    }


}
