package com.example.chartextract;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ChartModel {
    private Bitmap chartBitmap;

    ArrayList<ChartModelListener> subscribers;

    public ChartModel() {

    }

    public void setChartBitmap(Bitmap newBitmap) {
        chartBitmap = newBitmap;
        notifySubscribers();
    }

    public Bitmap getChartBitmap() {
        return chartBitmap;
    }

    public void addSubscriber(ChartModelListener subscriber) {
        subscribers.add(subscriber);
    }

    private void notifySubscribers() {
        for (ChartModelListener sub : subscribers) {
            sub.modelChanged();
        }
    }
}
