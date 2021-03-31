package com.example.chartextract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.YELLOW;

public class ChartView extends View implements ChartListener {
    Paint paint;

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
            Rect rectangle = new Rect(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(iModel.getChartBitmap(), null, rectangle, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(RED);
            canvas.drawRect(iModel.getTopLeft().x, iModel.getTopLeft().y, iModel.getBottomRight().x, iModel.getBottomRight().y, paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(iModel.getTopLeft().x, iModel.getTopLeft().y, 10, paint);
            canvas.drawCircle(iModel.getTopRight().x, iModel.getTopRight().y, 10, paint);
            canvas.drawCircle(iModel.getBottomLeft().x, iModel.getBottomRight().y, 10, paint);
            canvas.drawCircle(iModel.getBottomRight().x, iModel.getBottomRight().y, 10, paint);

            if (!chartModel.noPoints()) {
                for (Point point : chartModel.getChartPoints()) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(YELLOW);
                    canvas.drawCircle(point.x, point.y, 10, paint);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    paint.setColor(BLACK);
                    canvas.drawCircle(point.x, point.y, 10, paint);
                }
            }
        } catch (Exception e) {}
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
        this.invalidate();
    }
}
