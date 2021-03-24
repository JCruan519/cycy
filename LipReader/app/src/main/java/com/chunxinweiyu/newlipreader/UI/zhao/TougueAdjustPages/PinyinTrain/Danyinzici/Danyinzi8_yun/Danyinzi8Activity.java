package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi8_yun;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.githang.statusbar.StatusBarCompat;

public class Danyinzi8Activity extends AppCompatActivity {
    private Danyinzi8PronunciationExplainFragment mDanyinzi8PronunciationExplainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu13);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View fragment_container = findViewById(R.id.fragment_container);

        mDanyinzi8PronunciationExplainFragment = new Danyinzi8PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mDanyinzi8PronunciationExplainFragment).commit();
    }
}
