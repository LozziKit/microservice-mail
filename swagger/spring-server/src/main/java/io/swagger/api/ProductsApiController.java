package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:11:51.948Z")

@Controller
public class ProductsApiController implements ProductsApi {
    public ResponseEntity<List<Product>> productsGet(@NotNull @ApiParam(value = "Latitude component of location.", required = true) @RequestParam(value = "latitude", required = true) Double latitude,
                                                     @NotNull @ApiParam(value = "Longitude component of location.", required = true) @RequestParam(value = "longitude", required = true) Double longitude) {
        // do some magic!
        return new ResponseEntity<List<Product>>(HttpStatus.OK);
    }

}
