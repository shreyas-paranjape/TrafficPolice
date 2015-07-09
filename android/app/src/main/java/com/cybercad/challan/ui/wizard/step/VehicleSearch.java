package com.cybercad.challan.ui.wizard.step;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VehicleSearch extends WizardStep {

    private static final String TAG = VehicleSearch.class.getSimpleName();

    private VehicleAdapter vehicleAdapter;
    private EditText vehicleNumberQuery;
    private EditText vehicleStateCodeQuery;
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
        vehicleNumberQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    searchButton.callOnClick();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(vehicleNumberQuery.getWindowToken(), 0);
                }
            }
        });
        vehicleStateCodeQuery = (EditText) v.findViewById(R.id.vehicle_statecode);
        vehicleStateCodeQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    vehicleNumberQuery.requestFocus();
                }
            }
        });

        vehicleStateCodeQuery.requestFocus();
        listView = (ListView) v.findViewById(R.id.vehicles);
        listView.setEmptyView(v.findViewById(R.id.vehicles_empty));
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
                String stateCode = vehicleStateCodeQuery.getText().toString();
                String number = vehicleNumberQuery.getText().toString();
                if (!"".equalsIgnoreCase(number)) {
                    vehicleAdapter.clear();
                    vehicleAdapter.addAll(Vehicle.findByNumber(stateCode, number));
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
