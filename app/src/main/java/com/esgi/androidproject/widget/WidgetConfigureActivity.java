package com.esgi.androidproject.widget;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.esgi.androidproject.R;

/**
 * Created by thomasfouan on 07/02/2017.
 */

public class WidgetConfigureActivity extends AppCompatActivity {

    private int widgetId;

    private static final int MY_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_widget_layout);

        setResult(RESULT_CANCELED);

        widgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("WIDGET CONFIGURATION", "NOT ALLOW !");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(MY_REQUEST_CODE == requestCode) {
            Log.d("WIDGET CONFIGURATION", "PERMISSION GRANTED BY THE USER");
        } else {
            Log.d("WIDGET CONFIGURATION", "PERMISSION NOT GRANTED BY THE USER");
        }
    }

    public void success(View view) {

        EditText editText = (EditText) findViewById(R.id.configureEditText);

        // GET Values from configuration view
        Object o = editText.getText().toString();

        // Send it to the widget
        //WhereToEatWidget.configureWidget(this, AppWidgetManager.getInstance(this), widgetId, o);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
