package com.esgi.androidproject.controller.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.MealListActivity;
import com.esgi.androidproject.database.DAORestaurant;
import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class ListPageFragment extends Fragment {

    public enum SortMode {
        ALPHABETICAL,
        CLOSEST,
        BEST,
        CHEAPEST
    }

    private EditText inputSearch;

    private ViewGroup viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.list, container, false);

        inputSearch = (EditText) rootView.findViewById(R.id.input_search);

        Spinner sortSpinner = (Spinner) rootView.findViewById(R.id.sort_spinner);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        updateList(SortMode.ALPHABETICAL);
                        break;

                    case 1:
                        updateList(SortMode.CLOSEST);
                        break;

                    case 2:
                        updateList(SortMode.BEST);
                        break;

                    case 3:
                        updateList(SortMode.CHEAPEST);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.viewGroup = rootView;
        updateList(SortMode.ALPHABETICAL);

        return rootView;
    }

    public void updateList(SortMode mode) {

        ListView listView = (ListView) viewGroup.findViewById(R.id.listRestaurants);

        DAORestaurant daoRestaurant = new DAORestaurant(getActivity());
        List<Restaurant> list = daoRestaurant.getRestaurants();
        daoRestaurant.close();

        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location lastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean milesUnit = sharedPreferences.getBoolean("milesUnit", false);

            // Update the distance and the distance unit in the restaurant
            for(Restaurant res : list) {
                res.setMilesUnit(milesUnit);
                res.setDistanceFromUser(lastLocation.distanceTo(res.getLocationFromLatLng()));
            }
        }

        switch (mode){
            case ALPHABETICAL:
                list = alphabeticalSort(list);
                break;
            case CLOSEST:
                list = closestSort(list);
                break;
            case BEST:
                list = bestSort(list);
                break;
            case CHEAPEST:
                list = cheapestSort(list);
                break;
        }

        final ArrayAdapter<Restaurant> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                list);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != "")
                    adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    public List<Restaurant> alphabeticalSort(List<Restaurant> list){
        Collections.sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });

        return list;
    }

    public List<Restaurant> closestSort(List<Restaurant> list){
        Collections.sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return r1.getDistanceFromUser() > r2.getDistanceFromUser() ? 1 : -1;
            }
        });

        return list;
    }

    public List<Restaurant> bestSort(List<Restaurant> list){
        Collections.sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return r1.getMark() >= r2.getMark() ? -1 : 1;
            }
        });

        return list;
    }

    public List<Restaurant> cheapestSort(List<Restaurant> list){
        Collections.sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                double price_r1 = getAverageMealPrice(r1);
                double price_r2 = getAverageMealPrice(r2);

                return price_r1 >= price_r2 ? 1 : -1;
            }
        });

        return list;
    }

    public double getAverageMealPrice(Restaurant r){
        double sum = 0;
        int number = 0;
        for(Meal m : r.getMeals()){
            sum += m.getPrice();
            number++;
        }

        return sum/number;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList(SortMode.ALPHABETICAL);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            updateList(SortMode.ALPHABETICAL);
        }
    }
}