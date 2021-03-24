package com.chunxinweiyu.newlipreader.UI.huang.MainPagesHuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.huang.TougueAdjustPages.TougueAdjustActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends Fragment {

    public FunctionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View tougueAdjustBtn = view.findViewById(R.id.tougue_adjust);
        setTougueAdjustBtn(tougueAdjustBtn);

    }

    private void setTougueAdjustBtn(View tougueAdjustBtn){
        tougueAdjustBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TougueAdjustActivity.class);
                startActivity(intent);
            }
        });
    }
}
