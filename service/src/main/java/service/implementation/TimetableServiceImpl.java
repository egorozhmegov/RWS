package service.implementation;

import exception.ServiceMessageException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.interfaces.TimetableService;
import util.TimetableMessage;

import javax.jms.*;

/**
 * Timetable service implementation.
 */
@Service("timetableServiceImpl")
public class TimetableServiceImpl implements TimetableService {

    private final static Logger LOG = LoggerFactory.getLogger(TimetableServiceImpl.class);

    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static final String SUBJECT = "RWS_QUEUE";

    /**
     * Send message on table.
     *
     * @param message TimetableMessage
     */
    @Override
    public void sendTimetableMessage(TimetableMessage message){
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection
                    .createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(SUBJECT);

            MessageProducer producer = session.createProducer(destination);
            TextMessage mes = session
                    .createTextMessage(
                            message.getStation() + " " +
                                    message.getTrain() + " " +
                                    message.getStatus() + " " +
                                    message.getMessage()
                    );
            producer.send(mes);
            connection.close();
        } catch (JMSException e) {
            LOG.error("Message not sent.");
            throw new ServiceMessageException("Message not sent.");
        }
    }
}
