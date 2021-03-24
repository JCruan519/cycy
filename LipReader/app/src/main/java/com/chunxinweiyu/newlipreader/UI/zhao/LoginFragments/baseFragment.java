package com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.zhao.LoginActivity;
import com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments.LoginWithoutPasswordFragment;
import com.chunxinweiyu.newlipreader.UI.zhao.LoginFragments.LoginWithPasswordFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public abstract class baseFragment extends Fragment{
    public LoginActivity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (LoginActivity) getActivity();
        View view = initView();
        return view;
    }
    public abstract View initView();

}