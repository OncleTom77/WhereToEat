package com.esgi.androidproject.controller.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.esgi.androidproject.R;

public class OptionPageFragment extends Fragment {

    Switch btnMapSwitch;
    Switch milesSwitch;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.option, container, false);

        Button btn = (Button) rootView.findViewById(R.id.saveOptionsBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOptions();
            }
        });

        this.btnMapSwitch = (Switch) rootView.findViewById(R.id.buttonMapSwitchOption);
        this.milesSwitch = (Switch) rootView.findViewById(R.id.milesSwitchOption);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean option1 = sharedPreferences.getBoolean("displayMapBtn", true);
        boolean option2 = sharedPreferences.getBoolean("milesUnit", false);

        btnMapSwitch.setChecked(option1);
        milesSwitch.setChecked(option2);

        return rootView;
    }

    public void saveOptions() {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("displayMapBtn", btnMapSwitch.isChecked());
        editor.putBoolean("milesUnit", milesSwitch.isChecked());
        editor.apply();

        Toast.makeText(getContext(), "Les options ont bien été enregistrées", Toast.LENGTH_SHORT).show();
    }
}