package io.lozzikit.mail.api;

import io.lozzikit.mail.api.model.JobDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class JobsApiController implements JobsApi {
    @Override
    public ResponseEntity<List<JobDto>> jobsGet() {
        return null;
    }

    @Override
    public ResponseEntity<Void> jobsIdDelete(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<JobDto> jobsIdGet(Integer id) {
        return null;
    }
}
