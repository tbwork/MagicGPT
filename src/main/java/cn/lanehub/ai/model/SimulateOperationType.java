package cn.lanehub.ai.model;

public enum SimulateOperationType {
    SEND_KEYS("SEND_KEYS"),
    CLICK("CLICK"),
    CLEAR("CLEAR"),
    SUBMIT("SUBMIT")
    ;

    private final String value;

    SimulateOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
