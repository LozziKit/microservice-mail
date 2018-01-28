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

/**
 * Mail api controller
 */
@Controller
public class MailsApiController implements MailsApi {
    @Autowired
    private MailService mailService;

    /**
     * Post for mails DTO
     *
     * @param mailDtos mail to post
     * @return list of job related to mails posted
     */
    @Override
    public ResponseEntity<List<JobDto>> mailsPost(@RequestBody List<MailDto> mailDtos) {
        try {
            return new ResponseEntity<>(mailService.sendMails(mailDtos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Getter for mails
     *
     * @return list of mails
     */
    @Override
    public ResponseEntity<List<ArchivedMailDto>> mailsGet() {
        List<ArchivedMailDto> mails = mailService.getAllMails();

        if (mails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mails, HttpStatus.OK);
    }

    /**
     * Getter for mail given its id
     *
     * @param id id of mail to fetch
     * @return archivedMail
     */
    @Override
    public ResponseEntity<ArchivedMailDto> mailsIdGet(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(mailService.getMailById(id), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
