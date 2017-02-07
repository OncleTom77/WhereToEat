package com.esgi.androidproject.widget;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.esgi.androidproject.R;
import com.esgi.androidproject.database.DAORestaurant;
import com.esgi.androidproject.model.Restaurant;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by thomasfouan on 07/02/2017.
 */

public class WhereToEatWidget extends AppWidgetProvider {

    private static final String UPDATE_ACTION = "UPDATE_WIDGET";
    private static final long LOCATION_REFRESH_TIME = 1000;
    private static final float LOCATION_REFRESH_DISTANCE = 0;

    public WhereToEatWidget() {
    }

    public static void configureWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Object valueFromConfiguration) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.buttonTop, (String) valueFromConfiguration);

        Intent intent = new Intent(context, WidgetConfigureActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.buttonBottom, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onEnabled(final Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        LocationManager mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Location lastLocation;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            lastLocation = null;
        } else {
            Log.d("WIDGET", "LOCATION LISTENER SET");
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, new LocationListener() {

                        @Override
                        public void onLocationChanged(Location location) {
                            updateDistanceWidget(context, location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
            lastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("WIDGET", lastLocation.toString());
        }

        // Set the listener on the button. When triggered, the onReceive method is called
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setOnClickPendingIntent(R.id.buttonTop, getPendingSelfIntent(context, UPDATE_ACTION, lastLocation));
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(UPDATE_ACTION.equals(intent.getAction())) {
            Location location = intent.getParcelableExtra("location");
            updateDistanceWidget(context, location);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action, Location lastLocation) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        intent.putExtra("location", lastLocation);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private void updateDistanceWidget(Context context, Location location) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        ComponentName watchWidget = new ComponentName(context, WhereToEatWidget.class);

        if(location != null) {
            Restaurant nearestRestaurant = getNearestRestaurant(context, location);
            float distance = getDistanceFromRestaurant(location, nearestRestaurant);

            remoteViews.setTextViewText(R.id.nearestRestaurant, nearestRestaurant.getName() + " : " + distance + " mètres restants");
        } else {
            remoteViews.setTextViewText(R.id.nearestRestaurant, "Position inconnue...");
        }

        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    private float getDistanceFromRestaurant(Location location, Restaurant restaurant) {

        Location resLocation = new Location("");
        resLocation.setLatitude(restaurant.getLatitude());
        resLocation.setLongitude(restaurant.getLongitude());

        return location.distanceTo(resLocation);
    }

    private Restaurant getNearestRestaurant(Context context, Location location) {

        DAORestaurant daoRestaurant = new DAORestaurant(context);
        List<Restaurant> restaurants = daoRestaurant.getRestaurants();
        daoRestaurant.close();

        Restaurant nearestRestaurant = null;
        float shortestDistance = Float.MAX_VALUE;

        for(Restaurant res : restaurants) {
            float newDistance = getDistanceFromRestaurant(location, res);

            if(newDistance < shortestDistance) {
                shortestDistance = newDistance;
                nearestRestaurant = res;
            }
        }

        return nearestRestaurant;
    }
}
