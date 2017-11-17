package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Activities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:11:51.948Z")

@Controller
public class HistoryApiController implements HistoryApi {
    public ResponseEntity<Activities> historyGet(@ApiParam(value = "Offset the list of returned results by this amount. Default is zero.") @RequestParam(value = "offset", required = false) Integer offset,
                                                 @ApiParam(value = "Number of items to retrieve. Default is 5, maximum is 100.") @RequestParam(value = "limit", required = false) Integer limit) {
        // do some magic!
        return new ResponseEntity<Activities>(HttpStatus.OK);
    }

}
