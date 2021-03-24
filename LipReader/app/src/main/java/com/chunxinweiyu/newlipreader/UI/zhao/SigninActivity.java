package com.chunxinweiyu.newlipreader.UI.zhao;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.chunxinweiyu.newlipreader.R;
public class SigninActivity extends AppCompatActivity {
//    注册界面
    private ImageButton reg_button;
    private ImageButton reg_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        reg_button = (ImageButton)findViewById(R.id.reg_button);
        reg_button.setOnClickListener(new switch_questionnaire());
        //注册键
        reg_back = (ImageButton)findViewById(R.id.reg_back);
        reg_back.setOnClickListener(new switch_log());
        //返回键（回登录）
    }
    class switch_questionnaire implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SigninActivity.this,QuestionnaireActivity.class);
            startActivity(intent);
        }
    }
    class switch_log implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SigninActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
