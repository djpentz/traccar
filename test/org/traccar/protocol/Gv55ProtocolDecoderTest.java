package org.traccar.protocol;

import org.junit.Test;
import org.traccar.helper.TestDataManager;
import org.traccar.protocol.parser.MessageParser;
import org.traccar.protocol.parser.gv55.GTCRAParser;
import org.traccar.protocol.parser.gv55.GTFRIParser;
import org.traccar.protocol.parser.gv55.GTSOSParser;
import org.traccar.protocol.patterns.QueclinkMessages;

import java.util.HashMap;
import java.util.Map;

import static org.traccar.helper.DecoderVerifier.verify;

/**
 * Testing messages for the GV55 device.
 *
 * @author djpentz
 * @since 2014/07/31 1:22 PM
 */
public class Gv55ProtocolDecoderTest {
    @Test
    public void testDecode() throws Exception {

        Map<String, MessageParser> parsers = new HashMap<String, MessageParser>();
        parsers.put(QueclinkMessages.GTCRA, new GTCRAParser());
        parsers.put(QueclinkMessages.GTFRI, new GTFRIParser());
        parsers.put(QueclinkMessages.GTSOS, new GTSOSParser());

        QueclinkProtocolDecoder decoder = new QueclinkProtocolDecoder(null, parsers);
        decoder.setDataManager(new TestDataManager());

        verify(decoder.decode(null, null,
                "+RESP:GTSOS,0F0100,135790246811220,,,00,1,1,4.3,92,70.0,121.354335,31.222073,20090214013254,0460,0000,18d8,6141,00,2000.0,20090214093254,11F0"));

        verify(decoder.decode(null, null,
              "+RESP:GTCRA,0F0100,135790246811220,,0,4.3,92,70.0,121.354335,31.222073,2009021401 3254,0460,0000,18d8,6141,00,20090214093254,11F0"));
    }
}
