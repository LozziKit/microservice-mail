package io.lozzikit.mail.model;

/**
 * Status code enum
 */
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

    public static StatusEnum fromValue(String text) {
        for (StatusEnum b : StatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
