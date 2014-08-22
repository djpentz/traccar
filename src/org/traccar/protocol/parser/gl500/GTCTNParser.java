package org.traccar.protocol.parser.gl500;

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
 * GTCTN parser for GL500 device.
 *
 * @author djpentz
 * @since 2014/07/17 4:58 PM
 */
public class GTCTNParser extends QueclinkParser {

    @Override
    public String getCommand() {
        return QueclinkMessages.GTCTN;
    }

    @Override
    public Integer getType() {
        return MessageType.POSITION.toValue();
    }

    /**
     * Example GTCTN for reference
     * +RESP:GTCTN,110106,868487002012345,GL500,0,0,2,15.8,65,3,0.8,0,0.6,20.877020,-44.068223,20140718210812,0655,0001,00DD,6723,,,,20140718211028,24A5
     *
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
        ExtendedInfoFormatter extendedInfo = new ExtendedInfoFormatter("gl500");
        int i = 0;

        list.get(i++); // BUFF|ACK|RESP:GTTRI
        list.get(i++); // protocol version

        String imei = list.get(i++); // Get device by IMEI
        try {
            position.setDeviceId(mgr.getDeviceByImei(imei).getId());
        } catch (Exception error) {
            Log.warning("Unknown device - " + imei);
            return null;
        }

        String deviceName = getDeviceName(list.get(i++));
        String reportId = getReportId(list.get(i++));
        String reportType = getReportType(list.get(i++));
        String movementStatus = getMovementStatus(list.get(i++));
        Double temp = getTemperature(list.get(i++));
        Integer batteryPerc = getBatteryPerc(list.get(i++));
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
        list.get(i++); // Reserved
        list.get(i++); // Reserved
        list.get(i++); // Reserved
        Date sendTime = getSendTime(list.get(i++));
        Integer countNumber = getCountNumber(list.get(i));

        position.setBatteryPerc(batteryPerc);
        position.setGpsAccuracy(gpsAccuracy);
        position.setAltitude(altitude);
        position.setCourse(heading.doubleValue());
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setTime(sendTime);
        position.setSpeed(speed);
        position.setServerTime(new Date());
        if (temp != null) {
            position.setTemperature(temp);
        }

        // Cell information etc
        setConstantData(position, mcc, mnc, lac, cellId);
        position.setExtendedInfo(extendedInfo.toString());

        return position;
    }
}