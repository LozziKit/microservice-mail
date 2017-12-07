package io.lozzikit.mail.api;

import io.lozzikit.mail.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestApiController {
    @Autowired
    private TestService testService;
}
