package com.example.chartextract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ChartModel model;
    ChartInteractionModel iModel;
    ChartController controller;

    DetailView detailView;
    ChartView chartView;
    AxisView axisView;

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

        detailView = new DetailView(this);
        topRow.addView(detailView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        axisView = new AxisView(this);
        topRow.addView(axisView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        root.addView(topRow, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 4));

        chartView = new ChartView(this);
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
        model.setIModel(iModel);
        model.addSubscriber(chartView);
        model.addSubscriber(detailView);
        model.addSubscriber(axisView);
        iModel.addSubscriber(chartView);
        iModel.addSubscriber(detailView);
        iModel.addSubscriber(axisView);
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
            iModel.setChartBitmap(newBitmap);
        }
        else if (requestCode == 234 && resultCode == RESULT_OK) {
            Uri newFile = data.getData();
            try {
                ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(newFile, "w");
                FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
                Bitmap screen = Bitmap.createBitmap(chartView.getWidth(), chartView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screen);
                chartView.draw(canvas);
                screen.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == 345 && resultCode == RESULT_OK) {
            Uri textFile = data.getData();
            try {
                ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(textFile, "w");
                FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
                Bitmap screen = Bitmap.createBitmap(chartView.getWidth(), chartView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screen);
                chartView.draw(canvas);
                screen.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void chooseChart() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a chart"), 123);
    }

    public void exportData() {
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_CREATE_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 345);
    }

    public void screenshot() {
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("image/png")
                .putExtra(Intent.EXTRA_TITLE, "screenshot.png");
        startActivityForResult(intent2, 234);
    }


}
