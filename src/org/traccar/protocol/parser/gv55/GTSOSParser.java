package org.traccar.protocol.parser.gv55;

import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;
import org.traccar.helper.Log;
import org.traccar.message.MessageType;
import org.traccar.model.DataManager;
import org.traccar.model.ExtendedInfoFormatter;
import org.traccar.model.Position;
import org.traccar.protocol.parser.QueclinkParser;
import org.traccar.protocol.patterns.QueclinkMessages;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

/**
 * Panic alert from GV55.
 *
 * @author djpentz
 * @since 2014/07/30 6:05 PM
 */
public class GTSOSParser extends QueclinkParser {

    @Override
    public String getCommand() {
        return QueclinkMessages.GTSOS;
    }

    @Override
    public Integer getType() {
        return MessageType.PANIC.toValue();
    }

    /**
     * Example GTSOS for reference
     * +RESP:GTSOS,0F0100,135790246811220,,,00,1,1,4.3,92,70.0,121.354335,31.222073,200902 14013254,0460,0000,18d8,6141,00,2000.0,20090214093254,11F0$
     * @param mgr The DB manager class
     * @param msg The message, which should be a String
     * @return A Position object which is stored in the DB
     * @throws Exception Anything can happen, be prepared
     */
    @Override
    public Object parse(DataManager mgr, Object msg) throws Exception {
        String sentence = (String) msg;

        StringReader reader = new StringReader(sentence);
        ICsvListReader listReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE);

        List<String> list = listReader.read();
        Position position = new Position();
        ExtendedInfoFormatter extendedInfo = new ExtendedInfoFormatter("gv55");
        int i = 0;

        list.get(i++); // BUFF|ACK|RESP:GTSOS

        String protocol = getProtocolVersion(list.get(i++));

        String imei = list.get(i++); // Get device by IMEI
        try {
            position.setDeviceId(mgr.getDeviceByImei(imei).getId());
        } catch (Exception error) {
            Log.warning("Unknown device - " + imei);
            return null;
        }

        String deviceName = getDeviceName(list.get(i++));
        list.get(i++);
        String reportIdType = getReportIdType(list.get(i++));

        position.setValid(getNumber(list.get(i++)) == 1);

        Integer gpsAccuracy = getGpsAccuracy(list.get(i++));
        Double speed = getSpeed(list.get(i++));
        Integer heading = getHeading(list.get(i++));
        Double altitude = getAltitude(list.get(i++));
        Double longitude = getLongitude(list.get(i++));
        Double latitude = getLatitude(list.get(i++));
        Date gpsUtcTime = getGpsUtcTime(list.get(i++));
        String mcc = getMcc(list.get(i++));
        String mnc = getMnc(list.get(i++));
        String lac = getLac(list.get(i++));
        String cellId = getCellId(list.get(i++));
        list.get(i++); // Reserved2
        Double mileage = getMileage(list.get(i++));
        Date sendTime = getSendTime(list.get(i++));
        Integer countNumber = getCountNumber(list.get(i));

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

