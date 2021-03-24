package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.Shengmu1_b.Shengmu1BaseActivity;
import com.chunxinweiyu.newlipreader.UI.shun.TougueAdjustPages.ToneTrain.Shengmus.Shengmu2_p.Shengmu2Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu13_q.Shengmu13Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu14_x.Shengmu14Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu15_z.Shengmu15Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu16_c.Shengmu16Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu17_s.Shengmu17Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu18_r.Shengmu18Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu19_zh.Shengmu19Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu20_ch.Shengmu20Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu21_sh.Shengmu21Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu22_y.Shengmu22Activity;
import com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu23_w.Shengmu23Activity;
import com.githang.statusbar.StatusBarCompat;

public class ShengmuListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu_list);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View goBackBtn = findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);
        View goShengmu1 = findViewById(R.id.shengmu1_b);
        setGoShengmu1(goShengmu1);
        View goShengmu2 = findViewById(R.id.shengmu2_p);
        setGoShengmu2(goShengmu2);
        View goShengmu13 = findViewById(R.id.shengmu13_q);
        setGoShengmu13(goShengmu13);
        View goShengmu14 = findViewById(R.id.shengmu14_x);
        setGoShengmu14(goShengmu14);
        View goShengmu15 = findViewById(R.id.shengmu15_z);
        setGoShengmu15(goShengmu15);
        View goShengmu16 = findViewById(R.id.shengmu16_c);
        setGoShengmu16(goShengmu16);
        View goShengmu17 = findViewById(R.id.shengmu17_s);
        setGoShengmu17(goShengmu17);
        View goShengmu18 = findViewById(R.id.shengmu18_r);
        setGoShengmu18(goShengmu18);
        View goShengmu19 = findViewById(R.id.shengmu19_zh);
        setGoShengmu19(goShengmu19);
        View goShengmu20 = findViewById(R.id.shengmu20_ch);
        setGoShengmu20(goShengmu20);
        View goShengmu21 = findViewById(R.id.shengmu21_sh);
        setGoShengmu21(goShengmu21);
        View goShengmu22 = findViewById(R.id.shengmu22_y);
        setGoShengmu22(goShengmu22);
        View goShengmu23 = findViewById(R.id.shengmu23_w);
        setGoShengmu23(goShengmu23);

    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setGoShengmu1(View goShengmu1){
        goShengmu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu1BaseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setGoShengmu2(View goShengmu2){
        goShengmu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu2Activity.class);
                startActivity(intent);
            }
        });
    }

    private void setGoShengmu13(View goShengmu13){
        goShengmu13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu13Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu14(View goShengmu14){
        goShengmu14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu14Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu15(View goShengmu15){
        goShengmu15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu15Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu16(View goShengmu16){
        goShengmu16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu16Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu17(View goShengmu17){
        goShengmu17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu17Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu18(View goShengmu18){
        goShengmu18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu18Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu19(View goShengmu19){
        goShengmu19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu19Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu20(View goShengmu20){
        goShengmu20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu20Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu21(View goShengmu21){
        goShengmu21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu21Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu22(View goShengmu22){
        goShengmu22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu22Activity.class);
                startActivity(intent);
            }
        });
    }
    private void setGoShengmu23(View goShengmu23){
        goShengmu23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShengmuListActivity.this, Shengmu23Activity.class);
                startActivity(intent);
            }
        });
    }

}
