package com.example.zhu.note;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Wea_search extends AppCompatActivity {

    @BindView(R.id.icon_fanhui)ImageView icon_fanhui;
    @BindView(R.id.search_icon)ImageView search_icon;
    @BindView(R.id.search_text)EditText search_text;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wea_search);

        ButterKnife.bind(this);
        mcontext = this;
    }

    @OnClick(R.id.icon_fanhui)
    public void icon_fanhui(View view){
        finish();
    }


    @OnClick(R.id.search_icon)
    public void search_icon(View view){
        SharedHelper sp = new SharedHelper(mcontext);
        sp.save(search_text.getText().toString());
        Intent intent = new Intent(mcontext,Weather_Home.class);
        intent.putExtra("city",search_text.getText().toString());
        setResult(2,intent);
        finish();
    }

}
