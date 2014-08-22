package org.traccar.protocol.parser.gt200;

import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;
import org.traccar.helper.Log;
import org.traccar.model.DataManager;
import org.traccar.model.ExtendedInfoFormatter;
import org.traccar.model.Position;
import org.traccar.protocol.parser.QueclinkParser;
import org.traccar.message.MessageType;
import org.traccar.protocol.patterns.QueclinkMessages;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

/**
 * A parser for the GTTRI response.
 *
 * @author djpentz
 * @since 2014/06/02 9:55 AM
 */
public class GTTRIParser extends QueclinkParser {

    @Override
    public String getCommand() {
        return QueclinkMessages.GTTRI;
    }

    @Override
    public Integer getType() {
        return MessageType.POSITION.toValue();
    }

    public Object parse(DataManager mgr, Object msg) throws Exception {
        String sentence = (String) msg;

        StringReader reader = new StringReader(sentence);
        ICsvListReader listReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE);

        List<String> list = listReader.read();
        Position position = new Position();
        ExtendedInfoFormatter extendedInfo = new ExtendedInfoFormatter("gv320");
        int i = 0;

        list.get(i++); // BUFF|ACK|RESP:GTTRI

        String imei = list.get(i++); // Get device by IMEI
        try {
            position.setDeviceId(mgr.getDeviceByImei(imei).getId());
        } catch (Exception error) {
            Log.warning("Unknown device - " + imei);
            return null;
        }

        Integer number = getNumber(list.get(i++));
        list.get(i++); // reserved1
        list.get(i++); // reserved2
        String gpsFix = list.get(i++);
        Double speed = getSpeed(list.get(i++));
        Integer heading = getHeading(list.get(i++));
        Double altitude = getAltitude(list.get(i++));
        Integer gpsAccuracy = getGpsAccuracy(list.get(i++));
        Double longitude = getLongitude(list.get(i++));
        Double latitude = getLatitude(list.get(i++));
        Date sendTime = getSendTime(list.get(i++));
        String mcc = getMcc(list.get(i++));
        String mnc = getMnc(list.get(i++));
        String lac = getLac(list.get(i++));
        String cellId = getCellId(list.get(i++));
        String ta = list.get(i++);
        Integer countNumber = getCountNumber(list.get(i));
        String version = list.get(i++);

        position.setAltitude(altitude);
        position.setCourse(heading.doubleValue());
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setTime(sendTime);
        position.setSpeed(speed);
        position.setServerTime(new Date());
        position.setGpsAccuracy(gpsAccuracy);

        // Cell information etc
        setConstantData(position, mcc, mnc, lac, cellId);
        position.setExtendedInfo(extendedInfo.toString());

        return position;
    }

}
