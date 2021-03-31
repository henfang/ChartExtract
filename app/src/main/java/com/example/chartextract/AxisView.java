package com.example.chartextract;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.LTGRAY;

public class AxisView extends LinearLayout implements ChartListener {
    ChartModel chartModel;
    ChartInteractionModel iModel;
    ChartController controller;

    EditText yStart, yEnd, xStart, xEnd;
    TextView coords;

    public AxisView(Context aContext) {
        super(aContext);

        LinearLayout root = new LinearLayout(aContext);
        root.setOrientation(LinearLayout.VERTICAL);

        // Row 1
        LinearLayout row1 = new LinearLayout(aContext);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        row1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView yAxis = new TextView(aContext);
        yAxis.setText("Y Axis");

        yStart = new EditText(aContext);
        yStart.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        yStart.setGravity(Gravity.CENTER_HORIZONTAL);
        yStart.setInputType(InputType.TYPE_CLASS_NUMBER);
        yStart.setText("0", TextView.BufferType.EDITABLE);


        TextView row1to = new TextView(aContext);
        row1to.setText("to");

        yEnd = new EditText(aContext);
        yEnd.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        yEnd.setGravity(Gravity.CENTER_HORIZONTAL);
        yEnd.setInputType(InputType.TYPE_CLASS_NUMBER);
        yEnd.setText("100", TextView.BufferType.EDITABLE);

        row1.addView(yAxis);
        row1.addView(yStart);
        row1.addView(row1to);
        row1.addView(yEnd);
        row1.setBackgroundColor(LTGRAY);

        // Row 2
        LinearLayout row2 = new LinearLayout(aContext);
        row2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView xAxis = new TextView(aContext);
        xAxis.setText("X Axis");

        xStart = new EditText(aContext);
        xStart.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        xStart.setGravity(Gravity.CENTER_HORIZONTAL);
        xStart.setInputType(InputType.TYPE_CLASS_NUMBER);
        xStart.setText("0", TextView.BufferType.EDITABLE);

        TextView row2to = new TextView(aContext);
        row2to.setText("to");

        xEnd = new EditText(aContext);
        xEnd.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        xEnd.setGravity(Gravity.CENTER_HORIZONTAL);
        xEnd.setInputType(InputType.TYPE_CLASS_NUMBER);
        xEnd.setText("100", TextView.BufferType.EDITABLE);

        row2.addView(xAxis);
        row2.addView(xStart);
        row2.addView(row2to);
        row2.addView(xEnd);
        row2.setBackgroundColor(LTGRAY);

        // Coordinate row
        LinearLayout row3 = new LinearLayout(aContext);
        row3.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        coords = new TextView(aContext);
        coords.setText("0,0");
        coords.setTextSize(30);

        row3.addView(coords);
        row3.setGravity(Gravity.CENTER_HORIZONTAL);
        row3.setBackgroundColor(GRAY);


        root.addView(row1);
        root.addView(row2);
        root.addView(row3);
        root.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.setBackgroundColor(LTGRAY);
        this.setPadding(10,5,5,5);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(root);

    }

    public void setChartModel(ChartModel newChartModel) { chartModel = newChartModel;}

    public void setIModel(ChartInteractionModel newIModel) {
        iModel = newIModel;
    }

    public void setController(ChartController newController) {
        controller = newController;
    }

    @Override
    public void modelChanged() {
        try {
            iModel.setyStart(Integer.parseInt(yStart.getText().toString()));
            iModel.setyEnd(Integer.parseInt(yEnd.getText().toString()));
            iModel.setxStart(Integer.parseInt(xStart.getText().toString()));
            iModel.setxEnd(Integer.parseInt(xEnd.getText().toString()));
            chartModel.setScaleCoordinates();
            coords.setText(chartModel.xScalePoint + "," + chartModel.yScalePoint);
        } catch (Exception e) {}
        this.invalidate();
    }
}
