package com.cybercad.challan.ui.wizard;

import com.cybercad.challan.ui.wizard.step.AddOffence;
import com.cybercad.challan.ui.wizard.step.LicenceSearch;
import com.cybercad.challan.ui.wizard.step.PrintChallan;
import com.cybercad.challan.ui.wizard.step.VehicleLicenceInfo;
import com.cybercad.challan.ui.wizard.step.VehicleSearch;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

public class IssueChallanWizard extends BasicWizardLayout {
    public IssueChallanWizard() {
        super();
    }

    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(VehicleSearch.class)
                .addStep(LicenceSearch.class)
                .addStep(VehicleLicenceInfo.class)
                .addStep(AddOffence.class)
                .addStep(PrintChallan.class)
                .create();
    }

    @Override
    public void onWizardComplete() {
        super.onWizardComplete();   //Make sure to first call the super method before anything else
        //... Access context variables here before terminating the wizard
        //Store the data in the DB or pass it back to the calling activity
        getActivity().finish();     //Terminate the wizard
    }
}
