package un.lab.esa.demo.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import un.lab.esa.demo.model.Book;
import un.lab.esa.demo.model.Change;
import un.lab.esa.demo.model.Mail;
import un.lab.esa.demo.model.enums.ChangeType;

import java.lang.reflect.Field;

@Service
public class JmsSenderService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendCreateChange(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = obj.getClass();
        String entity = clazz.getSimpleName();
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        int id = field.getInt(obj);

        Mail mail = new Mail();
        mail.setReceiver("i.matiushkina@gmail.com");
        mail.setSubject(entity + " [create]");
        String body = "Тип изменения: create\n" +
                entity+" " + obj.toString();
        mail.setBody(body);
        jmsTemplate.convertAndSend("mailbox", mail);
        Change change = new Change();
        change.setType(ChangeType.create);
        change.setEntity(entity);
        change.setEntity_id(id);
        change.setInfo(body);
        jmsTemplate.convertAndSend("changebox", change);
    }

    public void sendUpdateChange(Object obj, Object newObj) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = obj.getClass();
        String entity = clazz.getSimpleName();
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        int id = field.getInt(obj);

        Mail mail = new Mail();
        mail.setReceiver("i.matiushkina@gmail.com");
        mail.setSubject(entity + " [update]");
        String body = "Тип изменения:  update\n" +
                "Старая версия: " + obj.toString() + "\n" +
                "Новая версия: " + newObj.toString();
        mail.setBody(body);
        jmsTemplate.convertAndSend("mailbox", mail);
        Change change = new Change();
        change.setType(ChangeType.update);
        change.setEntity(entity);
        change.setEntity_id(id);
        change.setInfo(body);
        jmsTemplate.convertAndSend("changebox", change);
    }

    public void sendDeleteChange(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = obj.getClass();
        String entity = clazz.getSimpleName();
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        int id = field.getInt(obj);

        Mail mail = new Mail();
        mail.setReceiver("i.matiushkina@gmail.com");
        mail.setSubject(entity + " [delete]");
        String body = "Тип изменения: delete\n" +
                entity+" " + obj.toString();
        mail.setBody(body);
        jmsTemplate.convertAndSend("mailbox", mail);
        Change change = new Change();
        change.setType(ChangeType.delete);
        change.setEntity(entity);
        change.setEntity_id(id);
        change.setInfo(body);
        jmsTemplate.convertAndSend("changebox", change);
    }

}
