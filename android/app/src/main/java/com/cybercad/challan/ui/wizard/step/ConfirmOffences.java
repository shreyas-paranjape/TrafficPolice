package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.ui.adapter.AddedOffenceAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;
import com.cybercad.challan.util.CollectionUtil;
import com.google.common.collect.ImmutableList;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class ConfirmOffences extends WizardStep {

    private static final String TAG = ConfirmOffences.class.getName();
    private ListView offencesView;
    private AddedOffenceAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        List<LicenceOffence> licenceOffences = new ArrayList<>();

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
                    VehicleOffence vehicleOffence = new VehicleOffence(vehicle, new Date(), offen);
                } else {
                    licenceOffences.add(new LicenceOffence(licence, new Date(), offen).calculateRepetetion());
                }
            }
            Log.i(TAG, "" + CollectionUtil.toString(licenceOffences));
            adapter = new AddedOffenceAdapter(getActivity(), licenceOffences);
            offencesView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_confirm, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        offencesView = (ListView) v.findViewById(R.id.lv_confirm_offences);

        Button nextButton = (Button) v.findViewById(R.id.btn_confirm_nxt);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bus.getInstance().post(
                            new IssueChallanWizardLayout.WizardEvent(
                                    null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
                } catch (Exception e) {
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
