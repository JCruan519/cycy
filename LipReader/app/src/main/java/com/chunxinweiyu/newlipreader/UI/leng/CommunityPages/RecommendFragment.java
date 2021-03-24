package com.chunxinweiyu.newlipreader.UI.leng.CommunityPages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.LinearRecommendAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {
//    社区推荐

    private RecyclerView mRvRecommend;

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRvRecommend = getActivity().findViewById(R.id.rv_recommend);
        final Context context = getActivity();
        mRvRecommend.setLayoutManager(new LinearLayoutManager(context));
        mRvRecommend.setAdapter(new LinearRecommendAdapter(context, new LinearRecommendAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(context,"click..."+pos, Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }
}
