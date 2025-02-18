package ups.edu.parking.Servicios;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class MailService {

    @Resource(lookup = "java:jboss/mail/Gmail")
    private Session mailSession;

    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            LOGGER.info("Correo enviado exitosamente a: " + to);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al enviar el correo", e);
        }
    }
}