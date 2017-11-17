package io.swagger.api;

import io.swagger.model.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:11:51.948Z")

@Controller
public class MeApiController implements MeApi {
    public ResponseEntity<Profile> meGet() {
        // do some magic!
        return new ResponseEntity<Profile>(HttpStatus.OK);
    }

}
