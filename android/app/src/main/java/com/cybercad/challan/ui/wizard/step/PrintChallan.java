package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.service.cache.ObjectCache;
import com.cybercad.challan.service.print.BluetoothPrinter;
import com.cybercad.challan.service.print.BluetoothPrinterImpl;

import org.codepond.wizardroid.WizardStep;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PrintChallan extends WizardStep {

    private BluetoothPrinter bluetoothPrinter;
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private OutputStream mOutputStream;
    private BluetoothPrinter printer;

    public PrintChallan() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();
        printer = new BluetoothPrinterImpl(getActivity(), "MOB-80");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_print, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        Button nextButton = (Button) v.findViewById(R.id.btn_print_nxt);
        final CheckBox paid_chk = (CheckBox) v.findViewById(R.id.chk_paid);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printer.print(getChallanString(paid_chk.isChecked()));
               /* try {
                    Bus.getInstance().post(
                            new IssueChallanWizardLayout.WizardEvent(
                                    null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
                } catch (Exception e) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }*/
            }
        });


    }

    private String getChallanString(boolean isPaid) {
        Vehicle vehicle = (Vehicle) ObjectCache.get("vehicle");
        Licence licence = (Licence) ObjectCache.get("licence");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        long total = 0;
        StringBuilder printStringBuilder = new StringBuilder();

        String docTypeString = null;
        if (isPaid) {
            docTypeString = "\tTraffic violation pay receipt\n";
        } else {
            docTypeString = "\tTraffic violation challan\n";
        }
        printStringBuilder.append("\tGOVERNMENT OF GOA\r\n")
                .append("\tDIRECTORATE OF TRANSPORT\r\n")
                .append("\tTraffic violation challan\r\n")
                .append("-------------------------------------------\r\n")
                .append("Challan no:")
                .append("123456789\t")
                .append(" Date: ")
                .append(formatter.format(new Date()))
                .append(" \r\n")
                .append("Issued by : Mr. Traffic police\r\n")

                .append("Licensee details : \r\n")
                .append("Name - Shreyas Mahesh Paranjape\r\n")

                .append("Licence details: \r\n")
                .append("Number- ")
                .append(licence.getLicenceNumber())
                .append(" Expiry- ")
                .append(formatter.format(licence.getExpiryDate()))
                .append("\r\n")
                .append("Vehicle class- LMV, MCWG\r\n")

                .append("Vehicle details: \r\n")
                .append(vehicle.getNumberPlateString())
                .append(" ")
                .append(vehicle.getMake())
                .append(" ")
                .append(vehicle.getColor())
                .append("\r\n\r\n");
        List<VehicleOffence> vehOff = (List<VehicleOffence>) ObjectCache.get("vehicle_offences");
        for (VehicleOffence off : vehOff) {
            off.save();
            total += off.getPenalty();
            printStringBuilder.append(off.getOffenceType().getDescription());
            printStringBuilder.append("\t");
            printStringBuilder.append(off.getRepetetion());
            printStringBuilder.append("\t");
            printStringBuilder.append(off.getPenalty());
            printStringBuilder.append("\r\n");
        }

        List<LicenceOffence> licOff = (List<LicenceOffence>) ObjectCache.get("licence_offences");
        for (LicenceOffence off : licOff) {
            off.save();
            total += off.getPenalty();
            printStringBuilder.append(off.getOffenceType().getDescription());
            printStringBuilder.append("\t");
            printStringBuilder.append(off.getRepetetion());
            printStringBuilder.append("\t");
            printStringBuilder.append(off.getPenalty());
            printStringBuilder.append("\r\n");
        }
        printStringBuilder.append("\tTotal : ")
                .append(total)
                .append("\r\n");
        return printStringBuilder.toString();
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
