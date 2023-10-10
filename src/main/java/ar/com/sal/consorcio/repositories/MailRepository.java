package ar.com.sal.consorcio.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.Mail;

@Repository
public interface MailRepository extends CrudRepository<Mail, Integer>{    
}
