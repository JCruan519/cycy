package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.PinyinTrainFragment;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.ToneTrainFragment;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

public class TougueAdjustActivity extends AppCompatActivity {
    private Fragment       currentFragment;
    private MyPagerAdapter pagerAdapter;
    private ViewPager      viewPager;
    private TabLayout      tabLayout;

    private ToneTrainFragment   mToneTrainFragment;
    private PinyinTrainFragment mPinyinTrainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tougue_adjust);
        // 设置状态栏颜色为白色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        initPages();
        View goBackBtn = findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);



    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initPages(){
        tabLayout = findViewById(R.id.tougue_adjust_tab);
        viewPager = findViewById(R.id.tougue_adjust_viewPager);
        mToneTrainFragment = new ToneTrainFragment();
        mPinyinTrainFragment = new PinyinTrainFragment();
        List<String> mTitle = new ArrayList<>();
        mTitle.add("音调训练");
        mTitle.add("拼音训练");
        List<Fragment> mFragment = new ArrayList<>();
        mFragment.add(mToneTrainFragment);
        mFragment.add(mPinyinTrainFragment);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),mTitle,mFragment);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    //派生一个适配器
    class MyPagerAdapter extends FragmentStatePagerAdapter {

        List<String>   mTitle;
        List<Fragment> mFragment;

        public MyPagerAdapter(FragmentManager fm, List<String> mTitle, List<Fragment> mFragment) {
            super(fm);
            this.mTitle = mTitle;
            this.mFragment = mFragment;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragment.get(i);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            currentFragment = (Fragment) object;
            super.setPrimaryItem(container, position, object);
        }

        public Fragment getCurrentFragment() {
            return currentFragment;
        }


    }
}
