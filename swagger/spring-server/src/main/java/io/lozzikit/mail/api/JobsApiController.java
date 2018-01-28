package io.lozzikit.mail.api;

import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Job Api Controller
 */
@Controller
public class JobsApiController implements JobsApi {
    @Autowired
    private JobService jobService;

    /**
     * Getter for a job
     *
     * @return list of job
     */
    @Override
    public ResponseEntity<List<JobDto>> jobsGet() {
        List<JobDto> mails = jobService.getAllJobs();

        if (mails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mails, HttpStatus.OK);
    }

    /**
     * Getter for a job given its id
     *
     * @param id id of job to fetch
     * @return job
     */
    @Override
    public ResponseEntity<JobDto> jobsIdGet(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete for given job with id
     *
     * @param id id of job to delete
     *
     * @return void
     */
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
