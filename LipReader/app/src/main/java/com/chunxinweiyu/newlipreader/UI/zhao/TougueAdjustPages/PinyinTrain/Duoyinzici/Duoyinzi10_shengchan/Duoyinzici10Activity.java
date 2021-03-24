package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Duoyinzici.Duoyinzi10_shengchan;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.githang.statusbar.StatusBarCompat;

public class Duoyinzici10Activity extends AppCompatActivity {
    private Duoyinzici10PronunciationExplainFragment mDuoyinzici10PronunciationExplainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu13);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View fragment_container = findViewById(R.id.fragment_container);

        mDuoyinzici10PronunciationExplainFragment = new Duoyinzici10PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mDuoyinzici10PronunciationExplainFragment).commit();
    }
}
