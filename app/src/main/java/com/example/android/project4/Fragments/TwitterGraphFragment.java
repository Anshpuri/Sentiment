package com.example.android.project4.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.project4.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwitterGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterGraphFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG="GRAPH";
    private String mParam1;
    private String mParam2;

    PieChart mChart;
    private Entry entry;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public TwitterGraphFragment() {

    }

    public static TwitterGraphFragment newInstance(String param1, String param2) {
        TwitterGraphFragment fragment = new TwitterGraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_twitter_graph, container, false);

        preferences=getActivity().getSharedPreferences("emotionPref", Context.MODE_PRIVATE);

        Log.d(TAG, "onCreateView: "+ preferences.getInt("anger",0));
        Log.d(TAG, "onCreateView: "+ preferences.getInt("disgust",0));

        mChart =(PieChart)v.findViewById(R.id.pieChart);
        mChart.setUsePercentValues(true);
//        mChart.setDescription("Smartphones Market Share");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        addData();

        return  v;
    }

    private void addData() {


        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());




        final List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(preferences.getInt("anger",0), "Anger"));
        entries.add(new PieEntry(preferences.getInt("disgust",0), "Disgust"));
        entries.add(new PieEntry(preferences.getInt("fear",0), "Fear"));
        entries.add(new PieEntry(preferences.getInt("happy",0), "Happy"));
        entries.add(new PieEntry(preferences.getInt("neutral",0), "Neutral"));
        entries.add(new PieEntry(preferences.getInt("sad",0), "Sad"));
        entries.add(new PieEntry(preferences.getInt("surprise",0), "Surprise"));

        final PieDataSet set = new PieDataSet(entries, "emotion Results");
        set.setColors(colors);



        final PieData data = new PieData(set);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        mChart.setData(data);

//        set.notifyDataSetChanged();
//        mChart.notifyDataSetChanged();
//        data.notifyDataChanged();
        mChart.invalidate(); // refresh
    }

}
