package com.chunxinweiyu.newlipreader.UI.leng.UserPages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chunxinweiyu.newlipreader.R;

public class PersonalPageActivity extends AppCompatActivity {
    private ImageView mIvBack;

//    个人主页页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        mIvBack = findViewById(R.id.iv_person_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
