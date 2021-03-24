package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllPinyinzhishi;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.githang.statusbar.StatusBarCompat;

public class PinyinzhishiListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyinzhishi_list);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View goPinyinzhishi1Btn = findViewById(R.id.pinyinzhishi1);
        View goPinyinzhishi2Btn = findViewById(R.id.pinyinzhishi2);
        View goBackBtn = findViewById(R.id.go_back_btn);
        setgoPinyinzhishi1Btn(goPinyinzhishi1Btn);
        setgoPinyinzhishi2Btn(goPinyinzhishi2Btn);
        setGoBackBtn(goBackBtn);
    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private  void setgoPinyinzhishi1Btn(View goPinyinzhishi1Btn){
        goPinyinzhishi1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PinyinzhishiListActivity.this, Pinyinzhishi1Activity.class);
                startActivity(intent);
            }
        });
    }

    private  void setgoPinyinzhishi2Btn(View goPinyinzhishi2Btn){
        goPinyinzhishi2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PinyinzhishiListActivity.this, Pinyinzhishi2Activity.class);
                startActivity(intent);
            }
        });
    }
}
