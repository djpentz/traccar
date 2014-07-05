package org.traccar.protocol.parser;

import org.traccar.model.DataManager;

/**
 * The basic interface that all message parsers must implement.
 *
 * @author djpentz
 * @since 2014/05/07 10:48 PM
 */
public interface MessageParser {

    Object parse(DataManager mgr, Object msg) throws Exception;

    String getCommand();

    Integer getType();

}
