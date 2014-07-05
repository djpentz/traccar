package org.traccar.notify;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.client.ClientImpl;
import org.traccar.model.Position;

/**
 * A Jesque implementation of the NotificationManager interface. This implementation
 * expects the Jesque Job's to be implemented and executed elsewhere i.e. not in
 * the traccar server namespace.
 *
 * @author djpentz
 * @since 2014/07/05 9:54 AM
 */
public class JesqueNotificationManager implements NotificationManager {

    private Config config;
    public static final String NOTIFICATION_QUEUE_NAME = "positions";
    private ObjectMapper mapper;

    public JesqueNotificationManager(String redisHost, String redisPort) {
        ConfigBuilder builder = new ConfigBuilder();
        if (redisHost != null) {
            builder = builder.withHost(redisHost);
        }
        if (redisPort != null) {
            builder = builder.withPort(Integer.parseInt(redisPort));
        }
        config = builder.build();
        mapper = new ObjectMapper();
    }

    @Override
    public void triggerEvent(Position position) throws Exception {
        final Client client = new ClientImpl(config);
        Job job = new Job("PositionEvent", buildPositionStr(position));
        try {
            client.enqueue(NOTIFICATION_QUEUE_NAME, job);
        } finally {
            client.end();
        }
    }

    private String buildPositionStr(Position position) throws Exception {
            return mapper.writeValueAsString(position);
    }
}
