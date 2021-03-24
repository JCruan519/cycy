package com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllShengmu.ShengmuListActivity;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.ToneTrain.AllYunmu.YunmuListActivity;


public class ToneTrainFragment extends Fragment {
//


    private View go2AllShengmuBtn;
    private View go2AllYunmuBtn;

    public ToneTrainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tone_train, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        go2AllShengmuBtn = view.findViewById(R.id.go_all_shengmu_page);
        go2AllYunmuBtn = view.findViewById(R.id.go_all_yunmu_page);
        setGo2AllShengmuBtn(go2AllShengmuBtn);
        setGo2AllYunmuBtn(go2AllYunmuBtn);
    }

    private void setGo2AllShengmuBtn(View go2AllShengmuBtn){
        go2AllShengmuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ShengmuListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setGo2AllYunmuBtn(View go2AllYunmuBtn){
        go2AllYunmuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), YunmuListActivity.class);
                startActivity(intent);
            }
        });
    }



}
