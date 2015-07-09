package com.cybercad.challan.ui.wizard.step;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.ui.adapter.LicenceAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LicenceSearch extends WizardStep {

    private static final String TAG = LicenceSearch.class.getSimpleName();

    private LicenceAdapter licenceAdapter;
    private EditText licenceNumberQuery;
    private ImageButton searchButton;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = initView(inflater, container);
        setListAdapter();
        setListeners();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(licenceNumberQuery, 0);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        final View v = inflater.inflate(R.layout.step_licence_search, container, false);
        listView = (ListView) v.findViewById(R.id.licensees);
        listView.setEmptyView(v.findViewById(R.id.licences_empty));
        searchButton = (ImageButton) v.findViewById(R.id.search_licensees);
        licenceNumberQuery = (EditText) v.findViewById(R.id.licensee_query);
        return v;
    }

    private void setListAdapter() {
        licenceAdapter = new LicenceAdapter(getActivity(), new ArrayList<Licence>());
        listView.setAdapter(licenceAdapter);
    }

    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("licence", licenceAdapter.getItem(position));
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
                String query = licenceNumberQuery.getText().toString();
                if (!"".equalsIgnoreCase(query)) {
                    licenceAdapter.clear();
                    licenceAdapter.addAll(Licence.searchByNumber(query));
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
