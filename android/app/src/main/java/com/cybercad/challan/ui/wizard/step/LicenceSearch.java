package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.person.Licensee;
import com.cybercad.challan.ui.adapter.LicenseeAdapter;
import com.cybercad.challan.ui.wizard.IssueChallanWizardLayout;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LicenceSearch extends WizardStep {

    private static final String TAG = LicenceSearch.class.getSimpleName();

    private LicenseeAdapter licenseeAdapter;
    private EditText licenceNumberQuery;
    private ImageButton searchButton;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = initView(inflater, container);
        setListAdapter();
        setListeners();
        Log.i(TAG, "Vehicle from cache : " + IssueChallanWizardLayout.get("vehicle"));
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

    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Serializable> payload = new HashMap<String, Serializable>();
                payload.put("licensee", licenseeAdapter.getItem(position));
                notifyCompleted();
                Bus.getInstance().post(new IssueChallanWizardLayout.WizardEvent(payload, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
            }
        });
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


}
