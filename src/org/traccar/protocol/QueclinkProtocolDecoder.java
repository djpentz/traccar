package org.traccar.protocol;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.traccar.BaseProtocolDecoder;
import org.traccar.ServerManager;
import org.traccar.model.Position;
import org.traccar.protocol.parser.MessageParser;
import org.traccar.protocol.patterns.QueclinkPatterns;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author djpentz
 * @since 2014/07/04 12:57 PM
 */
public class QueclinkProtocolDecoder extends BaseProtocolDecoder {

    private Map<String, MessageParser> parsers;

    public QueclinkProtocolDecoder(ServerManager serverManager, Map<String, MessageParser> parsers) {
        super(serverManager);

        this.parsers = parsers;
    }

    @Override
    protected Object decode(
            ChannelHandlerContext ctx, Channel channel, Object msg)
            throws Exception {

        String sentence = (String) msg;
        System.out.println(new Date() + ": " + sentence);

        // Let's first see which message it is
        Pattern messageHeaderPattern = QueclinkPatterns.getMessageHeaderPattern();
        Matcher messageHeaderParser = messageHeaderPattern.matcher(sentence);
        if (!messageHeaderParser.matches()) {
            return null;
        }
        String msgType = messageHeaderParser.group(2);
        if (msgType == null) {
            return null;
        }

        MessageParser parser = parsers.get(msgType);
        if (parser == null) {
            return null;
        }

        Position position = (Position) parser.parse(getDataManager(), msg);
        position.setCommand(parser.getCommand());
        position.setType(parser.getType());
        return position;
    }

}
