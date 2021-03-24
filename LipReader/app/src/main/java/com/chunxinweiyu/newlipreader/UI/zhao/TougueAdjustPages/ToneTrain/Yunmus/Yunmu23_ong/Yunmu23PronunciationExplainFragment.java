package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Yunmus.Yunmu23_ong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.shun.TougueAdjustPages.ToneTrain.Shengmus.Shengmu2_p.Shengmu2PracticeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Yunmu23PronunciationExplainFragment extends Fragment {

    public Yunmu23PronunciationExplainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yunmu23_pronunciation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View goBackBtn = view.findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);
        View startPracticeBtn = view.findViewById(R.id.start_practice_btn);
        setStartPracticeBtn(startPracticeBtn);
    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void setStartPracticeBtn(View startPracticeBtn){
        startPracticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new Yunmu23PracticeFragment()).commit();
            }
        });
    }
}
