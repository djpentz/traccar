package org.traccar.protocol.parser;

import org.joda.time.Duration;
import org.traccar.model.DataManager;
import org.traccar.model.Position;
import org.traccar.protocol.patterns.QueclinkPatterns;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base parser implementation that contains numerous convenience parsing methods
 * common to many Queclink devices.
 *
 * @author djpentz
 * @since 2014/05/07 10:52 PM
 */
public abstract class QueclinkParser implements MessageParser {

    protected Pattern pattern;
    protected Pattern fullDatePattern = Pattern.compile(QueclinkPatterns.SendTime);
    protected Pattern hourCountPattern = Pattern.compile(QueclinkPatterns.HourMeterCount);

    public QueclinkParser() {

    }

    public QueclinkParser(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public abstract Object parse(DataManager mgr, Object msg) throws Exception;

    @Override
    public abstract String getCommand();

    @Override
    public abstract Integer getType();

    protected String getProtocolVersion(String part) {
        return part;
    }

    protected String getUniqueId(String part) {
        return part;
    }

    protected String getDeviceName(String part) {
        return part;
    }

    protected String getEriMask(String part) {
        return part;
    }

    protected Integer getExtPowerSupply(String part) {
        try {
            return Integer.parseInt(part);
        } catch (Exception e) {
            return -1;
        }
    }

    protected String getReportIdType(String part) {
        return part;
    }

    protected String getReportId(String part) {
        return part;
    }

    protected String getReportType(String part) {
        return part;
    }

    protected String getMovementStatus(String part) {
        return part;
    }

    protected Double getTemperature(String part) {
        try {
            return Double.parseDouble(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Integer getNumber(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected Integer getGpsAccuracy(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected Double getSpeed(String part) {
        try {
            return Double.parseDouble(part);
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    protected Integer getHeading(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected Double getAltitude(String part) {
        try {
            return Double.parseDouble(part);
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    protected Double getLongitude(String part) {
        try {
            return Double.parseDouble(part);
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    protected Double getLatitude(String part) {
        try {
            return Double.parseDouble(part);
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    protected Date getGpsUtcTime(String yyyymmddhhMMss) {
        if (yyyymmddhhMMss == null) return null;

        Matcher matcher = fullDatePattern.matcher(yyyymmddhhMMss);
        if (!matcher.matches()) {
            return null;
        }

        int i = 1;

        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        time.clear();
        time.set(Calendar.YEAR, Integer.valueOf(matcher.group(i++)));
        time.set(Calendar.MONTH, Integer.valueOf(matcher.group(i++)) - 1);
        time.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(i++)));

        // Time
        time.set(Calendar.HOUR_OF_DAY, Integer.valueOf(matcher.group(i++)));
        time.set(Calendar.MINUTE, Integer.valueOf(matcher.group(i++)));
        time.set(Calendar.SECOND, Integer.valueOf(matcher.group(i++)));
        return time.getTime();
    }

    protected String getMcc(String part) {
        return part;
    }

    protected String getMnc(String part) {
        return part;
    }

    protected String getLac(String part) {
        return part;
    }

    protected String getCellId(String part) {
        return part;
    }

    /**
     * Note that this is actually expressed in kilometres
     *
     * @return A km value
     */
    protected Double getMileage(String part) {
        try {
            return Double.parseDouble(part);
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    protected Duration getHourMeterCount(String hhhhhmmss) {
        if (hhhhhmmss == null) return null;

        Matcher matcher = hourCountPattern.matcher(hhhhhmmss);
        if (!matcher.matches()) {
            return null;
        }

        int i = 1;
        long total = 0;
        try {
            int hours = Integer.parseInt(matcher.group(i++));
            int minutes = Integer.parseInt(matcher.group(i++));
            int seconds = Integer.parseInt(matcher.group(i));
            total += (hours * 60 * 60 * 1000); // to ms
            total += (minutes * 60 * 1000);
            total += (seconds * 1000);
        } catch (Exception e) {
            return null;
        }

        return new Duration(total);
    }

    protected Integer getAnalogInputVcc(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Integer getAnalogInput1(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Integer getAnalogInput2(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Integer getBatteryPerc(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Integer getBackupBatteryPerc(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected String getDeviceStatus(String part) {
        return part;
    }

    protected Integer getUartDeviceType(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected String getDigitFuelSensorData(String part) {
        return part;
    }

    protected Integer getOneWireDeviceNumber(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected String getOneWireDeviceId(String part) {
        return part;
    }

    protected String getOneWireDeviceType(String part) {
        return part;
    }

    protected String getOneWireDeviceData(String part) {
        return part;
    }

    protected Date getSendTime(String yyyymmddhhMMss) {
        if (yyyymmddhhMMss == null) return null;

        Matcher matcher = fullDatePattern.matcher(yyyymmddhhMMss);
        if (!matcher.matches()) {
            return null;
        }
        int i = 1;

        // Date
        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        time.clear();
        time.set(Calendar.YEAR, Integer.valueOf(matcher.group(i++)));
        time.set(Calendar.MONTH, Integer.valueOf(matcher.group(i++)) - 1);
        time.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(i++)));

        // Time
        time.set(Calendar.HOUR_OF_DAY, Integer.valueOf(matcher.group(i++)));
        time.set(Calendar.MINUTE, Integer.valueOf(matcher.group(i++)));
        time.set(Calendar.SECOND, Integer.valueOf(matcher.group(i)));
        return time.getTime();
    }

    protected Integer getCountNumber(String part) {
        return Integer.valueOf(part, 16);
    }

    protected Double convertHexTemperature(String oneWireDeviceData) {
        if (oneWireDeviceData == null) return null;

        short temp = Integer.valueOf(oneWireDeviceData, 16).shortValue();
        return (temp * 0.0625d);
    }

    /**
     * This is data that is consistently in the Queclink device data, so we just
     * set it all here for convenience.
     * @param position The position
     * @param mcc The MCC
     * @param mnc The MNC
     * @param lac The LAC
     * @param cellId The cell ID
     */
    protected void setConstantData(Position position, String mcc, String mnc, String lac, String cellId) {
        position.setMcc(mcc);
        position.setMnc(mnc);
        position.setLac(lac);
        position.setCell(cellId);
    }
}
