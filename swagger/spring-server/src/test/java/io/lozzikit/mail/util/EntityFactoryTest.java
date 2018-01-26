package io.lozzikit.mail.util;

import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.entity.MailEntity;
import org.junit.Assert;
import org.junit.Test;

public class EntityFactoryTest {
    @Test
    public void createFromMailDto() {
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

        Assert.assertEquals(dto.getFrom(), entity.getFrom());
        Assert.assertArrayEquals(dto.getTo().toArray(), entity.getTo().toArray());
        Assert.assertArrayEquals(dto.getCc().toArray(), entity.getCc().toArray());
        Assert.assertArrayEquals(dto.getCci().toArray(), entity.getCci().toArray());
        Assert.assertEquals(dto.getTemplateName(), entity.getTemplateName());
        Assert.assertArrayEquals(dto.getMap().entrySet().toArray(), entity.getMap().entrySet().toArray());
    }

    @Test
    public void createFromJobDto() {
    }

    @Test
    public void createFromTemplateDto() {
    }
}