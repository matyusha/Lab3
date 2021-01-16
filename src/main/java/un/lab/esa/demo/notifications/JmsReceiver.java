package un.lab.esa.demo.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import un.lab.esa.demo.model.Change;
import un.lab.esa.demo.model.Mail;
import un.lab.esa.demo.repository.ChangeRepository;
import un.lab.esa.demo.repository.MailRepository;

@Component
public class JmsReceiver {

    private final EmailSenderService emailSenderService;
    private final ChangeRepository changeRepository;
    private final MailRepository mailRepository;

    @Autowired
    public JmsReceiver(EmailSenderService emailSenderService, ChangeRepository changeRepository, MailRepository mailRepository) {
        this.emailSenderService = emailSenderService;
        this.changeRepository = changeRepository;
        this.mailRepository = mailRepository;
    }

    @JmsListener(destination = "changebox", containerFactory = "myFactory")
    public void receiveChange(Change change) {
        changeRepository.save(change);
    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Mail mail) {
        emailSenderService.send(mail);
        mailRepository.save(mail);
    }
}
