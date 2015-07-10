package com.cybercad.challan.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.util.SystemUtil;

import java.util.List;

public class AddedVehicleOffence extends ArrayAdapter<VehicleOffence> {

    public AddedVehicleOffence(Activity context, List<VehicleOffence> licenceOffences) {
        super(context, R.layout.item_added_offence, licenceOffences);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_added_offence, parent, false);
        }
        setFields(convertView, getItem(position));
        return convertView;
    }

    private void setFields(View row, VehicleOffence current) {
        setDescription(row, current.getOffenceType());
        setRepetetion(row, current);
        setPenalty(row, current);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


    private void setDescription(View row, OffenceType current) {
        SystemUtil.setTextViewText(row, R.id.off_desc, current.getDescription());
    }

    private void setRepetetion(View row, VehicleOffence current) {
        SystemUtil.setTextViewText(row, R.id.off_repetetion, "" + current.getRepetetion());
    }

    private void setPenalty(View row, VehicleOffence current) {
        SystemUtil.setTextViewText(row, R.id.off_penalty, "" + current.getPenalty());
    }
}