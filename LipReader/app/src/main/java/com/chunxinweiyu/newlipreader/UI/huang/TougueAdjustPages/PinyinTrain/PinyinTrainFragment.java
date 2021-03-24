package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllDanyinzici.DanyinziciListActivity;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllDuoyinzici.DuoyinziciListActivity;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.PinyinTrain.AllPinyinzhishi.PinyinzhishiListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinyinTrainFragment extends Fragment {
    private View go2AllDanyinziBtn;
    private View go2AllDuoyinziBtn;
    private View go2AllPinyinzhishiBtn;

    public PinyinTrainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pinyin_train, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        go2AllDanyinziBtn = view.findViewById(R.id.go_all_danyinzici_page);
        go2AllDuoyinziBtn = view.findViewById(R.id.go_all_duoyinzici_page);
        go2AllPinyinzhishiBtn = view.findViewById(R.id.go_all_pinyinzhishi_page);
        setGo2AllDanyinziBtn(go2AllDanyinziBtn);
        setGo2AllDuoyinziBtn(go2AllDuoyinziBtn);
        setGo2AllPinyinzhishiBtn(go2AllPinyinzhishiBtn);
    }

    private void setGo2AllDanyinziBtn(View go2AllDanyinziBtn){
        go2AllDanyinziBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), DanyinziciListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setGo2AllDuoyinziBtn(View go2AllDuoyinziBtn){
        go2AllDuoyinziBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), DuoyinziciListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setGo2AllPinyinzhishiBtn(View go2AllPinyinzhishiBtn){
        go2AllPinyinzhishiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), PinyinzhishiListActivity.class);
                startActivity(intent);
            }
        });
    }
}
