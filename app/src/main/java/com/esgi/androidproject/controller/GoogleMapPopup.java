package com.esgi.androidproject.controller;

import android.view.View;
import android.widget.TextView;

import com.esgi.androidproject.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by thomasfouan on 22/01/2017.
 */
public class GoogleMapPopup implements GoogleMap.InfoWindowAdapter {

    View view;

    public GoogleMapPopup(View view) {
        this.view = view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView name = (TextView) view.findViewById(R.id.restaurantName);
        TextView note = (TextView) view.findViewById(R.id.restaurantNote);

        if(marker.getTitle() != null && !marker.getTitle().equals("")) {
            name.setText(marker.getTitle());
        } else {
            name.setText("Döner Kebab");
            note.setText("★★★★☆");
        }

        return view;
    }
}
