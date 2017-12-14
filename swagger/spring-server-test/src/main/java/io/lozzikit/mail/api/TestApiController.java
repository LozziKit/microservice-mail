package io.lozzikit.mail.api;

import io.lozzikit.mail.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestApiController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/tests/templates",
            method = RequestMethod.DELETE)
    public ResponseEntity<Object> dbTemplatesDelete() {
        testService.deleteTemplates();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/tests/templates",
            method = RequestMethod.POST)
    public ResponseEntity<Object> dbTemplatesPopulate(@RequestBody String set) {
        if(testService.populateTemplates(set)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tests/mails+jobs",
            method = RequestMethod.DELETE)
    public ResponseEntity<Object> dbMailsAndJobsDelete() {
        testService.deleteJobs();
        testService.deleteMails();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/tests/mails+jobs",
            method = RequestMethod.POST)
    public ResponseEntity<Object> dbMailsAndJobsPopulate(@RequestBody String set) {
        if(testService.populateJobsAndMails(set)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tests/mails/one",
            method = RequestMethod.GET)
    public ResponseEntity<Integer> dbMailArchivedId() {
        Integer archivedMailId = testService.getOneMailId();
        return new ResponseEntity<>(archivedMailId, HttpStatus.OK);
    }

    @RequestMapping(value = "/tests/jobs/one",
            method = RequestMethod.GET)
    public ResponseEntity<Integer> dbJobId(@RequestParam(name = "status", required = false) String status) {
        Integer jobId = testService.getOneJobId(status);
        return new ResponseEntity<>(jobId, HttpStatus.OK);
    }
}
