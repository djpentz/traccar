package org.traccar.protocol.parser.gv320;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Decodes the GTERI response for the GV320 device.
 *
 * @author djpentz
 * @since 2014/05/07 2:11 PM
 */
public class GTERIParser extends QueclinkParser {

    @Override
    public String getCommand() {
        return QueclinkMessages.GTERI;
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

        list.get(i++); // BUFF|ACK|RESP:GTERI
        list.get(i++); // protocol version

        String imei = list.get(i++); // Get device by IMEI
        try {
            position.setDeviceId(mgr.getDeviceByImei(imei).getId());
        } catch (Exception error) {
            Log.warning("Unknown device - " + imei);
            return null;
        }
        String deviceName = getDeviceName(list.get(i++));
        String eriMaskStr = getEriMask(list.get(i++));
        int eriMask = Integer.parseInt(eriMaskStr);
        boolean expectFuelData = (eriMask & 0x1) == 0x1;
        boolean expect1WireData = (eriMask & 0x2) == 0x2;
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
        Integer analogInput1 = getAnalogInput1(list.get(i++));
        Integer analogInput2 = getAnalogInput2(list.get(i++));
        Integer backupBatteryPerc = getBackupBatteryPerc(list.get(i++));
        String deviceStatus = getDeviceStatus(list.get(i++));
        Integer uartDeviceType = getUartDeviceType(list.get(i++));
        String digitFuelSensorData = null;
        if (expectFuelData) {
            digitFuelSensorData = getDigitFuelSensorData(list.get(i++));
        }
        // The GV320 can have up to 4 devices attached to it - at this time we only expect
        // to get temperature data.
        List<Double> tempList = new ArrayList<Double>();
        String oneWireDeviceId = null;
        String oneWireDeviceType = null;
        String oneWireDeviceData = null;
        if (expect1WireData) {
            Integer oneWireDeviceNumber = getOneWireDeviceNumber(list.get(i++));
            for (int num = 0; num < oneWireDeviceNumber; num++) {
                oneWireDeviceId = getOneWireDeviceId(list.get(i++));
                oneWireDeviceType = getOneWireDeviceType(list.get(i++));
                oneWireDeviceData = getOneWireDeviceData(list.get(i++));
                tempList.add(convertHexTemperature(oneWireDeviceData));
            }
        }
        Date sendTime = getSendTime(list.get(i++));
        Integer countNumber = getCountNumber(list.get(i));

        position.setAltitude(altitude);
        position.setCourse(heading.doubleValue());
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setTime(sendTime);
        position.setSpeed(speed);
        if (oneWireDeviceData != null && !oneWireDeviceData.trim().equals("")) {
            position.setTemperature(convertHexTemperature(oneWireDeviceData));
        }
        int count = 0;
        // Set the various temperatures depending on how many we read from the message
        for (Double temp : tempList) {
            switch (count) {
                case 0:
                    position.setTemp1(temp);
                    break;
                case 1:
                    position.setTemp2(temp);
                    break;
                case 2:
                    position.setTemp3(temp);
                    break;
                case 3:
                    position.setTemp4(temp);
                    break;
            }
            count++;
        }
        position.setServerTime(new Date());

        // Cell information etc
        setConstantData(position, mcc, mnc, lac, cellId);

        // Battery and GPS accuracy
        position.setGpsAccuracy(gpsAccuracy);
        position.setBatteryPerc(backupBatteryPerc);

        position.setExtendedInfo(extendedInfo.toString());

        return position;
    }

}