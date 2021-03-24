package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu20_ang;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.Shengmu1_b.Shengmu1PronunciationExplainFragment;
import com.githang.statusbar.StatusBarCompat;

public class Yunmu20Activity extends AppCompatActivity {
    private Yunmu20PronunciationExplainFragment mYunmu20PronunciationExplainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu13);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View fragment_container = findViewById(R.id.fragment_container);

        mYunmu20PronunciationExplainFragment = new Yunmu20PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mYunmu20PronunciationExplainFragment).commit();
    }
}
