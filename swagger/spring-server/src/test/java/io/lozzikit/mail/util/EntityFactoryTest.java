package io.lozzikit.mail.util;

import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.entity.MailEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityFactoryTest {

    @Test
    void createFromMailDto() {
        MailDto dto = new MailDto();
        dto.from("test.one@testing.mail")
                .addToItem("a.a@a.a")
                .addToItem("b.b@b.b")
                .addCcItem("c.c@c.c")
                .addCcItem("d.d@d.d")
                .addCciItem("e.e@e.e")
                .addCciItem("f.f@f.f")
                .templateName("test-template-1")
                .putMapItem("name", "adam")
                .putMapItem("event", "anniversary");

        MailEntity entity = EntityFactory.createFrom(dto);

        assertEquals(dto.getFrom(), entity.getFrom());
        assertIterableEquals(dto.getTo(), entity.getTo());
        assertIterableEquals(dto.getCc(), entity.getCc());
        assertIterableEquals(dto.getCci(), entity.getCci());
        assertEquals(dto.getTemplateName(), entity.getTemplateName());
        assertIterableEquals(dto.getMap().entrySet(), entity.getMap().entrySet());
    }

    @Test
    void createFromJobDto() {
    }

    @Test
    void createFromTemplateDto() {
    }
}