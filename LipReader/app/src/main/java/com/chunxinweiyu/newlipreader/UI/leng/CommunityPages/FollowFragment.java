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
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.LinearFollowAdapter;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter.LinearRecommendAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowFragment extends Fragment {
//    社区关注
    private RecyclerView mRvFollow;

    public FollowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRvFollow = getActivity().findViewById(R.id.rv_follow);
        final Context context = getActivity();
        mRvFollow.setLayoutManager(new LinearLayoutManager(context));
        mRvFollow.setAdapter(new LinearFollowAdapter(context, new LinearFollowAdapter.OnItemClickListener() {
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
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }
}
