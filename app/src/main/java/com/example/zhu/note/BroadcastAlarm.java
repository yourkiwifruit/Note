package com.example.zhu.note;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import org.litepal.crud.DataSupport;

/**
 * Created by zhu on 2018/8/20.
 */

public class BroadcastAlarm extends BroadcastReceiver{

    private int alarmId;

    @Override
    public void onReceive(Context context, Intent intent) {
        //showMemo(context);
        Log.d("闹钟响了","响了");
        alarmId=intent.getIntExtra("alarmId",0);
        showNotice(context);
    }

    private void showNotice(Context context) {
        int num=alarmId;
        Log.d("Note_Home","alarmNoticeId "+num);

        Intent intent=new Intent(context,Note_Taskdetail.class);

        Note_table note_table= DataSupport.find(Note_table.class,num);
        deleteTheAlarm(num);//or num
        transportInformationToEdit(intent,note_table);
        PendingIntent pi=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(context)
                .setContentTitle("您的备忘任务开始了～")
                .setContentText(note_table.getContent())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .setAutoCancel(true)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(record.getMainText()))
                .setLights(Color.GREEN,1000,1000)
                .build();
        manager.notify(num,notification);
    }

    private void deleteTheAlarm(int num) {
        ContentValues temp = new ContentValues();
        temp.put("alarm_key", "");
        String where = String.valueOf(num);
        DataSupport.updateAll(Note_table.class, temp, "id = ?", where);
    }

    private void transportInformationToEdit(Intent it,Note_table note_table) {

        it.putExtra("content",note_table.getContent());
        it.putExtra("color_key",note_table.getColor_key());
        it.putExtra("time",note_table.getTime());

    }

}
