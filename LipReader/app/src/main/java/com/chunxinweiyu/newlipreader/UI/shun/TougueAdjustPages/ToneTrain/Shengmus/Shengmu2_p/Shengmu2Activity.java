package com.chunxinweiyu.newlipreader.UI.shun.TougueAdjustPages.ToneTrain.Shengmus.Shengmu2_p;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.Shengmu1_b.Shengmu1PronunciationExplainFragment;
import com.githang.statusbar.StatusBarCompat;

public class Shengmu2Activity extends AppCompatActivity {
    private Shengmu2PronunciationExplainFragment mShengmu2PronunciationExplainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengmu2);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        View fragment_container = findViewById(R.id.fragment_container);

        mShengmu2PronunciationExplainFragment = new Shengmu2PronunciationExplainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mShengmu2PronunciationExplainFragment).commit();
    }
}
