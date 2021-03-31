package com.example.chartextract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.CYAN;

public class DetailView extends View implements ChartListener {
    Paint paint;

    ChartModel chartModel;
    ChartInteractionModel iModel;
    ChartController controller;

    Rect sourceRect, destinationRect;
    float bitmapLeft, bitmapTop, bitmapRight, bitmapBottom;

    public DetailView(Context aContext) {
        super(aContext);
        paint = new Paint();
        setBackgroundColor(CYAN);
        try {
            sourceRect = new Rect(0,0, iModel.getChartBitmap().getWidth(), iModel.getChartBitmap().getHeight());
        } catch (Exception e) {}
        destinationRect = new Rect();
    }

    protected void onDraw(Canvas canvas) {
        try {
            canvas.drawBitmap(iModel.getChartBitmap(), sourceRect, destinationRect, paint);
            iModel.setViewSize(this.getWidth(), this.getHeight());
        } catch (Exception e) {}
        paint.setStrokeWidth(3);
        canvas.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight(), paint);
        canvas.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2, paint);
    }

    public void setChartModel(ChartModel newChartModel) { chartModel = newChartModel;}

    public void setIModel(ChartInteractionModel newIModel) {
        iModel = newIModel;
    }

    public void setController(ChartController newController) {
        controller = newController;
        this.setOnTouchListener(controller);
    }

    public void modelChanged() {
        try {
            bitmapLeft = -(iModel.getChartBitmap().getWidth());
            bitmapTop = -(iModel.getChartBitmap().getHeight());
            bitmapRight = iModel.getChartBitmap().getWidth();
            bitmapBottom = iModel.getChartBitmap().getHeight();
            System.out.println(bitmapLeft);
            System.out.println(bitmapTop);
            System.out.println(bitmapRight);
            System.out.println(bitmapBottom);
            System.out.println(chartModel.selectedPoint.x);
            System.out.println(chartModel.selectedPoint.y);
        } catch (Exception e) {}
        destinationRect.set((int) bitmapLeft, (int) bitmapTop, (int) bitmapRight, (int) bitmapBottom);

        this.invalidate();
    }
}
