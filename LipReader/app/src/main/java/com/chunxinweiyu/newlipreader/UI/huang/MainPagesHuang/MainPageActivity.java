package com.chunxinweiyu.newlipreader.UI.huang.MainPagesHuang;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.leng.MainPagesLeng.CommunityFragment;
import com.chunxinweiyu.newlipreader.UI.leng.MainPagesLeng.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private RadioGroup mTabRadioGroup;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    //权限相关
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_RECORED_AUDIO = 1;
    private static final int REQUEST_INTERNET = 1;
    private static final int REQUEST_PERMISION= 1;
    private static String[] PERMISSIONS = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏字体为黑色 因为UI设计的白色背景
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main_page);
        verifyPermissions(this);
        initView();
        setRadioButtonSize();
    }

    private void setRadioButtonSize(){
        RadioButton[] radioButtons = new RadioButton[4];
        radioButtons[0] = findViewById(R.id.today_tab);
        radioButtons[1] = findViewById(R.id.record_tab);
        radioButtons[2] = findViewById(R.id.contact_tab);
        radioButtons[3] = findViewById(R.id.settings_tab);
        Drawable[] drawables;

        //for循环对每一个RadioButton图片进行缩放
        for (int i = 0; i < radioButtons.length; i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            drawables = radioButtons[i].getCompoundDrawables();
            //获取drawables，2/5表示图片要缩小的比例
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 7/10, drawables[1].getMinimumHeight() * 7/10);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给每一个RadioButton设置图片大小
            radioButtons[i].setCompoundDrawables(null, drawables[1], null, null);
        }


    }

    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        // init fragment

        mFragments = new ArrayList<>(4);
        mFragments.add(new MainPageFragment());
        mFragments.add(new FunctionFragment());
        mFragments.add(new CommunityFragment());
        mFragments.add(new UserFragment());
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    // TODO: 2020/5/2 以后应该要在APP启动的时候就申请权限 否则登录什么的会有问题！
    public static void verifyPermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            //检测是否有相机的权限
            int permissionCamera = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.CAMERA");
            //检测是否有录音的权限
            int permissionRecoredAudio = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.RECORD_AUDIO");
            //检测是否有网络的权限
            int permissionInternet = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.INTERNET");

            if (permissionWrite != PackageManager.PERMISSION_GRANTED || permissionCamera != PackageManager.PERMISSION_GRANTED || permissionRecoredAudio != PackageManager.PERMISSION_GRANTED || permissionInternet != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, PERMISSIONS,REQUEST_EXTERNAL_STORAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }
}
