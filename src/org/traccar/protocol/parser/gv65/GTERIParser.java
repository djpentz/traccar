package org.traccar.protocol.parser.gv65;

import java.util.regex.Pattern;

import static org.traccar.protocol.patterns.QueclinkPatterns.*;

/**
 * @author djpentz
 * @since 2014/05/07 2:09 PM
 */
public class GTERIParser {

    private static final Pattern GTERI = Pattern.compile(
            ResponseHeader +
                    ProtocolVersion +
                    UniqueId +
                    DeviceName +
                    EriMask +
                    ExtPowerSupply +
                    ReportIdType +
                    Number +
                    GpsAccuracy +
                    Speed +
                    Azimuth +
                    Altitude +
                    Longitude +
                    Latitude +
                    GpsUtcTime +
                    Mcc +
                    Mnc +
                    Lac +
                    CellId +
                    Reserved2 +
                    Mileage +
                    HourMeterCount +
                    AnalogInput +
                    Reserved0 +
                    DeviceStatus +
                    UartDeviceType +
                    OneWireDeviceNumber +
                    OneWireDeviceId +
                    OneWireDeviceType +
                    OneWireDeviceData +
                    SendTime +
                    CountNumber +
                    TailCharacter
    );

}
