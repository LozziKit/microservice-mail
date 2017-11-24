package io.lozzikit.mail.repository;

import io.lozzikit.mail.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Integer> {
    List<TemplateEntity> findByName(String name);
}
