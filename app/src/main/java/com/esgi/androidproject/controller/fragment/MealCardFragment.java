package com.esgi.androidproject.controller.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.MealListActivity;
import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;

import org.w3c.dom.Text;

/**
 * Created by Vincent on 27/01/2017.
 */

public class MealCardFragment extends Fragment {

    Restaurant restaurant;
    Meal meal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.meal_card, container, false);

        MealListActivity mla = (MealListActivity) getActivity();
        int givenMeal = mla.getGivenMeal();
        restaurant = mla.getRestaurant();
        meal = restaurant.getMeals().get(givenMeal);

        TextView mealRestaurantTitle = (TextView) rootView.findViewById(R.id.restaurant_title);
        mealRestaurantTitle.setText(restaurant.getName());

        TextView mealTitle = (TextView) rootView.findViewById(R.id.meal_title);
        String title = "Repas du "+ meal.getDate();
        mealTitle.setText(title);

        TextView mealName = (TextView) rootView.findViewById(R.id.meal_name);
        mealName.setText(meal.getName());

        TextView mealPrice = (TextView) rootView.findViewById(R.id.meal_price);
        mealPrice.setText(Double.toString(meal.getPrice()));

        TextView mealDescription = (TextView) rootView.findViewById(R.id.meal_description);
        mealDescription.setText(meal.getComment());

        TextView mealMark = (TextView) rootView.findViewById(R.id.meal_mark);
        String mark = "";
        for(int i=1; i<=5; i++) {
            mark += (meal.getMark() >= i) ? "★" : "☆";
        }
        mealMark.setText(mark);
        
        return rootView;
    }
}
