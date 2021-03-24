package com.chunxinweiyu.newlipreader.UI.leng.UserPages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chunxinweiyu.newlipreader.R;
import com.chunxinweiyu.newlipreader.UI.leng.CourseAdapter.LinearCourseFinishAdapter;
import com.chunxinweiyu.newlipreader.UI.leng.CourseAdapter.LinearCourseGoAdapter;

public class MyCoursesActivity extends AppCompatActivity {
    private ImageView mIVBack;
    private RecyclerView mRVFinish;
    private RecyclerView mRvGoing;
//    我的课程页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        mRVFinish = findViewById(R.id.rv_course_finish);
        mIVBack = findViewById(R.id.iv_course_back);
        mRvGoing = findViewById(R.id.rv_course_going);

        mIVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRVFinish.setLayoutManager(new LinearLayoutManager(MyCoursesActivity.this));
        mRVFinish.setAdapter(new LinearCourseFinishAdapter(MyCoursesActivity.this, new LinearCourseFinishAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(MyCoursesActivity.this,"click..."+pos, Toast.LENGTH_LONG).show();
            }
        }));

        mRvGoing.setLayoutManager(new LinearLayoutManager(MyCoursesActivity.this));
        mRvGoing.setAdapter(new LinearCourseGoAdapter(MyCoursesActivity.this, new LinearCourseGoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(MyCoursesActivity.this,"click..."+pos, Toast.LENGTH_LONG).show();
            }
        }));

//    class MyDecoration extends RecyclerView.ItemDecoration{
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.set(0,0,0,1);
//        }
//    }
    }
}
