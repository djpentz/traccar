package org.traccar.protocol.factory;

import org.traccar.ServerManager;
import org.traccar.protocol.QueclinkProtocolDecoder;
import org.traccar.protocol.parser.MessageParser;
import org.traccar.protocol.parser.gt200.GTMSAParser;
import org.traccar.protocol.parser.gt200.GTSOSParser;
import org.traccar.protocol.parser.gt200.GTTRIParser;
import org.traccar.protocol.parser.gv320.GTERIParser;
import org.traccar.protocol.parser.gv55.GTFRIParser;

import java.util.HashMap;
import java.util.Map;

import static org.traccar.protocol.patterns.QueclinkMessages.*;

/**
 * A factory for convenience to build QueclinkProtocolDecoder implementations.
 *
 * @author djpentz
 * @since 2014/07/04 1:39 PM
 */
public class QueclinkDecoderFactory {

    public static QueclinkProtocolDecoder buildGV320Decoder(ServerManager mgr) {
        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        parsers.put(GTERI, new GTERIParser());

        return new QueclinkProtocolDecoder(mgr, parsers);
    }

    public static QueclinkProtocolDecoder buildGT200Decoder(ServerManager mgr) {
        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        parsers.put(GTMSA, new GTMSAParser());
        parsers.put(GTSOS, new GTSOSParser());
        parsers.put(GTTRI, new GTTRIParser());

        return new QueclinkProtocolDecoder(mgr, parsers);
    }

    public static QueclinkProtocolDecoder buildGV55Decoder(ServerManager mgr) {
        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        parsers.put(GTFRI, new org.traccar.protocol.parser.gv55.GTFRIParser());

        return new QueclinkProtocolDecoder(mgr, parsers);
    }

    public static QueclinkProtocolDecoder buildGV65Decoder(ServerManager mgr) {
        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        parsers.put(GTFRI, new org.traccar.protocol.parser.gv65.GTFRIParser());

        return new QueclinkProtocolDecoder(mgr, parsers);
    }

    public static QueclinkProtocolDecoder buildGT300Decoder(ServerManager mgr) {
        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        // No parsers implemented for this just yet - but add them here when they're done

        return new QueclinkProtocolDecoder(mgr, parsers);
    }
}
