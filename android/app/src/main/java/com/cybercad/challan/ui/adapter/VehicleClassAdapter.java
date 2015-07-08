package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.LicenceVehicleClass;
import com.cybercad.challan.util.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VehicleClassAdapter extends ArrayAdapter<LicenceVehicleClass> {

    public VehicleClassAdapter(Context context, List<LicenceVehicleClass> vehicleClasses) {
        super(context, R.layout.item_vehicle_class, vehicleClasses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_vehicle_class, parent, false);
        }
        setFields(convertView, getItem(position));
        return convertView;
    }

    private void setFields(View row, LicenceVehicleClass current) {
        setIssueDate(row, current);
        setDescription(row, current);
        setCode(row, current);
    }

    private void setIssueDate(View row, LicenceVehicleClass current) {
        String formattedBirthDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(current.getDateOfIssue());
        SystemUtil.setTextViewText(row, R.id.vc_issue, formattedBirthDate);
    }

    private void setDescription(View row, LicenceVehicleClass current) {
        //TODO SystemUtil.setTextViewText(row, R.id.vc_desc, current.getDescription());
    }

    private void setCode(View row, LicenceVehicleClass current) {
        //TODO SystemUtil.setTextViewText(row, R.id.vc_code, current.getCode());
    }

}
