package com.cybercad.challan.ui.wizard.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.service.print.BluetoothPrinter;
import com.cybercad.challan.service.print.BluetoothPrinterImpl;
import com.cybercad.challan.ui.adapter.PrintOffenceTypeAdapter;
import com.cybercad.challan.ui.wizard.layout.IssueChallanWizardLayout;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.infrastructure.Bus;

import java.text.SimpleDateFormat;

public class PrintChallan extends WizardStep {

    private BluetoothPrinter bluetoothPrinter;

    public PrintChallan() {
        super();
        bluetoothPrinter = new BluetoothPrinterImpl(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_print, container, false);
        initView(v);
        //bluetoothPrinter.connect("MOB-80");
        return v;
    }

    private void initView(View v) {
        ListView offences = (ListView) v.findViewById(R.id.lv_print_offences);
        offences.setAdapter(new PrintOffenceTypeAdapter(getActivity(), OffenceType.getAll()));

        Button nextButton = (Button) v.findViewById(R.id.btn_print_nxt);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bluetoothPrinter.print(getChallanString());
                Bus.getInstance().post(
                        new IssueChallanWizardLayout.WizardEvent(
                                null, IssueChallanWizardLayout.WizardEvent.Type.NEXT));
            }
        });
    }

    private String getChallanString() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder printStringBuilder = new StringBuilder("GOVERNMENT OF GOA\r\n");
        printStringBuilder.append("DIRECTORATE OF TRANSPORT\r\n")
                .append("Traffic violation challan\r\n")
                .append("-------------------------------------------\r\n")
                .append("Challan no: 123456789").append(" Date: ")
                .append(formatter.format(new Date()))
                .append(" \r\n")
                .append("Issued by : Mr. XYZ\r\n")

                .append("Licensee details : \r\n")
                .append("Name - Shreyas Mahesh Paranjape\r\n")

                .append("Licence details: \r\n")
                .append("Number- MH12 201100799947 ")
                .append("Valid till-  07-09-2031\r\n")
                .append("Vehicle class- LMV, MCWG\r\n")

                .append("Vehicle details: \r\n")
                .append("No- GA05K7100, Make- YAMAHA FZS, Color- Blue\r\n");


        return printStringBuilder.toString();
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }
}
