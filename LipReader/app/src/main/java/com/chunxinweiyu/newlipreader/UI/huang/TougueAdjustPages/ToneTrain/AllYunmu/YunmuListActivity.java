package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllYunmu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.Shengmu1_b.Shengmu1BaseActivity;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.ShengmuListActivity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu13_ie.Yunmu13Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu14_üe.Yunmu14Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu15_er.Yunmu15Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu16_an.Yunmu16Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu17_en.Yunmu17Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu18_in.Yunmu18Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu19_un.Yunmu19Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu20_ang.Yunmu20Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu21_eng.Yunmu21Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu22_ing.Yunmu22Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu23_ong.Yunmu23Activity;
import com.githang.statusbar.StatusBarCompat;

public class YunmuListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yunmu_list);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View goBackBtn = findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);
        View goYunmu13 = findViewById(R.id.yunmu13_ie);
        setGoYunmu13(goYunmu13);
        View goYunmu14 = findViewById(R.id.yunmu14_üe);
        setGoYunmu14(goYunmu14);
        View goYunmu15 = findViewById(R.id.yunmu15_er);
        setGoYunmu15(goYunmu15);
        View goYunmu16 = findViewById(R.id.yunmu16_an);
        setGoYunmu16(goYunmu16);
        View goYunmu17 = findViewById(R.id.yunmu17_en);
        setGoYunmu17(goYunmu17);
        View goYunmu18 = findViewById(R.id.yunmu18_in);
        setGoYunmu18(goYunmu18);
        View goYunmu19 = findViewById(R.id.yunmu19_un);
        setGoYunmu19(goYunmu19);
        View goYunmu20 = findViewById(R.id.yunmu20_ang);
        setGoYunmu20(goYunmu20);
        View goYunmu21 = findViewById(R.id.yunmu21_eng);
        setGoYunmu21(goYunmu21);
        View goYunmu22 = findViewById(R.id.yunmu22_ing);
        setGoYunmu22(goYunmu22);
        View goYunmu23 = findViewById(R.id.yunmu23_ong);
        setGoYunmu23(goYunmu23);

    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setGoYunmu13(View goYunmu13){
        goYunmu13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu13Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu14(View goYunmu14){
        goYunmu14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu14Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu15(View goYunmu15){
        goYunmu15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu15Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu16(View goYunmu16){
        goYunmu16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu16Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu17(View goYunmu17){
        goYunmu17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu17Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu18(View goYunmu18){
        goYunmu18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu18Activity.class);
                startActivity(intent);
            }
        });
    }    private void setGoYunmu19(View goYunmu19){
        goYunmu19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu19Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu20(View goYunmu20){
        goYunmu20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu20Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu21(View goYunmu21){
        goYunmu21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu21Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu22(View goYunmu22){
        goYunmu22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu22Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoYunmu23(View goYunmu23){
        goYunmu23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YunmuListActivity.this, Yunmu23Activity.class);
                startActivity(intent);
            }
        });
    }
}
