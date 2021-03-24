package com.chunxinweiyu.newlipreader.UI.leng.MainPagesLeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.MyTabPageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {
//    社区部分的Fragment，会被加载在ViewPager中进行展示
//    这部分的容器在 newlipreader/huang/MainPagesHuang/MainPageActivity.java
//    根据设计图来说这里还嵌套了一个ViewPageer和Fragments的组合可以将这个Fragment作为容器

    private TabLayout mTLTop;
    private ViewPager mVpCommunity;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTLTop = getActivity().findViewById(R.id.tablayout_top_tab);
        mVpCommunity = getActivity().findViewById(R.id.vp_community_tab_content);

        //=======================显示首页顶部标签栏S==========================
        String[] mTitles =getResources().getStringArray(R.array.community_top_tab);
        //MyTabPageAdapter()里的参数不能用getFragmentManager,否则滑动出去,再滑回来会没数据
        mVpCommunity.setAdapter(new MyTabPageAdapter(getChildFragmentManager(),mTitles));
        mTLTop.setupWithViewPager(mVpCommunity);
        //=======================显示首页顶部标签栏E==========================

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }
}
