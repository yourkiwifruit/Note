package com.example.zhu.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by zhu on 2018/8/18.
 */

public class Weather_adapter extends BaseAdapter {

    public Context getMcontext() {
        return mcontext;
    }

    public void setMcontext(Context mcontext) {
        this.mcontext = mcontext;
    }

    public List<Weather_Info> getList() {
        return list;
    }

    public void setList(ArrayList<Weather_Info> list) {
        this.list = list;
    }

    private Context mcontext;
    private ArrayList<Weather_Info> list;

    public Weather_adapter(Context mcontext,ArrayList<Weather_Info> list){
        this.mcontext = mcontext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mcontext).inflate(R.layout.wea_list_item,parent,false);

        ImageView Wea_img = (ImageView) convertView.findViewById(R.id.img);
        TextView Wea_state = (TextView) convertView.findViewById(R.id.state);
        TextView Wea_temperature = (TextView) convertView.findViewById(R.id.temperature);
        TextView Wea_week = (TextView) convertView.findViewById(R.id.week);


        if("大雨".equals(list.get(position).getWea_state())){
            Wea_img.setImageResource(R.drawable.dayu);
        }
        else if("中雨".equals(list.get(position).getWea_state())){
            Wea_img.setImageResource(R.drawable.zhongyu);
        }
        else if("小雨".equals(list.get(position).getWea_state())){
            Wea_img.setImageResource(R.drawable.xiaoyu);
        }
        else if("阵雨".equals(list.get(position).getWea_state())){
            Wea_img.setImageResource(R.drawable.zhenyu);
        }
        else if("晴".equals(list.get(position).getWea_state())){
            Wea_img.setImageResource(R.drawable.qing);
        }
        else if("多云".equals(list.get(position).getWea_state())){
            Wea_img.setImageResource(R.drawable.duoyun);
        }
        else{
            Wea_img.setImageResource(R.drawable.zhenyu);
        }

        Wea_state.setText(list.get(position).getWea_state());
        Wea_temperature.setText(list.get(position).getWea_temperature());
        Wea_week.setText(list.get(position).getWea_week());

        return convertView;
    }
}
