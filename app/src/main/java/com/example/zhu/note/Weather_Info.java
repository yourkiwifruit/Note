package com.example.zhu.note;

/**
 * Created by zhu on 2018/8/18.
 */

public class Weather_Info {

    private String Wea_state;
    private String Wea_week;
    private String Wea_temperature;
    private String Wea_local;
    private String temperature;
    private String dressing_advice;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWea_local() {
        return Wea_local;
    }

    public void setWea_local(String wea_local) {
        Wea_local = wea_local;
    }


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDressing_advice() {
        return dressing_advice;
    }

    public void setDressing_advice(String dressing_advice) {
        this.dressing_advice = dressing_advice;
    }

    public String getWea_temperature() {
        return Wea_temperature;
    }

    public void setWea_temperature(String wea_temperature) {
        Wea_temperature = wea_temperature;
    }

    public String getWea_state() {
        return Wea_state;
    }

    public void setWea_state(String wea_state) {
        Wea_state = wea_state;
    }

    public String getWea_week() {
        return Wea_week;
    }

    public void setWea_week(String wea_week) {
        Wea_week = wea_week;
    }



}
