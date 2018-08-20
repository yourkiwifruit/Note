package com.example.zhu.note;

import java.io.Serializable;

/**
 * Created by zhu on 2018/8/19.
 */

public class Note_Info implements Serializable {
    private int id;
    private String content;
    private String time;
    private int color_key;
    public String getAlarm_key() {
        return alarm_key;
    }

    public void setAlarm_key(String alarm_key) {
        this.alarm_key = alarm_key;
    }

    private String alarm_key;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getColor_key() {
        return color_key;
    }

    public void setColor_key(int color_key) {
        this.color_key = color_key;
    }
}
