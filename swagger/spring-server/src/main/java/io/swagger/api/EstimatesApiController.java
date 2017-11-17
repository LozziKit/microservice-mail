package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.PriceEstimate;
import io.swagger.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:11:51.948Z")

@Controller
public class EstimatesApiController implements EstimatesApi {
    public ResponseEntity<List<PriceEstimate>> estimatesPriceGet(@NotNull @ApiParam(value = "Latitude component of start location.", required = true) @RequestParam(value = "start_latitude", required = true) Double startLatitude,
                                                                 @NotNull @ApiParam(value = "Longitude component of start location.", required = true) @RequestParam(value = "start_longitude", required = true) Double startLongitude,
                                                                 @NotNull @ApiParam(value = "Latitude component of end location.", required = true) @RequestParam(value = "end_latitude", required = true) Double endLatitude,
                                                                 @NotNull @ApiParam(value = "Longitude component of end location.", required = true) @RequestParam(value = "end_longitude", required = true) Double endLongitude) {
        // do some magic!
        return new ResponseEntity<List<PriceEstimate>>(HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> estimatesTimeGet(@NotNull @ApiParam(value = "Latitude component of start location.", required = true) @RequestParam(value = "start_latitude", required = true) Double startLatitude,
                                                          @NotNull @ApiParam(value = "Longitude component of start location.", required = true) @RequestParam(value = "start_longitude", required = true) Double startLongitude,
                                                          @ApiParam(value = "Unique customer identifier to be used for experience customization.") @RequestParam(value = "customer_uuid", required = false) UUID customerUuid,
                                                          @ApiParam(value = "Unique identifier representing a specific product for a given latitude & longitude.") @RequestParam(value = "product_id", required = false) String productId) {
        // do some magic!
        return new ResponseEntity<List<Product>>(HttpStatus.OK);
    }

}
