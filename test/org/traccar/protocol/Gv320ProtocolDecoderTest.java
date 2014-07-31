package org.traccar.protocol;

import org.junit.Test;
import org.traccar.helper.TestDataManager;
import org.traccar.protocol.parser.MessageParser;
import org.traccar.protocol.parser.gv320.GTERIParser;
import org.traccar.protocol.parser.gv320.GTSOSParser;
import org.traccar.protocol.patterns.QueclinkMessages;

import java.util.HashMap;
import java.util.Map;

import static org.traccar.helper.DecoderVerifier.verify;

/**
 * Test messages for the GV320.
 *
 * @author djpentz
 * @since 2014/07/31 4:54 PM
 */
public class Gv320ProtocolDecoderTest {
    @Test
    public void testDecode() throws Exception {

        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        parsers.put(QueclinkMessages.GTERI, new GTERIParser());
        parsers.put(QueclinkMessages.GTSOS, new GTSOSParser());

        QueclinkProtocolDecoder decoder = new QueclinkProtocolDecoder(null, parsers);
        decoder.setDataManager(new TestDataManager());

        verify(decoder.decode(null, null,
                "+RESP:GTSOS,0E0100,135790246811220,,,00,1,1,4.3,92,70.0,121.354335,31.222073,20090214013254,0460,0000,18d8,6141,00,2000.0,20090214093254,11F0"));

    }
}
