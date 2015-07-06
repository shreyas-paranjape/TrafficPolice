package com.cybercad.challan.ui.wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybercad.challan.R;
import com.cybercad.challan.ui.wizard.step.AddOffence;
import com.cybercad.challan.ui.wizard.step.LicenceSearch;
import com.cybercad.challan.ui.wizard.step.PrintChallan;
import com.cybercad.challan.ui.wizard.step.VehicleLicenceInfo;
import com.cybercad.challan.ui.wizard.step.VehicleSearch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardFragment;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;
import org.codepond.wizardroid.infrastructure.Subscriber;
import org.codepond.wizardroid.persistence.ContextManager;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class IssueChallanWizardLayout extends WizardFragment implements Subscriber {

    private static final String TAG = IssueChallanWizardLayout.class.getName();

    public IssueChallanWizardLayout() {
        super();
    }

    public IssueChallanWizardLayout(ContextManager contextManager) {
        super(contextManager);
    }

    private static final Cache<String, Serializable> cache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(1000)
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View wizardLayout = inflater.inflate(R.layout.wizard_layout, container, false);
        return wizardLayout;
    }


    public void onWizardComplete() {
        super.onWizardComplete();
        cache.invalidateAll();
        cache.cleanUp();
    }

    @Override
    public WizardFlow onSetup() {
        Bus.getInstance().register(this, WizardEvent.class);
        return new WizardFlow.Builder()
                //.addStep(VehicleSearch.class)
                //.addStep(LicenceSearch.class)
                //.addStep(VehicleLicenceInfo.class)
                .addStep(AddOffence.class)
                .addStep(PrintChallan.class)
                .create();
    }

    @Override
    public void receive(Object o) {
        if (o instanceof WizardEvent) {
            WizardEvent event = (WizardEvent) o;
            if (event.getPayload() != null) {
                cache.putAll(event.getPayload());
            }
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

    public static Serializable get(String key) {
        return cache.getIfPresent(key);
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
