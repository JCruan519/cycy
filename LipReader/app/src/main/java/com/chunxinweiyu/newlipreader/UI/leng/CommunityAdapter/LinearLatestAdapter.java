package com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chunxinweiyu.newlipreader.R;

public class LinearLatestAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    @NonNull
    private Context mContext;
    private OnItemClickListener mListener;
    //private List<String> list;

    public LinearLatestAdapter(Context context , OnItemClickListener listener){
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //viewType可以通过这个，展示不同的item
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_hot_item, parent, false));
        //这里需要传入每个item长什么样的布局，需要去layout中去画我们的布局
        }

    @Override
    //通过getItemViewType的返回值来选择具体的item显示
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            ((LinearViewHolder)holder).textView.setText("主标题");
            //如果是直接用viewholder的话，是不能用test view的
        //将点击事件放到外面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"click..."+position,Toast.LENGTH_SHORT).show();
                mListener.onClick(position);
            }
        });
    }

    //去控制viewType的方法，根据位置的奇偶性来区分
    @Override
    public int getItemViewType(int position) {
        if(position % 2 == 0){
            return 0;//偶数
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public LinearViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.hot_title);
        }
    }


    //接口
    public interface  OnItemClickListener{
        void onClick(int pos);
    }
}
