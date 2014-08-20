/*
 * Copyright 2012 - 2013 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar.model;

import java.util.Date;

/**
 * Position information
 */
public class Position extends Data {


    /**
     * The command from the device, e.g. GTTRI, GTSOS etc
     */
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * The type of the command, e.g. position, panic, movement, etc - expressed by an enum
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Time (UTC)
     */
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Validity flag
     */
    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * Latitude
     */
    private Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Longitude
     */
    private Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Altitude
     */
    private Double altitude;

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    /**
     * Speed (knots)
     */
    private Double speed;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * Course
     */
    private Double course;

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }

    /**
     * Address
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Temperature
     */
    private Double temperature;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    private Double temp1;

    public Double getTemp1() { return temp1; }

    public void setTemp1(Double temp1) { this.temp1 = temp1; }

    private Double temp2;

    public Double getTemp2() { return temp2; }

    public void setTemp2(Double temp2) { this.temp2 = temp2; }

    private Double temp3;

    public Double getTemp3() { return temp3; }

    public void setTemp3(Double temp3) { this.temp3 = temp3; }

    private Double temp4;

    public Double getTemp4() { return temp4; }

    public void setTemp4(Double temp4) { this.temp4 = temp4; }

    private String mcc;

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    private String mnc;

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    private String lac;

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    private String cell;

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    private Integer batteryPerc;

    public Integer getBatteryPerc() {
        return batteryPerc;
    }

    public void setBatteryPerc(Integer batteryPerc) {
        this.batteryPerc = batteryPerc;
    }

}
