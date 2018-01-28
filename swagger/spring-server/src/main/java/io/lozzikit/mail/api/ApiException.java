package io.lozzikit.mail.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

/**
 * API Exception, used to define the errors
 */
public class ApiException extends Exception {
    private int code;

    /**
     * Ctor
     *
     * @param code code of the Exception
     * @param msg message linked to code
     */
    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
