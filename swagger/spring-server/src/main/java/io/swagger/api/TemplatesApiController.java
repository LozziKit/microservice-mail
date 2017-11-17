package io.swagger.api;

import io.lozzikit.mail.api.TemplatesApi;
import io.lozzikit.mail.api.model.TemplateDto;
import io.swagger.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

@Controller
public class TemplatesApiController implements TemplatesApi {
    @Autowired
    private TemplateService templateService;

    public ResponseEntity<List<TemplateDto>> templatesGet() {
        List<TemplateDto> templates = templateService.getAllTemplates();

        if (templates.isEmpty()) {
            return new ResponseEntity<List<TemplateDto>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<TemplateDto>>(templates, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesIdDelete(@PathVariable Integer id) {
        try {
            templateService.deleteTemplate(id);
        } catch (NullPointerException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TemplateDto> templatesIdGet(@PathVariable Integer id) {
        TemplateDto templateDto;

        try {
            templateDto = templateService.getTemplateById(id);
        } catch (NullPointerException e) {
            return new ResponseEntity<TemplateDto>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<TemplateDto>(templateDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesIdPut(@PathVariable Integer id, @RequestBody TemplateDto templateDto) {
        try {
            templateService.updateTemplate(id, templateDto);
        } catch (NullPointerException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> templatesPost(@RequestBody TemplateDto templateDto) {
        try {
            templateService.addTemplate(templateDto);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
