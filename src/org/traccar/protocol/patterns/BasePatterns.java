package org.traccar.protocol.patterns;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.traccar.protocol.patterns.QueclinkPatterns.MessageTypePattern;

/**
 * The base class for all Patterns utility classes.
 *
 * @author djpentz
 * @since 2014/05/07 9:46 PM
 */
public class BasePatterns {

    protected static Pattern messageHeaderPattern = Pattern.compile(MessageTypePattern);

    protected static Map<String, Pattern> patterns;
    static {
        patterns = new HashMap<String, Pattern>();
    }

    public static Pattern getPatternFor(String command) {
        return patterns.get(command);
    }

}
