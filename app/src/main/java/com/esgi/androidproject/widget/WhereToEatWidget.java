package com.esgi.androidproject.widget;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
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

    private static final String NEAREST_ACTION = "UPDATE_NEAREST_WIDGET";
    private static final String NEXT_ACTION = "UPDATE_NEXT_WIDGET";
    private static final long LOCATION_REFRESH_TIME = 1000;
    private static final float LOCATION_REFRESH_DISTANCE = 1;

    public WhereToEatWidget() {
    }

    /*
    public static void configureWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Object valueFromConfiguration) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.buttonTop, (String) valueFromConfiguration);

        Intent intent = new Intent(context, WidgetConfigureActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.buttonBottom, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }*/

    @Override
    public void onEnabled(final Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        LocationManager mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Restaurant restaurant;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            restaurant = null;
        } else {
            // Set a listener on the location changes
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, new LocationListener() {

                        @Override
                        public void onLocationChanged(Location location) {
                            updateDistanceWidget(context, location, null);
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
            Location lastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            restaurant = getNearestRestaurant(context, lastLocation);
        }

        // Set listeners on the buttons to get the nearest or the next nearest restaurant. When triggered, the onReceive method is called
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setOnClickPendingIntent(R.id.nearestBtn, getPendingSelfIntent(context, NEAREST_ACTION));
        remoteViews.setOnClickPendingIntent(R.id.nextBtn, getPendingSelfIntent(context, NEXT_ACTION));

        // Save the the nearest restaurant
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("idRes", (restaurant != null) ? restaurant.getId() : -1);
        editor.apply();

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(NEAREST_ACTION.equals(intent.getAction()) || NEXT_ACTION.equals(intent.getAction())) {

            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            Location lastLocation = null;
            Restaurant restaurant = null;

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (NEAREST_ACTION.equals(intent.getAction())) {
                    restaurant = getNearestRestaurant(context, lastLocation);
                } else {
                    restaurant = getNextNearestRestaurant(context, lastLocation);
                }

                // Save the the current restaurant
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("idRes", (restaurant != null) ? restaurant.getId() : -1);
                editor.apply();
            }

            updateDistanceWidget(context, lastLocation, restaurant);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        //intent.putExtra("restaurant", restaurant);
        //intent.putExtra("location", lastLocation);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private void updateDistanceWidget(Context context, Location location, Restaurant restaurant) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        ComponentName watchWidget = new ComponentName(context, WhereToEatWidget.class);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = "...";
        String remainingDistance = "Position inconnue...";

        if(location != null) {
            if(restaurant == null) {
                // Get the restaurant we want to update the distance from the SharedPreferences
                long idRes = sharedPreferences.getLong("idRes", -1);

                if(idRes > 0) {
                    DAORestaurant daoRestaurant = new DAORestaurant(context);
                    restaurant = daoRestaurant.getRestaurant(idRes);
                    daoRestaurant.close();
                }
            }

            if(restaurant != null) {
                float distance = getDistanceFromRestaurant(location, restaurant);
                boolean milesUnit = sharedPreferences.getBoolean("milesUnit", false);
                remainingDistance = getFormatDistance(distance, milesUnit);

                name = restaurant.getName();
            } else {
                remainingDistance = "Aucun restaurant enregistré...";
            }
        }

        remoteViews.setTextViewText(R.id.restaurantName, name);
        remoteViews.setTextViewText(R.id.distanceToRestaurant, remainingDistance);
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

    private Restaurant getNextNearestRestaurant(Context context, Location location) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long idRes = sharedPreferences.getLong("idRes", -1);

        if(idRes < 0) {
            return getNearestRestaurant(context, location);
        }

        DAORestaurant daoRestaurant = new DAORestaurant(context);
        List<Restaurant> restaurants = daoRestaurant.getRestaurants();
        Restaurant currentRestaurant = daoRestaurant.getRestaurant(idRes);
        daoRestaurant.close();

        Restaurant newRestaurant = null;
        float currentDistance = getDistanceFromRestaurant(location, currentRestaurant);
        float newDistance = Float.MAX_VALUE;

        for(Restaurant res : restaurants) {
            float distance = getDistanceFromRestaurant(location, res);

            if(distance < newDistance && distance > currentDistance) {
                newDistance = distance;
                newRestaurant = res;
            }
        }

        // If the current restaurant is the farthest one, return the nearest restaurant
        return (newRestaurant != null) ? newRestaurant : getNearestRestaurant(context, location);
    }

    public static String getFormatDistance(float distance, boolean milesUnit) {
        String result;

        if(distance < 0) {
            return "distance inconnue";
        }

        if(milesUnit) {
            distance *= 3.28084;
            if(distance > 5280) {
                result = distance/5280 + " miles";
            } else {
                result = distance + " pieds";
            }
        } else {
            if(distance > 1000) {
                result = distance/1000 + " kilomètres";
            } else {
                result = distance + " mètres";
            }
        }

        return result;
    }
}
