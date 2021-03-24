package com.chunxinweiyu.newlipreader.UI.leng.MainPagesLeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.leng.TextImageView.TextImageView;
import com.chunxinweiyu.newlipreader.UI.leng.UserPages.MyCoursesActivity;
import com.chunxinweiyu.newlipreader.UI.leng.UserPages.PersonalPageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private LinearLayout mLLPerson;
    private TextImageView mTICourse;


    //    用户信息部分的Fragment，会被加载在ViewPager中进行展示
    //    这部分的容器在 newlipreader/huang/MainPagesHuang/MainPageActivity.java

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //个人主页模块
        mLLPerson = getActivity().findViewById(R.id.ll_person_page);
        mTICourse = getActivity().findViewById(R.id.tiv_person_course);

        setListener();
    }

    private void setListener(){
        OnClick onClick = new OnClick();
        mTICourse.setOnClickListener(onClick);
        mLLPerson.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.ll_person_page:
                    intent = new Intent(getActivity(), PersonalPageActivity.class);
                    break;
                case R.id.tiv_person_course:
                    intent = new Intent(getActivity(), MyCoursesActivity.class) ;
                    break;
            }
            startActivity(intent);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
}
