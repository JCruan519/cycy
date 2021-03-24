package com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chunxinweiyu.newlipreader.R;

public class LoginWithPasswordFragment extends baseFragment {
    //LoginWithPassword：密码登录
    private ImageButton no_pass;
    private ImageButton reg;
    private ImageButton log;
    private View view;
    @Override
    public View initView() {
        if (view == null) {
            view = View.inflate(mActivity, R.layout.fragment_login_with_password, null);
            reg = (ImageButton) view.findViewById(R.id.register_button);
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.sign_in();
                }
            });
            //注册键
            log = (ImageButton) view.findViewById(R.id.log_button);
            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.switchlogon();
                }
            });
            //登陆键
            no_pass = (ImageButton) view.findViewById(R.id.no_password);
            no_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.switchFragment("withpass","withoutpass");
                }
            });
            //切换免密
        }
        return view;
    }
}
