package com.example.chartextract;

import android.graphics.Point;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChartModel {
    ChartInteractionModel iModel;

    private ArrayList<ChartListener> subscribers;
    private int fatFinger;

    private ArrayList<Point> chartPoints;
    Point selectedPoint;
    float xScalePoint, yScalePoint;


    public ChartModel() {
        subscribers = new ArrayList<>();
        chartPoints = new ArrayList<>();
        fatFinger = 20;
    }

    public void setScaleCoordinates() {
        DecimalFormat df = new DecimalFormat("0.00");

        float xScaleLength = iModel.getTopRight().x-iModel.getBottomLeft().x;
        float yScaleLength = iModel.getBottomLeft().y-iModel.getTopRight().y;

        float xScaled = selectedPoint.x-iModel.getBottomLeft().x;
        float yScaled = iModel.getBottomLeft().y-selectedPoint.y;

        float xRelativeValue = ((xScaled/xScaleLength) * (iModel.getxEnd()-iModel.getxStart()) + iModel.getxStart());
        if (xRelativeValue > (iModel.getxEnd()+iModel.getxStart())) {
            xRelativeValue = iModel.getxEnd();
        }
        float yRelativeValue = ((yScaled/yScaleLength) * (iModel.getyEnd()-iModel.getyStart()) + iModel.getyStart());
        if (yRelativeValue > iModel.getyEnd()) {
            yRelativeValue = iModel.getyEnd();
        }

        xScalePoint = Float.parseFloat(df.format(xRelativeValue));
        yScalePoint = Float.parseFloat(df.format(yRelativeValue));
    }

    public boolean noPoints() {
        if (chartPoints.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean checkHit(float x, float y) {
        if (chartPoints.isEmpty()) {
            return false;
        }
        for (Point point : chartPoints) {
            if (x >= point.x - fatFinger && x <= point.x + fatFinger && y >= point.y - fatFinger && y <= point.y + fatFinger) {
                return true;
            }
        }
        return false;
    }

    public Point findPoint(float x, float y) {
        Point getPoint = null;
        for (Point point : chartPoints) {
            if (x >= point.x - fatFinger && x <= point.x + fatFinger && y >= point.y - fatFinger && y <= point.y + fatFinger) {
                getPoint = point;
            }
        }
        return getPoint;
    }

    public void addChartPoint(int x, int y) {
        Point newPoint = new Point();
        newPoint.x = x;
        newPoint.y = y;
        chartPoints.add(newPoint);
        setSelectedPoint(newPoint);
        notifySubscribers();
    }

    public void movePoint(Point point, int x, int y) {
        point.x = x;
        point.y = y;
        notifySubscribers();
    }

    public void setSelectedPoint(Point point) {
        selectedPoint = point;
        notifySubscribers();
    }

    public Point getSelectedPoint() {
        return selectedPoint;
    }

    public ArrayList<Point> getChartPoints() {
        return chartPoints;
    }

    public void setIModel(ChartInteractionModel newIModel) {
        iModel = newIModel;
    }

    public void addSubscriber(ChartListener subscriber) {
        subscribers.add(subscriber);
    }

    private void notifySubscribers() {
        for (ChartListener sub : subscribers) {
            sub.modelChanged();
        }
    }
}
