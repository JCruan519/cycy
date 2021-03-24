package com.chunxinweiyu.newlipreader.FunctionPart.BaseClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.MPAndroidChart.MyValueFormatter;
import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.MPAndroidChart.ValueFormatter;
import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.MPAndroidChart.XAxisValueFormatter;
import com.chunxinweiyu.newlipreader.R;
import com.cokus.wavelibrary.utils.SamplePlayer;
import com.cokus.wavelibrary.utils.SoundFile;
import com.cokus.wavelibrary.view.WaveSurfaceView;
import com.cokus.wavelibrary.view.WaveformView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;

public class CongratulationDialog extends Dialog implements View.OnClickListener{
    //在构造方法里提前加载了样式
    private Context context;//上下文
    private int     layoutResID;//布局文件id
    private int[]   listenedItem;//监听的控件id

    private BarChart chart;
    private String[] labels; //保存X轴坐标数据

    private File         mFile;
    private Thread       mLoadSoundFileThread;
    private SoundFile    mSoundFile;
    private boolean      mLoadingKeepGoing;
    private SamplePlayer mPlayer;
    private float        mDensity;
    private String       audioName = "coku1";
    private WaveformView    waveView;

    private void AddLabels(){
        labels = new String[3];
        labels[0] = "声音评分";
        labels[1] = "视频评分";
        labels[2] = "综合评分";
    }


    public CongratulationDialog(Context context,int layoutResID,int[] listenedItem){
        super(context, R.style.CongratulationDialog);//加载dialog的样式
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置dialog显示居中
        //dialogWindow.setWindowAnimations();设置动画效果
        setContentView(layoutResID);


        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*4/5;// 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);//点击外部Dialog消失
        //遍历控件id添加点击注册
        for(int id:listenedItem){
            findViewById(id).setOnClickListener(this);
        }

        waveView = findViewById(R.id.waveview);

        AddLabels();
        DrawChart();
        loadFromFile();
    }

    private void DrawChart(){
        chart = findViewById(R.id.score_chart);
        chart.getDescription().setEnabled(false);

        //设置最大值条目，超出之后不会有值
        chart.setMaxVisibleValueCount(60);
        chart.getViewPortHandler().setMaximumScaleX(1.0f);
        chart.getViewPortHandler().setMaximumScaleY(1.0f);
        //分别在x轴和y轴上进行缩放
        chart.setPinchZoom(true);
        //设置剩余统计图的阴影
        chart.setDrawBarShadow(false);
        //设置网格布局
        chart.setDrawGridBackground(true);
        //通过自定义一个x轴标签来实现2,015 有分割符符bug
        ValueFormatter custom = new MyValueFormatter(" ");
        //获取x轴线
        XAxis xAxis = chart.getXAxis();
        XAxisValueFormatter xAxisValueFormatter = new XAxisValueFormatter(labels);

        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //设置网格布局
        xAxis.setDrawGridLines(true);
        //图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setAvoidFirstLastClipping(false);
        //绘制标签  指x轴上的对应数值 默认true
        xAxis.setDrawLabels(true);
//        xAxis.setValueFormatter(custom);
        xAxis.setValueFormatter(xAxisValueFormatter);
        //缩放后x 轴数据重叠问题
        xAxis.setGranularityEnabled(true);
        //获取右边y标签
        YAxis axisRight = chart.getAxisRight();
        axisRight.setStartAtZero(true);
        //获取左边y轴的标签
        YAxis axisLeft = chart.getAxisLeft();
        //设置Y轴数值 从零开始
        axisLeft.setStartAtZero(true);

        chart.getAxisLeft().setDrawGridLines(false);
        //设置动画时间
        chart.animateXY(600,2000);

        chart.getLegend().setEnabled(true);

        getData();
        //设置柱形统计图上的值
        chart.getData().setValueTextSize(10);
        for (IDataSet set : chart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
    }

    public void getData(){
        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry barEntry1 = new BarEntry(Float.valueOf("1"),Float.valueOf("60"));
        BarEntry barEntry2 = new BarEntry(Float.valueOf("2"),Float.valueOf("80"));
        BarEntry barEntry3 = new BarEntry(Float.valueOf("3"),Float.valueOf("75"));

        values.add(barEntry1);
        values.add(barEntry2);
        values.add(barEntry3);
        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "各项评分");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chart.setData(data);

            chart.setFitBars(true);
        }
        //绘制图表
        chart.invalidate();

    }

    private OnCenterItemClickListener listener;
    public interface OnCenterItemClickListener {
        void OnCenterItemClick(CongratulationDialog dialog, View view);
    }
    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        dismiss();//注意：我在这里加了这句话，表示只要按任何一个控件的id,弹窗都会消失，不管是确定还是取消。
//        listener.OnCenterItemClick(this,v);
    }

    /** 载入wav文件显示波形 */
    public void loadFromFile() {
        try {
            Thread.sleep(300);//让文件写入完成后再载入波形 适当的休眠下
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mFile = new File(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/"+audioName+".wav");
        mLoadingKeepGoing = true;
        // Load the sound file in a background thread
        mLoadSoundFileThread = new Thread() {
            public void run() {
                try {
                    mSoundFile = SoundFile.create(mFile.getAbsolutePath(),null);
                    if (mSoundFile == null) {
                        return;
                    }
                    mPlayer = new SamplePlayer(mSoundFile);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (mLoadingKeepGoing) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishOpeningSoundFile();
                            waveView.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
        };
        mLoadSoundFileThread.start();
    }

    /**waveview载入波形完成*/
    private void finishOpeningSoundFile() {
        waveView.setSoundFile(mSoundFile);
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
        waveView.recomputeHeights(mDensity);
    }
}