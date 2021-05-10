package com.example.adminapp.productgetset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTime {

    @SerializedName("ticks")
    @Expose
    private Object ticks;
    @SerializedName("days")
    @Expose
    private Integer days;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("milliseconds")
    @Expose
    private Integer milliseconds;
    @SerializedName("minutes")
    @Expose
    private Integer minutes;
    @SerializedName("seconds")
    @Expose
    private Integer seconds;
    @SerializedName("totalDays")
    @Expose
    private Double totalDays;
    @SerializedName("totalHours")
    @Expose
    private Double totalHours;
    @SerializedName("totalMilliseconds")
    @Expose
    private Double totalMilliseconds;
    @SerializedName("totalMinutes")
    @Expose
    private Double totalMinutes;
    @SerializedName("totalSeconds")
    @Expose
    private Double totalSeconds;

    public Object getTicks() {
        return ticks;
    }

    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Integer milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Double totalDays) {
        this.totalDays = totalDays;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public Double getTotalMilliseconds() {
        return totalMilliseconds;
    }

    public void setTotalMilliseconds(Double totalMilliseconds) {
        this.totalMilliseconds = totalMilliseconds;
    }

    public Double getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Double totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Double getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(Double totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

}