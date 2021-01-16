package un.lab.esa.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import un.lab.esa.demo.model.Mail;

@Repository
public interface MailRepository extends CrudRepository<Mail, Integer> {
}
