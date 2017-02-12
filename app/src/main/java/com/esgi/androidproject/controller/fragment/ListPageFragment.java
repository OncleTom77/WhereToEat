package com.esgi.androidproject.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.MealListActivity;
import com.esgi.androidproject.database.DAORestaurant;
import com.esgi.androidproject.model.Restaurant;

import java.util.List;

public class ListPageFragment extends Fragment {

    private ViewGroup viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.list, container, false);

        this.viewGroup = rootView;
        updateList();

        return rootView;
    }

    public void updateList() {

        ListView listView = (ListView) viewGroup.findViewById(R.id.listRestaurants);

        DAORestaurant daoRestaurant = new DAORestaurant(getActivity());
        List<Restaurant> list = daoRestaurant.getRestaurants();
        daoRestaurant.close();

        final ArrayAdapter<Restaurant> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = (Restaurant) parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), MealListActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("restaurant", restaurant);
                i.putExtras(b);
                startActivity(i);
            }
        });

        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            updateList();
        }
    }
}