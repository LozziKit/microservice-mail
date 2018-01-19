package io.lozzikit.mail.api;

import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class JobsApiController implements JobsApi {
    @Autowired
    private JobService jobService;

    @Override
    public ResponseEntity<List<JobDto>> jobsGet() {
        List<JobDto> mails = jobService.getAllJobs();

        if (mails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mails, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<JobDto> jobsIdGet(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> jobsIdDelete(@PathVariable Integer id) {
        try {
            jobService.deleteJobById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnsupportedOperationException ex) {
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }

}
