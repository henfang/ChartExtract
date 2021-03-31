package com.example.chartextract;

public class ChartController {
    ChartModel chartModel;
    ChartInteractionModel chartIModel;

    public ChartController() {}

    public void setChartModel(ChartModel newModel) {
        chartModel = newModel;
    }

    public void setChartInteractionModel(ChartInteractionModel newIModel) {
        chartIModel = newIModel;
    }
}
