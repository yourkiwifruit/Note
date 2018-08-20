package com.example.zhu.note;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhu on 2018/8/18.
 */

public class Nowtime {
    public static String Now() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return (df.format(new Date()));// new Date()为获取当前系统时间
    }
}
