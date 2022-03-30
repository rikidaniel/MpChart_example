package com.gamma.chart.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gamma.chart.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private BarChart chart;

    private PieChart pieChart;

    LineChart lineChartTarget, lineChartPenjualanSales, lineChartArchivement;
    LineData lineData;
    List<Entry> entryList = new ArrayList<>();

    PieChart ijin, sakit, cuti, telat;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ((int) value) + " " + "K";
            }
        };

        // PIE CHART TOTAL GROSS PROFIT
        pieChart = view.findViewById(R.id.gross_profit);
        setupPieChart();
        loadPieChartData();



        // BAR CHART ARCHIVEMENT
        chart = view.findViewById(R.id.archievent);

        setData(5, 100);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);

        chart.setMaxVisibleValueCount(60);

        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


        // LINE CHART TARGET, PENJUALAN SALES & ARCHIEVEMENT
        lineChartTarget = view.findViewById(R.id.target);
        lineChartPenjualanSales = view.findViewById(R.id.penjualan_sales);
        lineChartArchivement = view.findViewById(R.id.archiv);

        entryList.add(new Entry(10,20));
        entryList.add(new Entry(5,10));
        entryList.add(new Entry(7,31));
        entryList.add(new Entry(3,14));
        LineDataSet lineDataSet = new LineDataSet(entryList,"Data");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setFillAlpha(110);
        lineData = new LineData(lineDataSet);
        lineChartTarget.setData(lineData);
        lineChartTarget.setVisibleXRangeMaximum(10);
        lineChartTarget.invalidate();

        lineChartPenjualanSales.setData(lineData);
        lineChartPenjualanSales.setVisibleXRangeMaximum(10);
        lineChartPenjualanSales.invalidate();

        lineChartArchivement.setData(lineData);
        lineChartArchivement.setVisibleXRangeMaximum(10);
        lineChartArchivement.invalidate();




        // PIE CHART KEHADIRAN
        ijin = view.findViewById(R.id.Ijin);
        sakit = view.findViewById(R.id.Sakit);
        cuti = view.findViewById(R.id.Cuti);
        telat = view.findViewById(R.id.Telat);

        setupKehadiranChart();
        loadKehadiranChartData();

        return view;
    }


    // PIE CHART TOTAL GROSS PROFIT DATA SET
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.70f, "Green"));
        entries.add(new PieEntry(0.30f, "Yellow"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }




    // BAR CHART ARCHIVEMENT DATA SET
    private void setData(int count, float range) {
        float start = 1f;
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));
            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }
        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "The year 2022");
            set1.setDrawIcons(false);
            int startColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
            int endColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark);
            List<GradientColor> gradientFills = new ArrayList<>();
            gradientFills.add(new GradientColor(startColor1, endColor1));
            gradientFills.add(new GradientColor(startColor2, endColor2));
            gradientFills.add(new GradientColor(startColor3, endColor3));
            set1.setGradientColors(gradientFills);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.setData(data);
        }
    }


    // PIE CHART KEHADIRAN
    private void setupKehadiranChart() {
        ijin.getDescription().setEnabled(false);
        sakit.getDescription().setEnabled(false);
        cuti.getDescription().setEnabled(false);
        telat.getDescription().setEnabled(false);

        Legend i = ijin.getLegend();
        i.setDrawInside(false);
        i.setEnabled(false);

        Legend s = sakit.getLegend();
        s.setDrawInside(false);
        s.setEnabled(false);

        Legend c = cuti.getLegend();
        c.setDrawInside(false);
        c.setEnabled(false);

        Legend t = telat.getLegend();
        t.setDrawInside(false);
        t.setEnabled(false);
    }

    private void loadKehadiranChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(1));
        entries.add(new PieEntry(2));
        entries.add(new PieEntry(3));
        entries.add(new PieEntry(4));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(ijin));
        data.setValueFormatter(new PercentFormatter(sakit));
        data.setValueFormatter(new PercentFormatter(cuti));
        data.setValueFormatter(new PercentFormatter(telat));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        ijin.setData(data);
        ijin.invalidate();
        ijin.animateY(1400, Easing.EaseInOutQuad);

        sakit.setData(data);
        sakit.invalidate();
        sakit.animateY(1400, Easing.EaseInOutQuad);

        cuti.setData(data);
        cuti.invalidate();
        cuti.animateY(1400, Easing.EaseInOutQuad);

        telat.setData(data);
        telat.invalidate();
        telat.animateY(1400, Easing.EaseInOutQuad);
    }


}
