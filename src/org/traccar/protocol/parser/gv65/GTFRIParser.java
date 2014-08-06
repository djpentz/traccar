package org.traccar.protocol.parser.gv65;

import org.joda.time.Duration;
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
 * @author djpentz
 * @since 2014/07/04 2:35 PM
 */
public class GTFRIParser extends QueclinkParser {

    @Override
    public String getCommand() {
        return QueclinkMessages.GTFRI;
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
        ExtendedInfoFormatter extendedInfo = new ExtendedInfoFormatter("gv55");
        int i = 0;

        list.get(i++); // BUFF|ACK|RESP:GTFRI
        list.get(i++); // protocol version

        String imei = list.get(i++); // Get device by IMEI
        try {
            position.setDeviceId(mgr.getDeviceByImei(imei).getId());
        } catch (Exception error) {
            Log.warning("Unknown device - " + imei);
            return null;
        }

        String deviceName = getDeviceName(list.get(i++));
        Integer extPowerSupply = getExtPowerSupply(list.get(i++));
        String reportIdType = getReportIdType(list.get(i++));
        Integer number = getNumber(list.get(i++));
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
        Duration hourMeterCount = getHourMeterCount(list.get(i++));
        Integer analogInputVcc = getAnalogInputVcc(list.get(i++));
        list.get(i++); // Reserved
        list.get(i++); // Reserved
        String deviceStatus = getDeviceStatus(list.get(i++));
        list.get(i++); // Reserved
        list.get(i++); // Reserved
        list.get(i++); // Reserved
        Date sendTime = getSendTime(list.get(i++));
        Integer countNumber = getCountNumber(list.get(i));

        position.setAltitude(altitude);
        position.setCourse(heading.doubleValue());
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setTime(sendTime);
        position.setSpeed(speed);
        position.setServerTime(new Date());

        // Cell information etc
        setConstantData(position, mcc, mnc, lac, cellId);
        position.setExtendedInfo(extendedInfo.toString());

        return position;
    }

}