package io.lozzikit.mail.model;

public enum StatusEnum {
    DONE("DONE"),

    FAILED("FAILED"),

    CANCELLED("CANCELLED"),

    ONGOING("ONGOING"),

    INVALID("INVALID");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static StatusEnum fromValue(String text) {
        for (StatusEnum b : StatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
