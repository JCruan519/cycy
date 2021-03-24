package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllDuoyinzici.Duoyinzici1_tongguo;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.Shengmu1_b.Shengmu1PronunciationExplainFragment;
import com.githang.statusbar.StatusBarCompat;

public class Duoyinzici1BaseActivity extends AppCompatActivity {
    private Duoyinzici1PronunciationExplainFragment mDuoyinzici1PronunciationExplainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duoyinzici1_base);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        View fragment_container = findViewById(R.id.fragment_container);

        mDuoyinzici1PronunciationExplainFragment = new Duoyinzici1PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mDuoyinzici1PronunciationExplainFragment).commit();

    }
}
