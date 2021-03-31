package com.example.chartextract;

import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class ChartController implements View.OnTouchListener {
    ChartModel chartModel;
    ChartInteractionModel iModel;

    private enum State {READY, DRAGGING};
    private State currentState = State.READY;
    private float normX, normY;
    private float normDX, normDY;
    private float prevNormX, prevNormY;

    public ChartController() {
    }

    public boolean onTouch(View v, MotionEvent event) {
        normX = event.getX();
        normY = event.getY();
        
        normDX = normX / iModel.getViewWidth() - prevNormX;
        normDY = normY / iModel.getViewHeight() - prevNormY;

        prevNormX = normX;
        prevNormY = normY;

        switch (currentState) {
            case READY:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (iModel.checkHit(normX, normY)) {
                            Point point = iModel.findPoint(normX, normY);
                            iModel.setSelectedPoint(point);
                            currentState = State.DRAGGING;
                        }
                        else if (chartModel.checkHit(normX, normY)) {
                            Point point = chartModel.findPoint(normX, normY);
                            chartModel.setSelectedPoint(point);
                            currentState = State.DRAGGING;
                        }
                        else {
                            chartModel.addChartPoint((int) normX, (int) normY);
                            currentState = State.READY;
                        }
                        break;
                }
                break;
            case DRAGGING:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (chartModel.getSelectedPoint() != null) {
                            chartModel.movePoint(chartModel.getSelectedPoint(), (int) normX, (int) normY);
                        }
                        else if (iModel.getSelectedPoint() != null) {
                            iModel.movePoint(iModel.getSelectedPoint(), (int) normX, (int) normY);
                        }
                        currentState = State.DRAGGING;
                        break;
                    case MotionEvent.ACTION_UP:
                        iModel.setSelectedPoint(null);
                        chartModel.setSelectedPoint(null);
                        currentState = State.READY;
                }
                break;
        }
        return true;
    }

    public void setChartModel(ChartModel newModel) {
        chartModel = newModel;
    }

    public void setChartInteractionModel(ChartInteractionModel newIModel) {
        iModel = newIModel;
    }
}
