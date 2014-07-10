package org.traccar.message;

/**
 * These are the different types of messages that the system can expect to receive.
 *
 * @author djpentz
 * @since 2014/06/29 4:31 PM
 */
public enum MessageType {

    POSITION(0),
    MOVEMENT(1),
    STOP(2),
    ACCIDENT(3),
    PANIC(4),
    PLEASE_CALL_ME(5),
    SPEEDING(6),
    SLEEP_TIMEOUT(7),
    GEOFENCE_ALERT(8),
    TEMP_ABOVE(9),
    TEMP_BELOW(10);

    private int value;

    private MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageType fromValue(Integer value) {
        if (value != null) {
            for (MessageType type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
        }

        // you may return a default value
        return getDefault();
        // or throw an exception
        // throw new IllegalArgumentException("Invalid color: " + value);
    }

    public int toValue() {
        return value;
    }

    public static MessageType getDefault() {
        return POSITION;
    }

}
