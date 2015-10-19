package com.brettren.android.sunshine.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;


public class DrawView extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] titles = new String[] { "First", "Second"};

        List<double[]> x = new ArrayList<>();
        List<double[]> y = new ArrayList<>();

        x.add(new double[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 } );
        x.add(new double[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 } );

        y.add(Utility.mMax);
        y.add(Utility.mMin);

        XYMultipleSeriesDataset dataset = buildDataset(titles, x, y);

        int[] colors = new int[] { Color.RED, Color.BLUE};
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND};
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles, true);

        setChartSettings(renderer, "Temperature in 10 Days", "X", "Y", -1, 10, Utility.getMin(Utility.mMin)-5, Utility.getMax(Utility.mMax)+5 , Color.BLACK, Color.BLACK);

        View chart = ChartFactory.getLineChartView(this, dataset, renderer);

        setContentView(chart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);  // 处理Up后退的情况
    }

    protected XYMultipleSeriesDataset buildDataset(String[] titles,
                                                   List<double[]> xValues,
                                                   List<double[]> yValues)
    {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        int length = titles.length;                  //有几条线
        for (int i = 0; i < length; i++)
        {
            XYSeries series = new XYSeries(titles[i]);    //根据每条线的名称创建
            double[] xV = xValues.get(i);                 //获取第i条线的数据
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;                 //有几个点

            for (int k = 0; k < seriesLength; k++)        //每条线里有几个点
            {
                series.add(xV[k], yV[k]);
            }

            dataset.addSeries(series);
        }

        return dataset;
    }

    protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill)
    {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++)
        {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(fill);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title,
                                    String xTitle,String yTitle, double xMin,
                                    double xMax, double yMin, double yMax,
                                    int axesColor,int labelsColor)
    {
        renderer.setChartTitle(title);
        renderer.setChartTitleTextSize(80);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setPointSize(15);

        renderer.setXLabels(10);
        renderer.setYLabels(10);

        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
        renderer.setGridColor(labelsColor);
        renderer.setLabelsTextSize(50);

        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(0xff1ca8f4);
        renderer.setMarginsColor(0xff1ca8f4);
    }
}

