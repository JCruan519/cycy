package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllDuoyinzici;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllDuoyinzici.Duoyinzici1_tongguo.Duoyinzici1BaseActivity;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.Shengmu1_b.Shengmu1BaseActivity;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.ShengmuListActivity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Duoyinzici.Duoyinzi10_shengchan.Duoyinzici10Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Duoyinzici.Duoyinzi6_qilai.Duoyinzici6Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Duoyinzici.Duoyinzi7_canjia.Duoyinzici7Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Duoyinzici.Duoyinzi8_kaishi.Duoyinzici8Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Duoyinzici.Duoyinzi9_chuangzao.Duoyinzici9Activity;
import com.githang.statusbar.StatusBarCompat;

public class DuoyinziciListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duoyinzici_list);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        // 绑定回退按钮
        View goBackBtn = findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);
        // 绑定第一个词
        View goDuoyinzici1 = findViewById(R.id.duoyinzici1);
        setgoDuoyinzici1(goDuoyinzici1);
        View goDuoyinzici6 = findViewById(R.id.duoyinzici6);
        setgoDuoyinzici6(goDuoyinzici6);
        View goDuoyinzici7 = findViewById(R.id.duoyinzici7);
        setgoDuoyinzici7(goDuoyinzici7);
        View goDuoyinzici8 = findViewById(R.id.duoyinzici8);
        setgoDuoyinzici8(goDuoyinzici8);
        View goDuoyinzici9 = findViewById(R.id.duoyinzici9);
        setgoDuoyinzici9(goDuoyinzici9);
        View goDuoyinzici10 = findViewById(R.id.duoyinzici10);
        setgoDuoyinzici10(goDuoyinzici10);
    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    protected void setgoDuoyinzici1(View goDuoyinzici1){
        goDuoyinzici1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuoyinziciListActivity.this, Duoyinzici1BaseActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void setgoDuoyinzici6(View goDuoyinzici6){
        goDuoyinzici6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuoyinziciListActivity.this, Duoyinzici6Activity.class);
                startActivity(intent);
            }
        });
    }
    protected void setgoDuoyinzici7(View goDuoyinzici7){
        goDuoyinzici7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuoyinziciListActivity.this, Duoyinzici7Activity.class);
                startActivity(intent);
            }
        });
    }
    protected void setgoDuoyinzici8(View goDuoyinzici8){
        goDuoyinzici8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuoyinziciListActivity.this, Duoyinzici8Activity.class);
                startActivity(intent);
            }
        });
    }
    protected void setgoDuoyinzici9(View goDuoyinzici9){
        goDuoyinzici9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuoyinziciListActivity.this, Duoyinzici9Activity.class);
                startActivity(intent);
            }
        });
    }
    protected void setgoDuoyinzici10(View goDuoyinzici10){
        goDuoyinzici10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuoyinziciListActivity.this, Duoyinzici10Activity.class);
                startActivity(intent);
            }
        });
    }
}
