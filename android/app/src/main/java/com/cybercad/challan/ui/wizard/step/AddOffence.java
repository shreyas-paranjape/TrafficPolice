package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.ui.adapter.OffenceTypeAdapter;
import com.cybercad.challan.util.CollectionUtil;

import org.codepond.wizardroid.WizardStep;

import java.util.List;

public class AddOffence extends WizardStep {

    private static final String TAG = AddOffence.class.getName();
    private GridView offencesGrid;
    private OffenceTypeAdapter offenceTypeAdapter;

    public AddOffence() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_add_offence, container, false);
        offencesGrid = (GridView) v.findViewById(R.id.offencesGrid);
        List<OffenceType> offenceTypes = OffenceType.getAll();
        Log.i(TAG, "Offence Types :" + CollectionUtil.toString(OffenceType.getAll()));
        offenceTypeAdapter = new OffenceTypeAdapter(getActivity(), offenceTypes);
        offencesGrid.setAdapter(offenceTypeAdapter);
        return v;
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
