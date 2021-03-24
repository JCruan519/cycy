package com.chunxinweiyu.newlipreader.UI.zhao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.MainPagesHuang.MainPageActivity;

public class QuestionnaireActivity extends AppCompatActivity {
//    问卷页 如果是注册则进入这页
    private Button submit;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        submit = (Button)this.findViewById(R.id.submission);
        submit.setOnClickListener(new switch_main());
        back = (ImageButton)this.findViewById(R.id.question_back);
        back.setOnClickListener(new switch_log());
    }
    class switch_main implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(QuestionnaireActivity.this, MainPageActivity.class);
            startActivity(intent);
        }
    }
    class switch_log implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(QuestionnaireActivity.this,SigninActivity.class);
            startActivity(intent);
        }
    }
}
