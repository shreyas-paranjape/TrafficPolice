package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.util.SystemUtil;

import java.util.List;


public class VehicleAdapter extends ArrayAdapter<Vehicle> {


    public VehicleAdapter(Context context, List<Vehicle> vehicles) {
        super(context, R.layout.item_vehicle, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_vehicle, parent, false);
        }
        setFields(convertView, getItem(position));
        return convertView;
    }

    private void setFields(View row, Vehicle current) {
        setVehicleNumber(row, current);
        setVehicleMake(row, current);
        setVehicleColor(row, current);
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

}