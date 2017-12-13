package io.lozzikit.mail.service;

import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.repository.JobRepository;
import io.lozzikit.mail.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<JobDto> getAllJobs() {
        return jobRepository.findAll().stream()
            .map(DtoFactory::createFrom)
            .collect(Collectors.toList());
    }

    public JobDto getJobById(Integer id) {
        return DtoFactory.createFrom(jobRepository.findOne(id));
    }
}
