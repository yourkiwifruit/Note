package com.example.zhu.note;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Weather_Home extends AppCompatActivity {

    @BindView(R.id.icon_fanhui)//返回图标
    ImageView icon_fanhui;
    @BindView(R.id.Wea_img)//天气图片
    ImageView Wea_img;
    @BindView(R.id.Wea_state)//天气状态
    TextView Wea_state;
    @BindView(R.id.Wea_local)//城市
    TextView Wea_local;
    @BindView(R.id.Wea_temperature)//温度
    TextView Wea_temperature;
    @BindView(R.id.Wea_suggest)//建议
    TextView Wea_suggest;
    @BindView(R.id.Wea_turnover_time)//更新时间
    TextView Wea_turnover_time;
    @BindView(R.id.listview)//列表
    ListView listview;

    String cityName = "合肥";
    List<Weather_Info> list = new ArrayList<>();
    Context mcontext;
    Weather_Info wea_info = null;
    Weather_adapter weather_adapter = null;


    //发送消息到UI线程
    private Handler myHandler = new Handler()
    {

        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if(msg.what == 1)
            {
                updateDate((Weather_Info)msg.obj);
                weaIcon((Weather_Info)msg.obj);
                weather_adapter = new Weather_adapter(mcontext,(ArrayList<Weather_Info>)list);
                Log.d("List长度",list.toString());
                Log.d("List长度",String.valueOf(list.size()));
                listview.setAdapter(weather_adapter);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);

        ButterKnife.bind(this);
        mcontext = Weather_Home.this;
        SharedHelper sp = new SharedHelper(mcontext);
        cityName = sp.read();
        getWeatherDatafromNet(cityName);

    }
    //图标的点击事件
    @OnClick(R.id.icon)
    public void icon(View view){
        Intent intent = new Intent(mcontext,Wea_search.class);
        startActivityForResult(intent,1);

    }
    //返回图标点击事件
    @OnClick(R.id.icon_fanhui)
    public void icon_fanhui(View v){
        finish();
    }
    //从第二界面返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2){
            if(requestCode == 1){
                cityName = data.getStringExtra("city").toString();
                getWeatherDatafromNet(cityName);
            }

        }
    }
    //通过天气状态去匹配对应的天气图标
    private void weaIcon(Weather_Info wea_info){

        if("大雨".equals(wea_info.getWea_state())){
            Wea_img.setImageResource(R.drawable.dayu);
        }
        else if("中雨".equals(wea_info.getWea_state())){
            Wea_img.setImageResource(R.drawable.zhongyu);
        }
        else if("小雨".equals(wea_info.getWea_state())){
            Wea_img.setImageResource(R.drawable.xiaoyu);
        }
        else if("阵雨".equals(wea_info.getWea_state())){
            Wea_img.setImageResource(R.drawable.zhenyu);
        }
        else if("晴".equals(wea_info.getWea_state())){
            Wea_img.setImageResource(R.drawable.qing);
        }
        else if("多云".equals(wea_info.getWea_state())){
            Wea_img.setImageResource(R.drawable.duoyun);
        }
        else{
            Wea_img.setImageResource(R.drawable.zhenyu);
        }

    }
    //通过线程对天气请求得到json数据
    private void getWeatherDatafromNet(String cityName)
    {
        final String address = "http://v.juhe.cn/weather/index?format=2&cityname="+cityName+"&key=我这个是聚合API的接口，你可以自己申请一个，我的就不拿出来的，略略略";
        Log.d("Address:",address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while((str=reader.readLine())!=null)
                    {
                        sb.append(str);
                        Log.d("date from url",str);
                    }
                    String response = sb.toString();
                    Log.d("response",response);
                    wea_info = ParseJson(response);
                    if(wea_info != null){
                        Log.d("标志：","进入Message");
                        Message message = new Message();
                        message.what = 1;
                        message.obj = wea_info;
                        myHandler.sendMessage(message);
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //对json数据进行解析，保存到Weather_info
    private Weather_Info ParseJson(String json){
        try {
            wea_info = new Weather_Info();
            JSONObject object = new JSONObject(json);
            JSONObject object_result = object.getJSONObject("result");
            JSONObject object_today = object_result.getJSONObject("today");

            wea_info.setWea_local(object_today.getString("city"));
            wea_info.setWea_state(object_today.getString("weather"));
            wea_info.setWea_temperature(object_today.getString("temperature"));
            wea_info.setDressing_advice(object_today.getString("dressing_advice"));
            wea_info.setDate(Nowtime.Now());

            Log.d("城市：",object_today.getString("city").toString());
            Log.d("城市：",object_today.getString("weather").toString());


            JSONArray object_future = object_result.getJSONArray("future");

            for(int i=1;i<object_future.length();i++) {
                JSONObject obj = object_future.getJSONObject(i);

                Weather_Info weather_info = new Weather_Info();

                weather_info.setWea_state(obj.getString("weather"));
                weather_info.setWea_temperature(obj.getString("temperature"));
                weather_info.setWea_week(obj.getString("week"));

                list.add(weather_info);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
       return  wea_info;
    }
    //对界面的view进行修改
    private void updateDate(Weather_Info wea_info){
        Wea_local.setText(wea_info.getWea_local());
        Wea_state.setText(wea_info.getWea_state());
        Wea_temperature.setText(wea_info.getWea_temperature());
        Wea_suggest.setText("建议:"+wea_info.getDressing_advice());
        Wea_turnover_time.setText("更新时间:"+wea_info.getDate());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();

    }
}
