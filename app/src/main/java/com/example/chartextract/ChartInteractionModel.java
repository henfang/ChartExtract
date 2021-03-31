package com.example.chartextract;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

public class ChartInteractionModel {
    private Bitmap chartBitmap;
    private Point topLeft, topRight, bottomLeft, bottomRight;
    private Point selectedPoint;
    private int yStart, yEnd, xStart, xEnd;
    private int fatFinger;
    private float viewWidth, viewHeight;
    float normViewLeft, normViewTop;
    float scaleFactor;

    private ArrayList<ChartListener> subscribers;

    public ChartInteractionModel() {
        subscribers = new ArrayList<>();
        topLeft = new Point();
        topLeft.x = 20;
        topLeft.y = 20;
        topRight = new Point();
        topRight.x = 200;
        topRight.y = 20;
        bottomLeft = new Point();
        bottomLeft.x = 20;
        bottomLeft.y = 400;
        bottomRight = new Point();
        bottomRight.x = 200;
        bottomRight.y = 400;
        fatFinger = 100;

        yStart = 0;
        yEnd = 100;
        xStart = 0;
        xEnd = 100;

        scaleFactor = 2.0f;
    }

    public void setViewSize(float width, float height) {
        viewWidth = width;
        viewHeight = height;
        normViewLeft = 0.0f;
        normViewTop = 0.0f;
    }

    public void moveViewport(float dx, float dy) {
        normViewLeft += dx / scaleFactor;
        normViewTop += dy / scaleFactor;
    }

    public boolean checkHit(float x, float y) {
        if (x >= topLeft.x - fatFinger && x <= topLeft.x + fatFinger && y >= topLeft.y - fatFinger && y <= topLeft.y + fatFinger) {
            return true;
        }
        else if (x >= topRight.x - fatFinger && x <= topRight.x + fatFinger && y >= topRight.y - fatFinger && y <= topRight.y + fatFinger) {
            return true;
        }
        else if (x >= bottomLeft.x - fatFinger && x <= bottomLeft.x + fatFinger && y >= bottomLeft.y - fatFinger && y <= bottomLeft.y + fatFinger) {
            return true;
        }
        else if (x >= bottomRight.x - fatFinger && x <= bottomRight.x + fatFinger && y >= bottomRight.y - fatFinger && y <= bottomRight.y + fatFinger) {
            return true;
        }
        return false;
    }

    public Point findPoint(float x, float y) {
        if (x >= topLeft.x - fatFinger && x <= topLeft.x + fatFinger && y >= topLeft.y - fatFinger && y <= topLeft.y + fatFinger) {
            return topLeft;
        }
        else if (x >= topRight.x - fatFinger && x <= topRight.x + fatFinger && y >= topRight.y - fatFinger && y <= topRight.y + fatFinger) {
            return topRight;
        }
        else if (x >= bottomLeft.x - fatFinger && x <= bottomLeft.x + fatFinger && y >= bottomLeft.y - fatFinger && y <= bottomLeft.y + fatFinger) {
            return bottomLeft;
        }
        else {
            return bottomRight;
        }
    }

    public void setSelectedPoint(Point point) {
        selectedPoint = point;
    }

    public Point getSelectedPoint() {
        return selectedPoint;
    }

    public void movePoint(Point point, int x, int y) {
        point.x = x;
        point.y = y;
        if (point.equals(topLeft)) {
            topRight.y = y;
            bottomLeft.x = x;
        }
        else if (point.equals(topRight)) {
            topLeft.y = y;
            bottomRight.x = x;
        }
        else if (point.equals(bottomLeft)) {
            topLeft.x = x;
            bottomRight.y = y;
        }
        else {
            topRight.x = x;
            bottomLeft.y = y;
        }
        notifySubscribers();
    }

    public void setChartBitmap(Bitmap newBitmap) {
        chartBitmap = newBitmap;
        notifySubscribers();
    }

    public Bitmap getChartBitmap() {
        return chartBitmap;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getTopRight() {
        return topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public float getViewWidth() {
        return viewWidth;
    }

    public float getViewHeight() {
        return viewHeight;
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
