package com.example.chartextract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
//    ChartView chartView;
//    DetailView detailView;
//    AxisView axisView;

    ChartModel model;
    ChartInteractionModel iModel;
    ChartController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ChartModel();
        iModel = new ChartInteractionModel();
        controller = new ChartController();

        setContentView(R.layout.activity_main);

        // Build main view
        LinearLayout root = findViewById(R.id.root);
        root.setOrientation(LinearLayout.VERTICAL);

        LinearLayout topRow = new LinearLayout(this);
        topRow.setOrientation(LinearLayout.HORIZONTAL);

        DetailView detailView = new DetailView(this);
        topRow.addView(detailView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        AxisView axisView = new AxisView(this);
        topRow.addView(axisView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        root.addView(topRow, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 4));

        ChartView chartView = new ChartView(this);
        root.addView(chartView, new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1)));

        // Connect MVC components
        controller.setChartModel(model);
        controller.setChartInteractionModel(iModel);
        chartView.setChartModel(model);
        detailView.setChartModel(model);
        axisView.setChartModel(model);
        chartView.setIModel(iModel);
        detailView.setIModel(iModel);
        axisView.setIModel(iModel);
        model.addSubscriber(chartView);
        model.addSubscriber(detailView);
        model.addSubscriber(axisView);
        chartView.setController(controller);
        detailView.setController(controller);
        axisView.setController(controller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choose_chart:
                chooseChart();
                return true;
            case R.id.export_data:
                exportData();
                return true;
            case R.id.screenshot:
                screenshot();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Bitmap newBitmap = null;
            try {
                newBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (Exception e) {
                return;
            }
            model.setChartBitmap(newBitmap);
//            chartView.setBitmap(newBitmap);
//            chartView.invalidate();
//            detailView.setBitmap(newBitmap);
//            detailView.invalidate();
        }

    }

    public void chooseChart() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a chart"), 123);
    }

    public void exportData() {

    }

    public void screenshot() {

    }


}
