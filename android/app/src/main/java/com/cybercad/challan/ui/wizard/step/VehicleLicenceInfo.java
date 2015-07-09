package com.cybercad.challan.ui.wizard.step;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.ui.adapter.OffenceAdapter;
import com.cybercad.challan.ui.adapter.VehicleOffenceAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VehicleLicenceInfo extends WizardStep {

    private static final String TAG = VehicleLicenceInfo.class.getSimpleName();

    private ListView licenseeOffencesView;
    private TextView licenseeName;
    private TextView licenseeBirthDate;
    private TextView licenceNumber;
    private ListView vehicleOffencesView;
    private TextView vehicleColor;
    private TextView vehicleMake;
    private TextView vehicleNumber;
    private ImageView picture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = initView(inflater, container);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Licence licence = (Licence) ObjectCache.get("licence");
        if (licence != null) {
            licenceNumber.setText(licence.getLicenceNumber());
            licenseeName.setText(licence.getLicensee().getPersonalDetails().getName());
            String formattedBirthDate = new SimpleDateFormat("dd-MM-yyyy",
                    Locale.getDefault()).format(licence.getLicensee().getPersonalDetails().getDateOfBirth());
            licenseeBirthDate.setText(formattedBirthDate);
            OffenceAdapter offenceAdapter = new OffenceAdapter(getActivity(), LicenceOffence.getForLicence(licence));
            licenseeOffencesView.setAdapter(offenceAdapter);
        }
        Vehicle vehicle = (Vehicle) ObjectCache.get("vehicle");
        if (vehicle != null) {
            vehicleNumber.setText(vehicle.getNumberPlateString());
            vehicleColor.setText(vehicle.getColor());
            vehicleMake.setText(vehicle.getMake());
            VehicleOffenceAdapter vehicleOffenceAdapter = new VehicleOffenceAdapter(getActivity(), VehicleOffence.getForVehicle(vehicle));
            vehicleOffencesView.setAdapter(vehicleOffenceAdapter);
        }
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        final View v = inflater.inflate(R.layout.step_vehicle_licence_info, container, false);
        licenseeOffencesView = (ListView) v.findViewById(R.id.licensee_offence);
        licenceNumber = (TextView) v.findViewById(R.id.licensee_ls_no);
        licenseeBirthDate = (TextView) v.findViewById(R.id.licensee_bdate);
        licenseeName = (TextView) v.findViewById(R.id.licensee_name);
        vehicleOffencesView = (ListView) v.findViewById(R.id.vehicle_Offences);
        vehicleColor = (TextView) v.findViewById(R.id.vehicle_color);
        vehicleMake = (TextView) v.findViewById(R.id.vehicle_make);
        vehicleNumber = (TextView) v.findViewById(R.id.vehicle_number);
        picture = (ImageView) v.findViewById(R.id.licensee_image);
        picture.setImageBitmap(getBitmapFromAssets("pic.png"));

        Button nextButton = (Button) v.findViewById(R.id.wizard_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bus.getInstance().post(
                            new IssueChallanWizardLayout.WizardEvent(
                                    null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
                } catch (Exception e) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
        });
        return v;
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        try {
            AssetManager assetManager = getActivity().getAssets();

            InputStream istr = assetManager.open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(istr);

            return bitmap;
        } catch (Exception e) {
            return null;
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

    /*private void setFields(View row, Vehicle vehicle, Licensee licensee) {
        //setVehicleNumber(row, vehicle);
        setVehicleMake(row, vehicle);
        setVehicleColor(row, vehicle);
        //setVehicleOffences(row, vehicle.getOffenses());
        //setName(row, licensee.getPersonalDetails());
        //setBirthDate(row, licensee.getPersonalDetails());
        //setLicenceNumber(row, licensee.getLicence());
        //setLicenseeOffences(row, licensee.getOffences());
    }

    private void setLicenseeOffences(View row, List<LicenceOffence> licenseeOffences) {
        ListView licenseeOffencesView = (ListView) row.findViewById(R.id.licensee_offence);
        licenseeOffencesView.setAdapter(new OffenceAdapter(getActivity(), licenseeOffences));
    }

    private void setVehicleOffences(View row, List<VehicleOffence> vehicleOffences) {
        ListView vehicleOffencesView = (ListView) row.findViewById(R.id.vehicle_Offences);
        vehicleOffencesView.setAdapter(new VehicleOffenceAdapter(getActivity(), vehicleOffences));
    }

    private void setVehicleColor(View row, Vehicle current) {
        SystemUtil.setTextViewText(row, R.id.vehicle_color, current.getColor());
    }

    private void setVehicleMake(View row, Vehicle current) {
        SystemUtil.setTextViewText(row, R.id.vehicle_make, current.getMake());
    }

    private void setVehicleNumber(View row, Vehicle current) {
        SystemUtil.setTextViewText(row, R.id.vehicle_number, current.getNumberPlateString());
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
    }*/

}
