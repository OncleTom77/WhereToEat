package com.esgi.androidproject.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.androidproject.R;

/**
 * Created by Vincent on 27/01/2017.
 */

public class MealFormFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.meal_form, container, false);

        return rootView;
    }
}
