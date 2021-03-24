package com.chunxinweiyu.newlipreader.UI.zhao;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.MainPagesHuang.MainPageActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LogoPageActivity extends AppCompatActivity {
//    APP的第一个界面放LOGO！显示个3s左右就可以跳转到下一个界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);
        final Intent intent_new = new Intent(LogoPageActivity.this,LoginActivity.class);
        final Intent intent_loged = new Intent(LogoPageActivity.this, MainPageActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {@Override public void run() {
            if(isnew())
            startActivity(intent_new);
            else startActivity(intent_loged);
        }
        };
        timer.schedule(task, 3 * 1000);//3s
        }
    public boolean isnew(){
        return true;
    }
    //判断是否已经登陆


}

