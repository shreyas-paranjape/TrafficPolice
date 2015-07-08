package com.cybercad.challan.ui.wizard.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybercad.challan.R;
import com.cybercad.challan.ui.wizard.step.AddOffence;
import com.cybercad.challan.ui.wizard.step.ConfirmOffences;
import com.cybercad.challan.ui.wizard.step.LicenceSearch;
import com.cybercad.challan.ui.wizard.step.VehicleLicenceInfo;
import com.cybercad.challan.ui.wizard.step.VehicleSearch;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardFragment;
import org.codepond.wizardroid.infrastructure.Bus;
import org.codepond.wizardroid.infrastructure.Subscriber;
import org.codepond.wizardroid.persistence.ContextManager;

import java.io.Serializable;
import java.util.Map;

public class IssueChallanWizardLayout extends WizardFragment implements Subscriber {

    private static final String TAG = IssueChallanWizardLayout.class.getName();

    public IssueChallanWizardLayout() {
        super();
    }

    public IssueChallanWizardLayout(ContextManager contextManager) {
        super(contextManager);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View wizardLayout = inflater.inflate(R.layout.wizard_layout, container, false);
        return wizardLayout;
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onWizardComplete() {
        super.onWizardComplete();
        Activity parent = getActivity();
        if (parent != null) {
            parent.finish();
        }
    }

    @Override
    public WizardFlow onSetup() {
        Bus.getInstance().register(this, WizardEvent.class);
        return new WizardFlow.Builder()
                .addStep(VehicleSearch.class, true)
                .addStep(LicenceSearch.class, true)
                .addStep(VehicleLicenceInfo.class)
                .addStep(AddOffence.class, true)
                .addStep(ConfirmOffences.class)
                        //.addStep(PrintChallan.class)
                .create();
    }

    @Override
    public void receive(Object o) {
        if (o instanceof WizardEvent) {
            WizardEvent event = (WizardEvent) o;
            switch (event.getType()) {
                case NEXT:
                    wizard.goNext();
                    break;
                case BACK:
                    wizard.goBack();
                    break;
            }
        }
    }


    public static class WizardEvent {
        private final Map<String, Serializable> payload;
        private final Type type;

        public static enum Type {NEXT, BACK}

        public WizardEvent(Map<String, Serializable> payload, Type type) {
            this.payload = payload;
            this.type = type;
        }

        public Map<String, Serializable> getPayload() {
            return payload;
        }

        public Type getType() {
            return type;
        }
    }

}
