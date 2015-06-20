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
import com.cybercad.challan.domain.Licence.Licence;
import com.cybercad.challan.domain.Licence.LicenceVehicleClass;
import com.cybercad.challan.domain.offence.Offence;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.domain.person.Licensee;
import com.cybercad.challan.domain.person.PersonalDetails;
import com.cybercad.challan.ui.adapter.LicenseeAdapter;
import com.cybercad.challan.util.CollectionUtil;

import org.codepond.wizardroid.WizardStep;

import java.util.ArrayList;
import java.util.Date;

public class LicenceSearch extends WizardStep {

    private static final String TAG = LicenceSearch.class.getName();

    private LicenseeAdapter licenseeAdapter;
    private EditText licenceNumberQuery;
    private ImageButton searchButton;
    private ListView listView;

    public LicenceSearch() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dummyData();// TODO remove this
        View v = initView(inflater, container);
        setListAdapter();
        setSearchClickListener();
        return v;
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        final View v = inflater.inflate(R.layout.step_licence_search, container, false);
        listView = (ListView) v.findViewById(R.id.licensees);
        searchButton = (ImageButton) v.findViewById(R.id.search_licensees);
        licenceNumberQuery = (EditText) v.findViewById(R.id.licensee_query);
        return v;
    }

    private void setListAdapter() {
        licenseeAdapter = new LicenseeAdapter(getActivity(), new ArrayList<Licensee>());
        listView.setAdapter(licenseeAdapter);
    }

    private void setSearchClickListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = licenceNumberQuery.getText().toString();
                if (!"".equalsIgnoreCase(query)) {
                    licenseeAdapter.clear();
                    licenseeAdapter.addAll(Licensee.searchByLicence(query));
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
        OffenceType noHelmet = new OffenceType("NH", "No helmet", 500);
        noHelmet.save();

        Licence licence = new Licence("12345", new Date(), new Date());
        LicenceVehicleClass twoWheeler = new LicenceVehicleClass("TW", new Date(), "Two wheeler", licence);

        PersonalDetails personalDetails = new PersonalDetails("Shreyas", "Mahesh", "Paranjape", new Date());
        Licensee shrep = new Licensee(personalDetails, licence);

        Offence shrepNoHelmet = new Offence(shrep, new Date(), OffenceType.searchByCode("NH"));

        shrep.save();
        shrep.addOffence(shrepNoHelmet);
        twoWheeler.save();

        Log.d(TAG, "Offence Types :" + CollectionUtil.toString(OffenceType.getAll()));
        Log.d(TAG, "licensees :" + CollectionUtil.toString(Licensee.getAll()));
        Log.d(TAG, "Offences :" + CollectionUtil.toString(Offence.getAll()));
    }
}
