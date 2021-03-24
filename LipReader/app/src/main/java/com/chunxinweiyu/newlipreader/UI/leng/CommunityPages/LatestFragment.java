package com.chunxinweiyu.newlipreader.UI.leng.CommunityPages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.LinearFollowAdapter;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.LinearLatestAdapter;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.LoopViewAdapter;
import com.chunxinweiyu.newlipreader.UI.leng.Listener.pagerOnClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {
//    社区最新

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;

    private RecyclerView mRVLatest;



    public LatestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoopView();

        mRVLatest = getActivity().findViewById(R.id.rv_latest);
        final Context context = getActivity();
        mRVLatest.setLayoutManager(new LinearLayoutManager(context));
        mRVLatest.setAdapter(new LinearLatestAdapter(context, new LinearLatestAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(context,"click..."+pos, Toast.LENGTH_LONG).show();
            }
        }));
    }
    private void initLoopView() {
        viewPager = (ViewPager)getActivity().findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout)getActivity().findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView)getActivity().findViewById(R.id.loop_dec);

        // 图片资源id数组
        mImg = new int[]{
                R.drawable.ad_1,
                R.drawable.ad_2,
                R.drawable.ad_3,
                R.drawable.ad_4,
                R.drawable.ad_5
        };

        // 文本描述
        mDec = new String[]{
                "热门1",
                "热门2",
                "热门3",
                "热门4",
                "热门5"
        };

        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
        };

        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片对象
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new pagerOnClickListener(getActivity().getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            dotView = new View(getActivity());
            dotView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(10,10);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }

        ll_dots_container.getChildAt(0).setEnabled(true);
        loop_dec.setText(mDec[0]);
        previousSelectedPosition=0;
        //设置适配器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) %mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                loop_dec.setText(mDec[newPosition]);
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 开启轮询
        new Thread(){
            public void run(){
                isRunning = true;
                while(isRunning){
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest, container, false);
    }
}
