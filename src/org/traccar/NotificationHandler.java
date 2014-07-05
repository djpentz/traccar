package org.traccar;

import org.jboss.netty.channel.*;
import org.traccar.notify.NotificationManager;
import org.traccar.model.Position;

import java.util.List;

/**
 * This handler will issue notifications for each position message received and stored
 * in the database. This will allow other listening processes to react to those
 * notifications.
 *
 * @author djpentz
 * @since 2014/07/05 10:44 AM
 */
public class NotificationHandler implements ChannelUpstreamHandler {

    private NotificationManager notificationManager;

    public NotificationHandler(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent evt) throws Exception {
        if (!(evt instanceof MessageEvent)) {
            ctx.sendUpstream(evt);
            return;
        }

        ctx.sendUpstream(evt);
        MessageEvent e = (MessageEvent) evt;
        if (notificationManager != null) {
            if (e.getMessage() instanceof Position) {
                Position position = (Position) e.getMessage();
                notificationManager.triggerEvent(position);
            } else if (e.getMessage() instanceof List) {
                List<Position> positions = (List<Position>) e.getMessage();
                for (Position position : positions) {
                    notificationManager.triggerEvent(position);
                }
            }
        }
    }

}
