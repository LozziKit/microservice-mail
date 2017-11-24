package io.lozzikit.mail.api;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MailsApiController implements MailsApi {
    @Autowired
    private MailService mailService;

    @Override
    public ResponseEntity<List<JobDto>> mailsPost(@RequestBody List<MailDto> mailDto) {
        try {
            return new ResponseEntity<>(mailService.sendMail(mailDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<ArchivedMailDto>> mailsGet() {
        List<ArchivedMailDto> mails = mailService.getAllMails();

        if (mails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mails, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArchivedMailDto> mailsIdGet(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(mailService.getMailById(id), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
