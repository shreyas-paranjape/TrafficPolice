package com.cybercad.challan.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.util.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VehicleOffenceAdapter extends ArrayAdapter<VehicleOffence> {

    public VehicleOffenceAdapter(Activity context, List<VehicleOffence> offences) {
        super(context, R.layout.item_offence, offences);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_offence, parent, false);
        }
        setFields(convertView, getItem(position));
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private void setFields(View row, VehicleOffence current) {
        setDate(row, current);
        setDescription(row, current.getOffenceType());
    }

    private void setDate(View row, VehicleOffence current) {
        String issueDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(current.getIssueDate());
        SystemUtil.setTextViewText(row, R.id.ofc_date, issueDate);
    }

    private void setDescription(View row, OffenceType current) {
        SystemUtil.setTextViewText(row, R.id.ofc_desc, current.getDescription());
    }
}
