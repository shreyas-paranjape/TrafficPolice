package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.ui.adapter.AddedOffenceAdapter;
import com.cybercad.challan.ui.adapter.AddedVehicleOffence;
import com.cybercad.challan.ui.adapter.VehicleOffenceAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;
import com.google.common.collect.ImmutableList;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;


public class ConfirmOffences extends WizardStep {

    private static final String TAG = ConfirmOffences.class.getName();
    private ListView vehicleOffencesView;
    private ListView licenceOffencesView;
    private TextView totalView;
    private ArrayAdapter<?> vehicleAdapter;
    private ArrayAdapter<?> licenceAdapter;

    @Override
    public void onResume() {
        super.onResume();
        List<LicenceOffence> licenceOffences = new ArrayList<>();
        List<VehicleOffence> vehicleOffences = new ArrayList<>();

        Licence licence = (Licence) ObjectCache.get("licence");
        if (licence != null) {
            List<LicenceOffence> prevLicOffence = LicenceOffence.getForLicence(licence);
        }
        Vehicle vehicle = (Vehicle) ObjectCache.get("vehicle");
        if (vehicle != null) {
            List<VehicleOffence> prevVehicleOffence = VehicleOffence.getForVehicle(vehicle);
        }
        ImmutableList<OffenceType> offences = (ImmutableList<OffenceType>) ObjectCache.get("offences");
        if (offences != null) {
            for (OffenceType offen : offences) {
                if (offen.isVehicleOffence()) {
                    vehicleOffences.add(new VehicleOffence(vehicle, new Date(), offen));
                } else {
                    licenceOffences.add(new LicenceOffence(licence, new Date(), offen));
                }
            }
            vehicleAdapter = new AddedVehicleOffence(getActivity(), vehicleOffences);
            vehicleOffencesView.setAdapter(vehicleAdapter);

            licenceAdapter = new AddedOffenceAdapter(getActivity(), licenceOffences);
            licenceOffencesView.setAdapter(licenceAdapter);

            Map<String, Object> vehOffCache = new HashMap<>();
            vehOffCache.put("vehicle_offences", vehicleOffences);
            ObjectCache.put(vehOffCache);

            Map<String, Object> licOffCache = new HashMap<>();
            licOffCache.put("licence_offences", licenceOffences);
            ObjectCache.put(licOffCache);

            long total = 0;
            for (VehicleOffence off : vehicleOffences) {
                total += off.getPenalty();
            }

            for (LicenceOffence off : licenceOffences) {
                total += off.getPenalty();
            }
            totalView.setText(Long.valueOf(total).toString());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_confirm, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        vehicleOffencesView = (ListView) v.findViewById(R.id.lv_confirm_veh_offences);
        licenceOffencesView = (ListView) v.findViewById(R.id.lv_confirm_lic_offences);
        totalView = (TextView) v.findViewById(R.id.tv_confirm_total);
        Button nextButton = (Button) v.findViewById(R.id.btn_confirm_nxt);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
