package com.chunxinweiyu.newlipreader.UI.huang.MainPagesHuang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chunxinweiyu.newlipreader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {

    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView clockInDayConutrt = view.findViewById(R.id.clock_in_day_count);
        setDayCount(clockInDayConutrt);
        TextView everydayPositive = view.findViewById(R.id.everyday_positive);
        setEverydayPositive(everydayPositive);
        TextView wordCnt = view.findViewById(R.id.word_counter);
        setWordCount(wordCnt);
        TextView sentenceCnt = view.findViewById(R.id.centence_counter);
        setSentenceCount(sentenceCnt);
        TextView hourCnt = view.findViewById(R.id.hour_counter);
        setHourCount(hourCnt);



    }

    private void setDayCount(TextView clockInDayConutrt){
        Spannable dayCntString = new SpannableString("已学8天");
        // 字体大小
        dayCntString.setSpan(new AbsoluteSizeSpan(300), 2, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 颜色
        dayCntString.setSpan(new ForegroundColorSpan(Color.rgb(254,184,40)), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        clockInDayConutrt.setText(dayCntString);
    }

    private void setEverydayPositive(TextView everydayPositive){
        Spannable everydayPositiveString = new SpannableString("这里到时候每天随机获取一句正能量");
        everydayPositive.setText((everydayPositiveString));
    }

    private void setWordCount(TextView wordCnt){
        Spannable wordCntString = new SpannableString("50\n词");
        // 颜色
        wordCntString.setSpan(new ForegroundColorSpan(Color.rgb(254,184,40)), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordCnt.setText(wordCntString);
    }

    private void setSentenceCount(TextView sentenceCnt){
        Spannable sentenceCntString = new SpannableString("12\n句");
        // 颜色
        sentenceCntString.setSpan(new ForegroundColorSpan(Color.rgb(254,184,40)), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sentenceCnt.setText(sentenceCntString);
    }

    private void setHourCount(TextView hourCnt){
        Spannable hourCntString = new SpannableString("2\n时");
        // 颜色
        hourCntString.setSpan(new ForegroundColorSpan(Color.rgb(254,184,40)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        hourCnt.setText(hourCntString);
    }

}
