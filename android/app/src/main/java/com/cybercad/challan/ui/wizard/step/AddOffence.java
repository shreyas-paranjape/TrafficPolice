package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.ui.adapter.OffenceTypeAdapter;
import com.cybercad.challan.ui.wizard.IssueChallanWizardLayout;
import com.cybercad.challan.util.CollectionUtil;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.util.List;

public class AddOffence extends WizardStep {

    private static final String TAG = AddOffence.class.getName();

    public AddOffence() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_add_offence, container, false);
        initGrid(v);
        Button nextButton = (Button) v.findViewById(R.id.btn_nxt_add_offence);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(
                        new IssueChallanWizardLayout.WizardEvent(
                                null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
            }
        });
        return v;
    }

    private void initGrid(View v) {
        List<OffenceType> offenceTypes = OffenceType.getAll();
        Log.d(TAG, "Offence Types :" + CollectionUtil.toString(offenceTypes));
        OffenceTypeAdapter offenceTypeAdapter = new OffenceTypeAdapter(getActivity(), offenceTypes);
        GridView offencesGrid = (GridView) v.findViewById(R.id.offencesGrid);
        offencesGrid.setAdapter(offenceTypeAdapter);
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
