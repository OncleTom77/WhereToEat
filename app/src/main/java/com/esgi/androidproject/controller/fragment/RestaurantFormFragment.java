package com.esgi.androidproject.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.esgi.androidproject.R;

/**
 * Created by Vincent on 23/01/2017.
 */

public class RestaurantFormFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.restaurant_form, container, false);

        Button add = (Button) rootView.findViewById(R.id.add_restaurant_form);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }

        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
