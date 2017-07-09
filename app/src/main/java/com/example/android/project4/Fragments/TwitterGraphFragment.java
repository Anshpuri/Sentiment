package com.example.android.project4.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.HashMap;
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
    private HashMap<String,Integer> map =new HashMap<String, Integer>();
    PieChart mChart;
    private Entry entry;

    int Anger=0,Disgust=0,Fear=0,Happy=0,Neutral=0,Sad=0,Surprise=0;



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


        map.put("anger",0);
        map.put("disgust",0);
        map.put("fear",0);
        map.put("happy",0);
        map.put("neutral",0);
        map.put("sad",0);
        map.put("surprise",0);


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

        entries.add(new PieEntry(Anger, "Anger"));
        entries.add(new PieEntry(Disgust, "Disgust"));
        entries.add(new PieEntry(Fear, "Fear"));
        entries.add(new PieEntry(Happy, "Happy"));
        entries.add(new PieEntry(Neutral, "Neutral"));
        entries.add(new PieEntry(Sad, "Sad"));
        entries.add(new PieEntry(Surprise, "Surprise"));

        final PieDataSet set = new PieDataSet(entries, "Election Results");
        set.setColors(colors);



        final PieData data = new PieData(set);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        mChart.setData(data);

        set.notifyDataSetChanged();
        mChart.notifyDataSetChanged();
        data.notifyDataChanged();
        mChart.invalidate(); // refresh
    }

}
