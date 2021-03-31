package com.example.chartextract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import static android.graphics.Color.BLUE;

public class ChartView extends View implements ChartModelListener {
    Paint paint;
    Bitmap bitmap;

    ChartModel chartModel;
    ChartInteractionModel iModel;
    ChartController controller;

    public ChartView(Context aContext) {
        super(aContext);
        paint = new Paint();
        setBackgroundColor(BLUE);
    }

    protected void onDraw(Canvas canvas) {
        try {
            Rect rectangle;
            rectangle = new Rect(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(bitmap, null, rectangle, paint);
        } catch (Exception e) {}
    }

    public void setBitmap(Bitmap newBitmap) {
        bitmap = newBitmap;
    }

    public void setChartModel(ChartModel newChartModel) { chartModel = newChartModel;}

    public void setIModel(ChartInteractionModel newIModel) {
        iModel = newIModel;
    }

    public void setController(ChartController newController) {
        controller = newController;
    }

    public void modelChanged() {
        this.invalidate();
    }
}
