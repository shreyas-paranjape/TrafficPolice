package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.Vehicle;
import com.cybercad.challan.ui.adapter.VehicleAdapter;

import org.codepond.wizardroid.WizardStep;

import java.util.ArrayList;

public class VehicleSearch extends WizardStep {

    private static final String TAG = VehicleSearch.class.getName();

    private VehicleAdapter vehicleAdapter;
    private EditText vehicleNumberQuery;
    private ImageButton searchButton;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dummyData();//TODO remove this
        View v = initView(inflater, container);
        setListAdapter();
        setSearchClickListener();
        return v;
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.step_vehicle_search, container, false);
        vehicleNumberQuery = (EditText) v.findViewById(R.id.vehicle_query);
        searchButton = (ImageButton) v.findViewById(R.id.search_vehicles);
        listView = (ListView) v.findViewById(R.id.vehicles);
        return v;
    }

    private void setListAdapter() {
        vehicleAdapter = new VehicleAdapter(getActivity(), new ArrayList<Vehicle>());
        listView.setAdapter(vehicleAdapter);
    }

    private void setSearchClickListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = vehicleNumberQuery.getText().toString();
                if (!"".equalsIgnoreCase(query)) {
                    vehicleAdapter.clear();
                    vehicleAdapter.addAll(Vehicle.findByNumber(query));
                }
            }
        });
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                break;
            case WizardStep.EXIT_PREVIOUS:
                break;
        }
    }

    private void dummyData() {
        new Vehicle("GA05", "K", "7100", "Yamaha FZ-S", "BLUE").save();
        Log.d(TAG, "Vehicles" + Vehicle.getAll());
    }
}
