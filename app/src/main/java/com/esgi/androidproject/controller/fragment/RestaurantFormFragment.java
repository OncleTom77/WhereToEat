package com.esgi.androidproject.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.MainActivity;
import com.esgi.androidproject.database.DAORestaurant;
import com.esgi.androidproject.model.Restaurant;

/**
 * Created by Vincent on 23/01/2017.
 */
public class RestaurantFormFragment extends Fragment {

    private double latitude;

    private double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.restaurant_form, container, false);

        final Button add = (Button) rootView.findViewById(R.id.add_restaurant_form);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestaurant();

                getActivity().onBackPressed();
            }
        });

        this.latitude = getArguments().getDouble("lat");
        this.longitude = getArguments().getDouble("long");

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void addRestaurant() {

        EditText editText = (EditText) getActivity().findViewById(R.id.editRestaurantName);
        String name = editText.getText().toString().trim();

        if(!"".equals(name)) {

            Restaurant restaurant = new Restaurant();
            restaurant.setName(name);
            restaurant.setMark(4);
            restaurant.setLatitude(latitude);
            restaurant.setLongitude(longitude);

            DAORestaurant daoRestaurant = new DAORestaurant(getActivity());
            daoRestaurant.insertRestaurant(restaurant);
            daoRestaurant.close();

            ((MainActivity) getActivity()).updateMapFragment(this);
        }
    }
}
