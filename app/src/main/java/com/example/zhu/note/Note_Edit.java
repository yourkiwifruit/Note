package com.example.zhu.note;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class Note_Edit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.icon_fanhui)ImageView icon_fanhui;
    @BindView(R.id.naozhong_icon)ImageView naozhong_icon;
    @BindView(R.id.edit_content)EditText edit_content;
    @BindView(R.id.radioGroup)RadioGroup radioGroup;
    @BindView(R.id.fab)FloatingActionButton fab;
    @BindView(R.id.Rb_black)RadioButton Rb_black;
    @BindView(R.id.Rb_chengse)RadioButton Rb_chengse;
    @BindView(R.id.Rb_hailu)RadioButton Rb_hailu;
    @BindView(R.id.Rb_hongse)RadioButton Rb_hongse;
    @BindView(R.id.Rb_luse)RadioButton Rb_luse;
    @BindView(R.id.Rb_qianzi)RadioButton Rb_qianzi;
    @BindView(R.id.Rb_shenglan)RadioButton Rb_shenglan;
    Context mcontext;

    int color_key;
    String alarm_date = "";
    int id;
    //判断是否是重新编辑还是再次编辑的标志
    int flg = 0;

    //定时变量
    int alarm_hour;
    int alarm_minute;
    int alarm_year;
    int alarm_month;
    int alarm_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mcontext = this;
        ButterKnife.bind(this);


        //主界面传递的一条备忘记录对象
        Note_Info note_info = (Note_Info)getIntent().getSerializableExtra("note_table_data");
        if(!(note_info == null)){
            revise_content(note_info);
            flg = 1;
        }

        //编辑界面的颜色选定
        color_selection();


    }
    //修改备忘时，调用此方法
    private void revise_content( Note_Info note_info){
        Log.d("序列化对象",note_info.getContent().toString());
        Log.d("序列化对象",String.valueOf(note_info.getColor_key()));
        Log.d("序列化对象",String.valueOf(note_info.getId()));
        Log.d("序列化对象",note_info.getAlarm_key());

        id = note_info.getId();
        edit_content.setText(note_info.getContent().toString());
        color_key = note_info.getColor_key();
        alarm_date = note_info.getAlarm_key();

        beijing_shezhi(color_key);

    }
    //设置背景
    void beijing_shezhi(int color_key){
        switch (color_key){
            case 0:
                edit_content.setBackgroundResource(R.color.black);
                Rb_black.setChecked(true);
                //Toast.makeText(mcontext,"黑色",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                edit_content.setBackgroundResource(R.color.chengse);
                Rb_chengse.setChecked(true);
                //Toast.makeText(mcontext,"chengse",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Rb_hailu.setChecked(true);
                edit_content.setBackgroundResource(R.color.hailu);
                //Toast.makeText(mcontext,"hailu",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Rb_hongse.setChecked(true);
                edit_content.setBackgroundResource(R.color.hongse);
                //Toast.makeText(mcontext,"hongse",Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Rb_qianzi.setChecked(true);
                edit_content.setBackgroundResource(R.color.qianzi);
                //Toast.makeText(mcontext,"qianzi",Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Rb_shenglan.setChecked(true);
                edit_content.setBackgroundResource(R.color.shenglan);
                //Toast.makeText(mcontext,"shenglan",Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Rb_luse.setChecked(true);
                edit_content.setBackgroundResource(R.color.luse);
               // Toast.makeText(mcontext,"luse",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    //返回图标，返回后是否自动保存？
    @OnClick(R.id.icon_fanhui)
    public void icon_fanhui(View view){
        finish();

    }

    //点击图标，进行定时操作
    @OnClick(R.id.naozhong_icon)
    public void naozhong_icon(View view){

        if(alarm_date.length()<=1) {
            //if no alarm clock has been set up before
            //show the current time
            Calendar c=Calendar.getInstance();
            alarm_hour=c.get(Calendar.HOUR_OF_DAY);
            alarm_minute=c.get(Calendar.MINUTE);

            alarm_year=c.get(Calendar.YEAR);
            alarm_month=c.get(Calendar.MONTH)+1;
            alarm_day=c.get(Calendar.DAY_OF_MONTH);
            Log.d("月份",String.valueOf(alarm_month));
        }
        else {
            //show the alarm clock time which has been set up before
            int i=0, k=0;
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
        }

        new TimePickerDialog(this,this,alarm_hour,alarm_minute,true).show();
        new DatePickerDialog(this,this,alarm_year,alarm_month-1,alarm_day).show();




    }

    //保存点击事件
    @OnClick(R.id.fab)
    public void fab(View view){
        if(flg == 0) {
            String content = edit_content.getText().toString();
            Note_table note_table = new Note_table();

            note_table.setContent(content);
            note_table.setTime(Nowtime.Now());
            note_table.setColor_key(color_key);
            note_table.setAlarm_key(alarm_date);

            note_table.save();
            id = note_table.getId();
        }
        if(flg == 1){
            Note_table note_table = DataSupport.find(Note_table.class,id);
            note_table.setContent(edit_content.getText().toString());
            note_table.setColor_key(color_key);
            note_table.setAlarm_key(alarm_date);
            note_table.setTime(Nowtime.Now());

            note_table.save();
        }
        Log.d("ID值",""+id);
        Intent intent = new Intent(mcontext,Note_Home.class);
        intent.putExtra("Id",id);
        intent.putExtra("alarm_date",alarm_date);
        setResult(2,intent);
        finish();
    }

    //编辑界面的颜色选定
    private void color_selection(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               switch (checkedId){
                   case R.id.Rb_black:
                       edit_content.setBackgroundResource(R.color.black);
                       //Toast.makeText(mcontext,"black",Toast.LENGTH_SHORT).show();
                       color_key = 0;
                       break;
                   case R.id.Rb_chengse:
                       edit_content.setBackgroundResource(R.color.chengse);
                       //Toast.makeText(mcontext,"chengse",Toast.LENGTH_SHORT).show();
                       color_key = 1;
                       break;
                   case R.id.Rb_hailu:
                       edit_content.setBackgroundResource(R.color.hailu);
                       //Toast.makeText(mcontext,"hailu",Toast.LENGTH_SHORT).show();
                       color_key = 2;
                       break;
                   case R.id.Rb_hongse:
                       edit_content.setBackgroundResource(R.color.hongse);
                       //Toast.makeText(mcontext,"hongse",Toast.LENGTH_SHORT).show();
                       color_key = 3;
                       break;
                   case R.id.Rb_qianzi:
                       edit_content.setBackgroundResource(R.color.qianzi);
                       //Toast.makeText(mcontext,"qianzi",Toast.LENGTH_SHORT).show();
                       color_key = 4;
                       break;
                   case R.id.Rb_shenglan:
                       edit_content.setBackgroundResource(R.color.shenglan);
                       //Toast.makeText(mcontext,"shenglan",Toast.LENGTH_SHORT).show();
                       color_key = 5;
                       break;
                   case R.id.Rb_luse:
                       edit_content.setBackgroundResource(R.color.luse);
                       //Toast.makeText(mcontext,"luse",Toast.LENGTH_SHORT).show();
                       color_key = 6;
                       break;
                   default:
                       break;
               }
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        alarm_year=year;
        alarm_month=monthOfYear+1;
        alarm_day=dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        alarm_hour=hourOfDay;
        alarm_minute=minute;

        alarm_date=alarm_year+"/"+alarm_month+"/"+alarm_day+" "+alarm_hour+":"+alarm_minute;
//        av.setText("Alert at "+alarm+"!");
//        av.setVisibility(View.VISIBLE);
//        Toast.makeText(this,"",Toast.LENGTH_LONG).show();
    }
}
