package org.traccar.protocol.patterns;

import java.util.regex.Pattern;

/**
 * This class captures all the Regex sub-patterns for Queclink devices. The idea is that these are
 * consistent across devices and can be used to build a regex pattern for a particular device
 * protocol. Note that all patterns are suffixed with a comma, which is simplify pattern
 * building and readability. As it is all Queclink messages are appended with the Count Number
 * which is the only pattern without a dedicated tailing comma.
 *
 * @author djpentz
 * @since 2014/05/07 10:34 AM
 */
public class QueclinkPatterns extends BasePatterns {

    public static final String ResponseHeader = "\\+(ACK|RESP|BUFF):(GT...),";
    public static final String MessageTypePattern = ResponseHeader + ".*";
    public static final String TailCharacter = "$";
    public static final String COMMA = ",";

    public static final String ProtocolVersion = "\\w{6}";
    public static final String UniqueId = "(\\d{15})";
    public static final String DeviceName = "(\\w{1,10})*";
    public static final String EriMask = "(\\p{XDigit}{8})";
    public static final String ExtPowerSupply = "(\\d{1,5})*";
    public static final String ReportIdType = "([1-4][0-3])";
    public static final String Number = "([0-9]|1[0-5])";
    public static final String GpsAccuracy = "([0-9]|[1-4][0-9]|50)";
    public static final String Speed = "(\\d{1,3}\\.\\d{1})";
    public static final String Heading = "(\\d{1,3})";
    public static final String Azimuth = "(\\d{1,3})";
    public static final String Altitude = "(-?\\d+\\.\\d)";
    public static final String Longitude = "(-?\\d+\\.\\d+)";
    public static final String Latitude = "(-?\\d+\\.\\d+)";
    public static final String GpsUtcTime = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
    public static final String Mcc = "(\\d{4})";
    public static final String Mnc = "(\\d{4})";
    public static final String Lac = "(\\p{XDigit}{4})";
    public static final String CellId = "(\\p{XDigit}{4})";
    public static final String Reserved0 = "(\\w)*"; // 0 characters
    public static final String Reserved2 = "(\\d+\\d+)"; // 2 characters
    public static final String Mileage = "(\\d+.\\d+)";
    public static final String HourMeterCount = "(?:(\\d{4}):(\\d{2}):(\\d{2}))*";
    public static final String AnalogInput = "(\\d{1,5})*";
    public static final String AnalogInput1 = "(\\d{1,5})*";
    public static final String AnalogInput2 = "(\\d{1,5})*";
    public static final String BatteryBackupPerc = "(\\d{1,3})";
    public static final String DeviceStatus = "(\\p{XDigit}{6})";
    public static final String UartDeviceType = "(\\d{1,2})";
    public static final String DigitFuelSensorData = "(\\w{1,20})";
    public static final String OneWireDeviceNumber = "(\\d{1,2})";
    public static final String OneWireDeviceId = "(\\p{XDigit}{16})";
    public static final String OneWireDeviceType = "(\\w{1,2})";
    public static final String OneWireDeviceData = "(\\w{1,40})";
    public static final String SendTime = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
    public static final String CountNumber = "(\\p{XDigit}{4})";

    public static Pattern getMessageHeaderPattern() {
        return messageHeaderPattern;
    }

}
