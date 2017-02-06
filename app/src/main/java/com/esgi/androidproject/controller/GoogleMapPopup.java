package com.esgi.androidproject.controller;

import android.view.View;
import android.widget.TextView;

import com.esgi.androidproject.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomasfouan on 22/01/2017.
 */
public class GoogleMapPopup implements GoogleMap.InfoWindowAdapter {

    private View view;

    public GoogleMapPopup(View view) {
        this.view = view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView nameView = (TextView) view.findViewById(R.id.restaurantName);
        TextView markView = (TextView) view.findViewById(R.id.restaurantNote);
        String name;
        String mark;

        try {
            String jsonArgs = marker.getSnippet();
            JSONObject object = new JSONObject(jsonArgs);

            name = (String) object.get("name");
            mark = (String) object.get("mark");
        } catch (JSONException e) {
            name = marker.getTitle();
            mark = "☆☆☆☆☆";
        }

        if(name == null || name.equals("")) {
            name = "Döner Kebab";
            markView.setText("★★★★☆");
        }

        nameView.setText(name);
        markView.setText(mark);

        return view;
    }
}
