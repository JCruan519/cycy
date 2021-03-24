package com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chunxinweiyu.newlipreader.R;


public class LoginWithoutPasswordFragment extends baseFragment {
    //LoginWithoutPassword：免密码登录
    private ImageButton logg;//登录
    private ImageButton reg;//注册
    private ImageButton log;//有密登录
    private View view;

    @Override
    public View initView() {
        if (view == null) {
            view = View.inflate(mActivity, R.layout.fragment_login_without_password, null);
            reg = (ImageButton) view.findViewById(R.id.register_button);
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.sign_in();
                }
            });
            //注册

            log = (ImageButton) view.findViewById(R.id.password_log);
            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.switchFragment("withoutpass","withpass");
                }
            });
            //切换有密登录

            logg = (ImageButton) view.findViewById(R.id.log_button);
            logg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.switchlogon();
                }
            });
            //登录

        }
        return view;
    }
}
