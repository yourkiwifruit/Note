package com.example.zhu.note;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhu on 2018/8/19.
 */

public class SharedHelper {

    Context mcontext;

    public SharedHelper( Context mcontext){
        this.mcontext = mcontext;
    }

    //将搜索的城市存入SharedPreferences
    public void save(String city){

        SharedPreferences sp = mcontext.getSharedPreferences("city",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("local",city);
        editor.commit();
    }

    public String read(){
        String city;
        SharedPreferences sp = mcontext.getSharedPreferences("city",Context.MODE_PRIVATE);
        city = sp.getString("local","合肥");
        return city;
    }



}
