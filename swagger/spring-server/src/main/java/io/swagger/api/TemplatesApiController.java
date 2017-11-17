package io.swagger.api;

import io.lozzikit.mail.api.TemplatesApi;
import io.lozzikit.mail.api.model.TemplateDto;
import io.swagger.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

@Controller
public class TemplatesApiController implements TemplatesApi {
    @Autowired
    private TemplateService templateService;

    public ResponseEntity<List<TemplateDto>> templatesGet() {
        List<TemplateDto> templates = templateService.getAllTemplates();

        if(templates.size() == 0) {
            return new ResponseEntity<List<TemplateDto>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<TemplateDto>>(templates, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesPost(TemplateDto templateDto) {
        templateService.addTemplate(templateDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
