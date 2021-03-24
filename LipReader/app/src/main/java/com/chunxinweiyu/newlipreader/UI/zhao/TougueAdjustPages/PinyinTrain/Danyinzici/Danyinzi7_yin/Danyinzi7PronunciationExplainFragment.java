package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.PinyinTrain.Danyinzici.Danyinzi7_yin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Danyinzi7PronunciationExplainFragment extends Fragment {

    public Danyinzi7PronunciationExplainFragment() {
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
        return inflater.inflate(R.layout.fragment_danyinzi7_pronunciation, container, false);
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
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new Danyinzi7PracticeFragment()).commit();
            }
        });
    }
}
