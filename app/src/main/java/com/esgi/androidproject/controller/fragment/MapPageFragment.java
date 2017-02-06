package com.esgi.androidproject.controller.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.JsonWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.esgi.androidproject.controller.GoogleMapPopup;
import com.esgi.androidproject.R;
import com.esgi.androidproject.database.DAORestaurant;
import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.esgi.androidproject.R.layout.map;

public class MapPageFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private SupportMapFragment mapFragment;

    private GoogleMap mMap;

    private GoogleApiClient googleApiClient;

    private boolean isAdding;

    private boolean isReady = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(map, container, false);

        Button btn = (Button) rootView.findViewById(R.id.addRestaurant);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdding = true;
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("ON RESUME !!");
        //if (mMap == null) {
          //  mMap = mapFragment.getMap();
            //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
        //}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        /*
        googleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
                */
    }

    private void configureGoogleMap() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.google_map_popup, null);
        UiSettings settings = mMap.getUiSettings();

        settings.setAllGesturesEnabled(true);
        settings.setMapToolbarEnabled(false);
        settings.setMyLocationButtonEnabled(true);
        settings.setCompassEnabled(true);
        settings.setZoomControlsEnabled(true);

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {

        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(googleApiClient.isConnected()) {
                    //search();
                }
                if(isAdding) {
                    //Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                    //marker.showInfoWindow();
                    //newRestaurantPos = latLng;

                    Bundle bundle = new Bundle();
                    bundle.putDouble("lat", latLng.latitude);
                    bundle.putDouble("long", latLng.longitude);

                    RestaurantFormFragment nextFrag = new RestaurantFormFragment();
                    nextFrag.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.map_layout, nextFrag, "Restaurant form")
                            .addToBackStack(null)
                            .commit();

                    isAdding = false;
                }
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMapPopup(view));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                long id;

                try {
                    String jsonArgs = marker.getSnippet();
                    JSONObject object = new JSONObject(jsonArgs);

                    id = object.getLong("id");
                } catch (JSONException e) {
                    id = -1;
                }

                // START ACTIVITY OF ALL MEALS OF THE RESTAURANT WITH ID = id
                if(id != -1) {

                }
            }
        });

        this.isAdding = false;
    }

    private void search() {

        PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(googleApiClient, "13 Rue du Docteur Finlay Paris", null, null);
        results.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(@NonNull AutocompletePredictionBuffer predictionsBuffer) {

                if(predictionsBuffer.getStatus().isSuccess()) {
                    for(AutocompletePrediction prediction : predictionsBuffer) {
                        String id = prediction.getPlaceId();
                        System.out.println("Id place : " + id);

                        PendingResult<PlaceBuffer> resultPlaces = Places.GeoDataApi.getPlaceById(googleApiClient, id);
                        resultPlaces.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(@NonNull PlaceBuffer placeBuffer) {
                                if(placeBuffer.getStatus().isSuccess()) {
                                    for(Place place : placeBuffer) {
                                        System.out.println("Address : " + place.getAddress());
                                        System.out.println("Name : " + place.getName());
                                        System.out.println("Phone Number : " + place.getPhoneNumber());
                                    }
                                } else {
                                    System.out.println("Erreur lors de la récupération des places !!");
                                    System.out.println(placeBuffer.getStatus().getStatusCode());
                                    System.out.println(placeBuffer.getStatus().getStatusMessage());
                                    placeBuffer.release();
                                }
                                DataBufferUtils.freezeAndClose(placeBuffer);
                            }
                        });
                    }
                } else {
                    System.out.println("Erreur lors de la récupération des prédictions !!");
                    System.out.println(predictionsBuffer.getStatus().getStatusCode());
                    System.out.println(predictionsBuffer.getStatus().getStatusMessage());
                    predictionsBuffer.release();
                }

                DataBufferUtils.freezeAndClose(predictionsBuffer);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        loadRestaurants();

        configureGoogleMap();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void loadRestaurants() {

        DAORestaurant daoRestaurant = new DAORestaurant(getActivity());

        List<Restaurant> restaurants = daoRestaurant.getRestaurants();
        LatLng latLng = null;

        for(Restaurant res : restaurants) {
            latLng = new LatLng(res.getLatitude(), res.getLongitude());

            String jsonArgs = "{"
                    + "id: " + res.getId()
                    + ", name: " + res.getName()
                    + ", mark: " + res.getStarsMark()
                    + "}";
            mMap.addMarker(new MarkerOptions().position(latLng).title(res.getName()).snippet(jsonArgs));
        }

        if(latLng != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        daoRestaurant.close();
    }
}