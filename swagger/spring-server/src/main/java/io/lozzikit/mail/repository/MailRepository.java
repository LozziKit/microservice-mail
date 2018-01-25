package io.lozzikit.mail.repository;

import io.lozzikit.mail.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Integer> {
    List<MailEntity> findAllByOrderByIdDesc();
}
