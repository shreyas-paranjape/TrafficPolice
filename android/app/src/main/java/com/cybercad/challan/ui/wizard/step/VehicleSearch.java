package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.ui.adapter.VehicleAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VehicleSearch extends WizardStep {

    private static final String TAG = VehicleSearch.class.getSimpleName();

    private VehicleAdapter vehicleAdapter;
    private EditText vehicleNumberQuery;
    private ImageButton searchButton;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = initView(inflater, container);
        setListAdapter();
        setListeners();
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

    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("vehicle", vehicleAdapter.getItem(position));
                ObjectCache.put(payload);
                notifyCompleted();
                try {
                    Bus.getInstance().post(new IssueChallanWizardLayout.WizardEvent(
                            null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
                } catch (Exception e) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
        });
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

}
