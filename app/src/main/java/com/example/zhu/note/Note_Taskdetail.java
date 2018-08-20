package com.example.zhu.note;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Note_Taskdetail extends AppCompatActivity {

    @BindView(R.id.content)TextView edit_content;
    @BindView(R.id.time)TextView edit_time;
    @BindView(R.id.card)CardView card;
    @BindView(R.id.icon_fanhui)ImageView icon_fanhui;
    Context mcontext;
    int color_key;
    String content;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taskdetail);
        mcontext = this;
        ButterKnife.bind(this);

        content = getIntent().getStringExtra("content");
        time = getIntent().getStringExtra("time");
        color_key = getIntent().getIntExtra("color_key",0);

        edit_content.setText(content);
        edit_time.setText(time);
        if(color_key==0){
            card.setBackgroundResource(R.drawable.card_radius_black);

        }
        else if(color_key==1){

            card.setBackgroundResource(R.drawable.card_radius_chengse);
        }
        else if(color_key==2){
            card.setBackgroundResource(R.drawable.card_radius_hailu);
        }
        else if(color_key==3){
            card.setBackgroundResource(R.drawable.card_radius_red);
        }
        else if(color_key==4){
            card.setBackgroundResource(R.drawable.card_radius_qianzi);
        }
        else if(color_key==5){
            card.setBackgroundResource(R.drawable.card_radius_shenglan);
        }
        else if(color_key==6){
            card.setBackgroundResource(R.drawable.card_radius_luse);
        }
    }


    @OnClick(R.id.icon_fanhui)
    public void icon_fanhui(View view){
        Intent intent = new Intent(this,Note_Home.class);
        startActivity(intent);
    }
}
