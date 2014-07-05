package org.traccar.notify;

import org.traccar.model.Position;

/**
 * An interface for an implementation of a NotificationManager to handle triggering
 * of events of relevance in the tracking system. This loose coupling allows listening
 * processes to respond accordingly.
 *
 * @author djpentz
 * @since 2014/07/05 9:45 AM
 */
public interface NotificationManager {

    void triggerEvent(Position position) throws Exception;

}
