package io.lozzikit.mail.repository;

import io.lozzikit.mail.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Integer> {
    TemplateEntity findOneByName(String name);

    @Transactional
    Long deleteOneByName(String name);
}
