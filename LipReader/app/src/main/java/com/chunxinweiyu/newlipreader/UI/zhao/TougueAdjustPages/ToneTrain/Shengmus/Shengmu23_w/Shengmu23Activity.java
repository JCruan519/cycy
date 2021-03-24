package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu23_w;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.githang.statusbar.StatusBarCompat;

public class Shengmu23Activity extends AppCompatActivity {
    private Shengmu23PronunciationExplainFragment mShengmu23PronunciationExplainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu13);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View fragment_container = findViewById(R.id.fragment_container);

        mShengmu23PronunciationExplainFragment = new Shengmu23PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mShengmu23PronunciationExplainFragment).commit();
    }
}
