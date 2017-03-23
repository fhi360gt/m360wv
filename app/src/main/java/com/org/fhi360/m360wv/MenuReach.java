package com.org.fhi360.m360wv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sergio on 3/1/2016.
 */
public class MenuReach extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_reach);

        ImageButton btn_collect = (ImageButton) findViewById(R.id.btn_collect);
        ImageButton btn_analytics = (ImageButton) findViewById(R.id.btn_analytics);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);


        btn_collect.setOnClickListener(this);
        btn_analytics.setOnClickListener(this);
        btn_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_collect:
                Intent intent1 = new Intent(MenuReach.this, CollectSelectActivityNEW.class);
                startActivity(intent1);
                break;
            case R.id.btn_analytics:
                Intent intent2 = new Intent(MenuReach.this, MainActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn_back:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
