package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi9_ye;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.githang.statusbar.StatusBarCompat;

public class Danyinzi9Activity extends AppCompatActivity {
    private Danyinzi9PronunciationExplainFragment mDanyinzi9PronunciationExplainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu13);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View fragment_container = findViewById(R.id.fragment_container);

        mDanyinzi9PronunciationExplainFragment = new Danyinzi9PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mDanyinzi9PronunciationExplainFragment).commit();
    }
}
