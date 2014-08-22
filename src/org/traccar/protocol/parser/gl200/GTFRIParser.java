package org.traccar.protocol.parser.gl200;

import org.joda.time.Duration;
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
 * Parser for GTFRI for GL200.
 *
 * @author djpentz
 * @since 2014/08/05 10:11 PM
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

    /**
     * Sample message:
     * +RESP:GTFRI,02010B,135790246811220,,0,0,2,1,4.3,92,70.0,121.354335,31.222073,20090 214013254,0460,0000,18d8,6141,00,0,4.3,92,70.0,121.354335,31.222073,20090101000000,04 60,0000,18d8,6141,00,,20090214093254,11F0
     * @param mgr
     * @param msg
     * @return
     * @throws Exception
     */
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
        String reportId = getReportId(list.get(i++));
        String reportType = getReportType(list.get(i++));
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
        Double mileage = getMileage(list.get(i++));
        Integer backupPerc = getBackupBatteryPerc(list.get(i++));
        Date sendTime = getSendTime(list.get(i++));
        Integer countNumber = getCountNumber(list.get(i));

        position.setAltitude(altitude);
        position.setCourse(heading.doubleValue());
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setTime(sendTime);
        position.setSpeed(speed);
        position.setServerTime(new Date());
        position.setBatteryPerc(backupPerc);
        position.setGpsAccuracy(gpsAccuracy);

        // Cell information etc
        setConstantData(position, mcc, mnc, lac, cellId);
        position.setExtendedInfo(extendedInfo.toString());

        return position;
    }

}

