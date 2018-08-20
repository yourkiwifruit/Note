package com.example.zhu.note;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhu on 2018/8/17.
 */

public class Note_Adapter extends BaseAdapter {

    public Context getMcontext() {
        return mcontext;
    }

    public void setMcontext(Context mcontext) {
        this.mcontext = mcontext;
    }

    public ArrayList<Note_table> getList() {
        return list;
    }

    public void setList(ArrayList<Note_table> list) {
        this.list = list;
    }

    private Context mcontext;
    private ArrayList<Note_table> list;


    public Note_Adapter(Context mcontext,ArrayList<Note_table> list){
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

        convertView = LayoutInflater.from(mcontext).inflate(R.layout.list_item,parent,false);
        Log.d("这里是Note_adapter",String.valueOf(list.size()));

        CardView cardView = (CardView) convertView.findViewById(R.id.card);
        ImageView naozhong_icon = (ImageView) convertView.findViewById(R.id.naozhong_icon);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView time = (TextView) convertView.findViewById(R.id.time);



        if(list.get(position).getColor_key()==0){
            cardView.setBackgroundResource(R.drawable.card_radius_black);

        }
        else if(list.get(position).getColor_key()==1){

            cardView.setBackgroundResource(R.drawable.card_radius_chengse);
        }
        else if(list.get(position).getColor_key()==2){
            cardView.setBackgroundResource(R.drawable.card_radius_hailu);
        }
        else if(list.get(position).getColor_key()==3){
            cardView.setBackgroundResource(R.drawable.card_radius_red);
        }
        else if(list.get(position).getColor_key()==4){
            cardView.setBackgroundResource(R.drawable.card_radius_qianzi);
        }
        else if(list.get(position).getColor_key()==5){
            cardView.setBackgroundResource(R.drawable.card_radius_shenglan);
        }
        else if(list.get(position).getColor_key()==6){
            cardView.setBackgroundResource(R.drawable.card_radius_luse);
        }

        if(list.get(position).getAlarm_key().isEmpty()){
            naozhong_icon.setVisibility(View.GONE);
        }
        else {
            naozhong_icon.setVisibility(View.VISIBLE);
        }

        Log.d("颜色",String.valueOf(list.get(position).getColor_key()));

        content.setText(list.get(position).getContent().toString());
        time.setText(list.get(position).getTime().toString());

        return convertView;
    }
}
