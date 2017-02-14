package com.esgi.androidproject.controller.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.MealListActivity;
import com.esgi.androidproject.database.DAOMeal;
import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vincent on 27/01/2017.
 */

public class MealFormFragment extends Fragment {

    Restaurant restaurant;
    long restaurant_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.meal_form, container, false);

        MealListActivity mla = (MealListActivity) getActivity();
        restaurant = mla.getRestaurant();

        restaurant_id = restaurant.getId();

        System.out.println(restaurant.getName());

        Button submit = (Button) rootView.findViewById(R.id.add_meal_form);

        TextView meal_restaurant_title = (TextView) rootView.findViewById(R.id.restaurant_title);
        meal_restaurant_title.setText(restaurant.getName());

        TextView meal_title = (TextView) rootView.findViewById(R.id.meal_title);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(calendar.getTime());
        String title = "Repas du "+date;
        meal_title.setText(title);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(getName()) && getPrice() != -1.0 && "".equals(getComment())) {

                    Meal meal = new Meal();

                    meal.setIdRestaurant(restaurant_id);
                    meal.setDate(getDate());
                    meal.setName(getName());
                    meal.setPrice(getPrice());
                    meal.setComment(getComment());
                    meal.setMark(getMark());

                    DAOMeal daomeal = new DAOMeal(getActivity());
                    daomeal.insertMeal(meal);
                    daomeal.close();

                    //Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    //startActivity(intent);
                }
            }

            String getDate(){
                String title = ((TextView) rootView.findViewById(R.id.meal_title)).getText().toString();
                String date = title.substring("Repas du ".length(), title.length());
                return date;
            }

            String getName(){
                return ((EditText) rootView.findViewById(R.id.meal_name)).getText().toString();
            }

            Double getPrice(){
                String price = ((EditText) rootView.findViewById(R.id.meal_price)).getText().toString();
                if(!"".equals(price))
                    return Double.valueOf(price);
                else
                    return -1.0;
            }

            String getComment(){
                return ((EditText) rootView.findViewById(R.id.meal_description)).getText().toString();
            }

            int getMark(){
                return Integer.parseInt(((Spinner) rootView.findViewById(R.id.meal_mark_spinner)).getSelectedItem().toString());
            }

        });

        return rootView;
    }
}
