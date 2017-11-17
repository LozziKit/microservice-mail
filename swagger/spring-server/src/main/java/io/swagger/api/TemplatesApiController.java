package io.swagger.api;


import io.lozzikit.mail.api.TemplatesApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

@Controller
public class TemplatesApiController implements TemplatesApi {


    public ResponseEntity<Void> templatesGet() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> templatesPost() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
