package io.swagger.api;

import io.lozzikit.mail.api.TemplatesApi;
import io.lozzikit.mail.api.model.TemplateDto;
import io.swagger.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

@Controller
public class TemplatesApiController implements TemplatesApi {
    @Autowired
    private TemplateService templateService;

    public ResponseEntity<Void> templatesGet() {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesIdDelete(Integer id) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesIdGet(Integer id) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesIdPut(Integer id) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesPost(TemplateDto templateDto) {
        templateService.addTemplate(templateDto);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
