package io.lozzikit.mail.api;

import javax.xml.bind.annotation.XmlTransient;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

@javax.xml.bind.annotation.XmlRootElement
public class ApiResponseMessage {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int OK = 4;
    public static final int TOO_BUSY = 5;

    int code;
    String type;
    String message;

    /**
     * Ctor
     */
    public ApiResponseMessage() {
    }

    /**
     * Codes to sent back for api responses
     *
     * @param code code to fetch
     * @param message message linked
     */
    public ApiResponseMessage(int code, String message) {
        this.code = code;
        switch (code) {
            case ERROR:
                setType("error");
                break;
            case WARNING:
                setType("warning");
                break;
            case INFO:
                setType("info");
                break;
            case OK:
                setType("ok");
                break;
            case TOO_BUSY:
                setType("too busy");
                break;
            default:
                setType("unknown");
                break;
        }
        this.message = message;
    }

    /**
     * Getter for code
     *
     * @return code
     */
    @XmlTransient
    public int getCode() {
        return code;
    }

    /**
     * Setter for code
     *
     * @param code code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Getter for type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     *
     * @param type type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message
     *
     * @param message message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
