package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllDanyinzici;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllYunmu.YunmuListActivity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi10_yue.Danyinzi10Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi6_yu.Danyinzi6Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi7_yin.Danyinzi7Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi8_yun.Danyinzi8Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi9_ye.Danyinzi9Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu13_ie.Yunmu13Activity;
import com.githang.statusbar.StatusBarCompat;

public class DanyinziciListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danyinzici_list);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View goBackBtn = findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);
        View goDanyinzi6 = findViewById(R.id.danyinzi6_yu);
        setGoDanyinzi6(goDanyinzi6);
        View goDanyinzi7 = findViewById(R.id.danyinzi7_yin);
        setGoDanyinzi7(goDanyinzi7);
        View goDanyinzi8 = findViewById(R.id.danyinzi8_yun);
        setGoDanyinzi8(goDanyinzi8);
        View goDanyinzi9 = findViewById(R.id.danyinzi9_ye);
        setGoDanyinzi9(goDanyinzi9);
        View goDanyinzi10 = findViewById(R.id.danyinzi10_yue);
        setGoDanyinzi10(goDanyinzi10);
    }


    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setGoDanyinzi6(View goDanyinzi6){
        goDanyinzi6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanyinziciListActivity.this, Danyinzi6Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoDanyinzi7(View goDanyinzi7){
        goDanyinzi7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanyinziciListActivity.this, Danyinzi7Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoDanyinzi8(View goDanyinzi8){
        goDanyinzi8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanyinziciListActivity.this, Danyinzi8Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoDanyinzi9(View goDanyinzi9){
        goDanyinzi9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanyinziciListActivity.this, Danyinzi9Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoDanyinzi10(View goDanyinzi10){
        goDanyinzi10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanyinziciListActivity.this, Danyinzi10Activity.class);
                startActivity(intent);
            }
        });
    }

}
