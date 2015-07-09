package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.ToggleButton;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.ui.adapter.OffenceTypeAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;
import com.cybercad.challan.util.CollectionUtil;
import com.google.common.collect.ImmutableList;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOffence extends WizardStep {

    private static final String TAG = AddOffence.class.getName();
    private ArrayAdapter<OffenceType> vehicleOffenceTypeAdapter;
    private ArrayAdapter<OffenceType> licenceOffenceTypeAdapter;
    private GridView vehicleOffencesGrid;
    private GridView licenceOffencesGrid;
    private TabHost offencesContainer;
    private final List<OffenceType> offencesSelected = new ArrayList<>();

    public AddOffence() {
        super();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_add_offence, container, false);
        initGrid(v);
        Button nextButton = (Button) v.findViewById(R.id.btn_nxt_add_offence);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> payload = new HashMap<>();
                Log.i(TAG, "" + CollectionUtil.toString(offencesSelected));
                payload.put("offences", ImmutableList.copyOf(offencesSelected));
                ObjectCache.put(payload);
                notifyCompleted();
                try {
                    Bus.getInstance().post(
                            new IssueChallanWizardLayout.WizardEvent(
                                    null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
                } catch (Exception e) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
        });
        return v;
    }

    private void initGrid(View v) {
        offencesContainer = (TabHost) v.findViewById(R.id.offencesContainer);
        offencesContainer.setup();
        offencesContainer.addTab(offencesContainer.newTabSpec("Licence_Offences")
                .setIndicator("Licence Offences").setContent(R.id.tabLicenceOffence));
        offencesContainer.addTab(offencesContainer.newTabSpec("Vehicle_Offences")
                .setIndicator("Vehicle Offences").setContent(R.id.tabVehicleOffence));

        List<OffenceType> vehicleOffences = OffenceType.getVehicleOffences();
        List<OffenceType> licenceOffences = OffenceType.getLicenceOffences();

        vehicleOffenceTypeAdapter = new ArrayAdapter<OffenceType>(getActivity(), R.layout.item_offence_type, vehicleOffences) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final OffenceType offenceType = getItem(position);
                ToggleButton button = new ToggleButton(getContext());
                button.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.toggle));
                button.setText(offenceType.getDescription());
                button.setTextOff(offenceType.getDescription());
                button.setTextOn(offenceType.getDescription());
                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            offencesSelected.add(offenceType);
                        } else {
                            offencesSelected.remove(offenceType);
                        }
                        Log.i(TAG, "Added offence. All : " + CollectionUtil.toString(offencesSelected));
                    }
                });
                return button;
            }
        };
        vehicleOffencesGrid = (GridView) v.findViewById(R.id.vehicleOffencesGrid);
        vehicleOffencesGrid.setAdapter(vehicleOffenceTypeAdapter);

        licenceOffenceTypeAdapter = new ArrayAdapter<OffenceType>(getActivity(), R.layout.item_offence_type, licenceOffences) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final OffenceType offenceType = getItem(position);
                ToggleButton button = new ToggleButton(getContext());
                button.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.toggle));
                button.setText(offenceType.getDescription());
                button.setTextOff(offenceType.getDescription());
                button.setTextOn(offenceType.getDescription());
                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            offencesSelected.add(offenceType);
                        } else {
                            offencesSelected.remove(offenceType);
                        }
                        Log.i(TAG, "" + CollectionUtil.toString(offencesSelected));
                    }
                });
                return button;
            }
        };
        licenceOffencesGrid = (GridView) v.findViewById(R.id.licenceOffencesGrid);
        licenceOffencesGrid.setAdapter(licenceOffenceTypeAdapter);
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
