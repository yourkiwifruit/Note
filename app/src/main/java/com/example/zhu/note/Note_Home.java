package com.example.zhu.note;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Note_Home extends AppCompatActivity {

    @BindView(R.id.list)ListView listView;
    @BindView(R.id.icon)ImageView icon;
    @BindView(R.id.fab)FloatingActionButton fab;


    private Context mcontext;
    Note_Adapter note_adapter;
    List<Note_table> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcontext = this;
        ButterKnife.bind(this);
        listview_click();  //listview点击事件
        listview_longPress(); //listview长按事件
    }
    //添加新备忘
    @OnClick(R.id.fab)
    public void fab(View view){
        Intent intent = new Intent(mcontext,Note_Edit.class);
        startActivityForResult(intent,1);
    }
    //天气
    @OnClick(R.id.icon)
    public void icon(View view){
        Intent intent = new Intent(mcontext,Weather_Home.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        Data_loading();
        Log.d("这里是ONSTART",String.valueOf(list.size()));
        note_adapter = new Note_Adapter(mcontext,(ArrayList<Note_table>)list);
        listView.setAdapter(note_adapter);
    }


    //从数据库中加载数据
    private void Data_loading(){
        list = DataSupport.findAll(Note_table.class);
    }

    //listview点击事件
    private void listview_click(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Note_Info note_info =  new Note_Info();

                note_info.setId(list.get(position).getId());
                note_info.setColor_key(list.get(position).getColor_key());
                note_info.setContent(list.get(position).getContent());
                note_info.setAlarm_key(list.get(position).getAlarm_key());
                note_info.setTime(list.get(position).getTime());

                Intent intent = new Intent(mcontext,Note_Edit.class);
                intent.putExtra("note_table_data", note_info);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2) {
            if (requestCode == 1) {

                //定时变量
                int alarm_hour;
                int alarm_minute;
                int alarm_year;
                int alarm_month;
                int alarm_day;
                int i=0, k=0;


                String alarm_date = data.getStringExtra("alarm_date");
                if(alarm_date.length()>1){
                    int id = data.getIntExtra("Id",0);
                    while(i<alarm_date.length()&&alarm_date.charAt(i)!='/') i++;
                    alarm_year=Integer.parseInt(alarm_date.substring(k,i));
                    k=i+1;i++;
                    while(i<alarm_date.length()&&alarm_date.charAt(i)!='/') i++;
                    alarm_month=Integer.parseInt(alarm_date.substring(k,i));
                    k=i+1;i++;
                    while(i<alarm_date.length()&&alarm_date.charAt(i)!=' ') i++;
                    alarm_day=Integer.parseInt(alarm_date.substring(k,i));
                    k=i+1;i++;
                    while(i<alarm_date.length()&&alarm_date.charAt(i)!=':') i++;
                    alarm_hour=Integer.parseInt(alarm_date.substring(k,i));
                    k=i+1;i++;
                    alarm_minute=Integer.parseInt(alarm_date.substring(k));


                    //从数据库中拿到alarm_date数据进行定时操作
                    Intent intent = new Intent(mcontext, BroadcastAlarm.class);
                    intent.putExtra("alarmId",id);
                    PendingIntent sender = PendingIntent.getBroadcast(mcontext,id, intent, 0);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    //calendar.add(Calendar.SECOND, 5);

                    Calendar alarm_time = Calendar.getInstance();
                    alarm_time.set(alarm_year,alarm_month-1,alarm_day,alarm_hour,alarm_minute);

                    // Schedule the alarm!
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //if(interval==0)
                    am.set(AlarmManager.RTC_WAKEUP, alarm_time.getTimeInMillis(), sender);
                }
            }
        }
    }

    //listview长按事件
    private void listview_longPress(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog alert = null;
                AlertDialog.Builder builder  = new AlertDialog.Builder(mcontext);

                alert = builder.setIcon(R.drawable.jinggao)
                        .setTitle("系统提示：")
                        .setMessage("您想要删除此条备忘？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mcontext,"取消删除～",Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    cancelAlarm(position);
                                    Note_table note_table = DataSupport.find(Note_table.class,list.get(position).getId());
                                    note_table.delete();
                                    Data_loading();
                                    note_adapter = new Note_Adapter(mcontext,(ArrayList<Note_table>)list);
                                    listView.setAdapter(note_adapter);
                                    Toast.makeText(mcontext,"删除成功～",Toast.LENGTH_SHORT).show();

                                }
                            }).create();

                alert.show();

                return true;
            }
        });
    }

    //cancel the alarm
    private void cancelAlarm(int num) {


        Intent intent = new Intent(mcontext,BroadcastAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(mcontext,list.get(num).getId(),intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        Log.d("闹钟取消","取消取消");
    }
}
