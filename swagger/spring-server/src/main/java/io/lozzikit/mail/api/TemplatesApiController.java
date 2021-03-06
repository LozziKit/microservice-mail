package io.lozzikit.mail.api;

import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T13:22:11.816Z")

/**
 * Template api controller
 */
@Controller
public class TemplatesApiController implements TemplatesApi {
    @Autowired
    private TemplateService templateService;

    /**
     * POST for templates
     *
     * @param templateDto template to post
     * @return TemplateDto template posted
     */
    @Override
    public ResponseEntity<TemplateDto> templatesPost(@RequestBody TemplateDto templateDto) {
        try {
            return new ResponseEntity<>(templateService.addTemplate(templateDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Getter for templates
     *
     * @return list of templates
     */
    public ResponseEntity<List<TemplateDto>> templatesGet() {
        List<TemplateDto> templates = templateService.getAllTemplates();

        if (templates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    /**
     * Getter for template given its name
     *
     * @param name name of the template to fetch
     * @return TemplateDto template
     */
    @Override
    public ResponseEntity<TemplateDto> templatesNameGet(@PathVariable String name) {
        try {
            return new ResponseEntity<>(templateService.getTemplateByName(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a template given its name
     *
     * @param name name of template to delete
     * @return void
     */
    @Override
    public ResponseEntity<Void> templatesNameDelete(@PathVariable String name) {
        try {
            if (templateService.deleteTemplate(name) > 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT for new template
     *
     * @param name name of the new template
     * @param templateDto dto of given template
     * @return void
     */
    @Override
    public ResponseEntity<Void> templatesNamePut(@PathVariable String name, @RequestBody TemplateDto templateDto) {
        try {
            templateService.updateTemplate(name, templateDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
