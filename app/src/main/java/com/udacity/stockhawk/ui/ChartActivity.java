package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart chart;

    private List<Entry> entryList;
    private List<History> historyList;
    private List<String> dateList;

    private String history;
    private String symbol;

    private static final int DURATION_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
        initData();
        extractData();
    }

    private void initData() {
        entryList = new ArrayList<>();

        Intent intent=getIntent();
        if (intent.hasExtra(getString(R.string.history_extra))){
            history = intent.getStringExtra(getString(R.string.history_extra));
            symbol=intent.getStringExtra(getString(R.string.symbol_extra));
        }

        chart.animateXY(DURATION_MILLIS, DURATION_MILLIS);
        chart.invalidate();

    }

    private void extractData() {
        historyList = extractHistory(history);
        dateList = new ArrayList<>();

        for (int i = 0; i < historyList.size(); i++) {
            float price = Float.parseFloat(String.valueOf(historyList.get(i).getPrice()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(historyList.get(i).getDate());

            SimpleDateFormat format = new SimpleDateFormat("yy-MM");
            dateList.add(format.format(calendar.getTime()));


            entryList.add(new Entry(price, i));

        }

        LineDataSet dataSet = new LineDataSet(entryList, symbol);
        LineData lineData = new LineData(dateList, dataSet);
        chart.setData(lineData);
    }

    public List<History> extractHistory(String history) {
        List<History> historyArrayList = new ArrayList<>();
        String[] historyArray = history.split("\n");
        String[] temporaryArray;
        for (int i = historyArray.length - 1; i >= 0; i--) {
            temporaryArray = historyArray[i].split(",");
            historyArrayList.add(new History(Long.parseLong(temporaryArray[0]), Double.parseDouble(temporaryArray[1])));
        }

        return historyArrayList;
    }

}


