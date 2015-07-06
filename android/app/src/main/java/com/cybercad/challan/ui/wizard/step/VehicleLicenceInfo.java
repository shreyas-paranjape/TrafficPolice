package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.Licence.Licence;
import com.cybercad.challan.domain.Licence.LicenceVehicleClass;
import com.cybercad.challan.domain.Vehicle;
import com.cybercad.challan.domain.offence.Offence;
import com.cybercad.challan.domain.offence.VehicleOffence;
import com.cybercad.challan.domain.person.Licensee;
import com.cybercad.challan.domain.person.PersonalDetails;
import com.cybercad.challan.ui.wizard.IssueChallanWizardLayout;
import com.cybercad.challan.util.SystemUtil;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VehicleLicenceInfo extends WizardStep implements View.OnClickListener {

    private static final String TAG = VehicleLicenceInfo.class.getSimpleName();

    private Button nextButton;

    @ContextVariable
    private String vehicle;

    @ContextVariable
    private Licensee licensee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = initView(inflater, container);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        final View v = inflater.inflate(R.layout.step_vehicle_licence_info, container, false);
        nextButton = (Button) v.findViewById(R.id.wizard_next_button);
        nextButton.setOnClickListener(this);

        Vehicle selectedVehicle = new Vehicle("GA05", "K", "7100", "Yamaha FZ-S", "BLUE");//(Vehicle) IssueChallanWizardLayout.get("vehicle");
        //Licensee selectedLicensee = (Licensee) IssueChallanWizardLayout.get("licensee");

        Licence licence = new Licence("12345", new Date(), new Date());
        LicenceVehicleClass twoWheeler = new LicenceVehicleClass("TW", new Date(), "Two wheeler", licence);

        PersonalDetails personalDetails = new PersonalDetails("Shreyas", "Mahesh", "Paranjape", new Date());
        Licensee selectedLicensee = new Licensee(personalDetails, licence);

        if (selectedVehicle != null && selectedLicensee != null) {
            setFields(v, selectedVehicle, selectedLicensee);
        }
        return v;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.wizard_next_button) {
            Bus.getInstance().post(new IssueChallanWizardLayout.WizardEvent(null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
        }
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

    private void setFields(View row, Vehicle vehicle, Licensee licensee) {
        setVehicleNumber(row, vehicle);
        setVehicleMake(row, vehicle);
        setVehicleColor(row, vehicle);
        setVehicleOffences(row, vehicle.getOffenses());
        setName(row, licensee.getPersonalDetails());
        setBirthDate(row, licensee.getPersonalDetails());
        setLicenceNumber(row, licensee.getLicence());
        setLicenseeOffences(row, licensee.getOffences());
    }

    private void setLicenseeOffences(View row, List<Offence> offences) {

    }

    private void setVehicleOffences(View row, List<VehicleOffence> offenses) {

    }

    private void setVehicleColor(View row, Vehicle current) {
        SystemUtil.setTextViewText(row, R.id.vehicle_color, current.getColor());
    }

    private void setVehicleMake(View row, Vehicle current) {
        SystemUtil.setTextViewText(row, R.id.vehicle_make, current.getMake());
    }

    private void setVehicleNumber(View row, Vehicle current) {
        SystemUtil.setTextViewText(row, R.id.vehicle_number, current.getLicencePlateString());
    }

    private void setName(View row, PersonalDetails current) {
        SystemUtil.setTextViewText(row, R.id.licensee_name, current.getName());
    }

    private void setBirthDate(View row, PersonalDetails current) {
        String formattedBirthDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(current.getDateOfBirth());
        SystemUtil.setTextViewText(row, R.id.licensee_bdate, formattedBirthDate);
    }

    private void setLicenceNumber(View row, Licence current) {
        SystemUtil.setTextViewText(row, R.id.licensee_ls_no, current.getLicenceNumber());
    }

}
