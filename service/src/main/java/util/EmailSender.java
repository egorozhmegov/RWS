package util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of email sender. Use gmail smtp server.
 */
public class EmailSender {

    private final static Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    private String username;
    private String password;
    private Properties props;
    private long trainNumber;

    public EmailSender(long trainNumber) {
        this.trainNumber = trainNumber;
        this.username = "rws.inf@gmail.com";
        this.password = "egor1229@-@";
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    /**
     * Send email method. Takes mail message parameters and send message.
     *
     * @param toEmail String
     */
    public void send(String toEmail){
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("RWS TICKET SUPPORT");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Thank you for using the services of our company!");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();

            String filename = String.format(
                    "C:/Users/Ozhmegov/Desktop/t-systems/RWS/service/src/main/java/util/tickets/ticket_%s.pdf", trainNumber
            );
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(String.format("ticket_%s.pdf", trainNumber));
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            LOG.info(String.format("Message sended on email: %s", toEmail));
        } catch (MessagingException e) {
            LOG.error(String.format("Message not sended on email: %s", toEmail));
            throw new RuntimeException(e);
        }
    }
}
