package com.chunxinweiyu.newlipreader.UI.zhao;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.MainPagesHuang.MainPageActivity;
import com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments.LoginWithPasswordFragment;
import com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments.LoginWithoutPasswordFragment;

public class LoginActivity extends AppCompatActivity {
//    这一块应该要用到fragment 这个activity就当做容器 fragment放在LoginFragments文件夹下
//    LoginWithPassword：密码登录
//    LoginWithoutPassword：免密码登录
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment currentFra;
    private EditText account;
    private EditText password;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_login);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        LoginWithoutPasswordFragment loginWithoutPasswordFragment= new LoginWithoutPasswordFragment();
        LoginWithPasswordFragment loginWithPasswordFragment = new LoginWithPasswordFragment();
        ft.add(R.id.fragment_container,loginWithoutPasswordFragment,"withoutpass").add(R.id.fragment_container,loginWithPasswordFragment,"withpass").hide(loginWithPasswordFragment).commit();
        account = (EditText)this.findViewById(R.id.account);
        password = (EditText)this.findViewById(R.id.password);
        back = (ImageButton)this.findViewById(R.id.back);
        back.setOnClickListener(new log_back());

    }
    public void switchFragment(String fromTag, String toTag) {
        Fragment from = fm.findFragmentByTag(fromTag);
        Fragment to = fm.findFragmentByTag(toTag);
        if (currentFra != to) {
            currentFra = to;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) {
                //判断是否被添加到了Activity里面去了
                transaction.hide(from).add(R.id.fragment_container, to).commit();
            }
            else {
                transaction.hide(from).show(to).commit();
            }
        }
    }
    //切换fragment


    public void switchlogon(){
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
    }
    //登录，切到主页面

    class log_back implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switchFragment("logon", "log");
            switchFragment("reg", "log");
        }
    }
    //返回

    public void sign_in(){
            Intent intent = new Intent(LoginActivity.this,SigninActivity.class);
            startActivity(intent);
    }
    //注册

}