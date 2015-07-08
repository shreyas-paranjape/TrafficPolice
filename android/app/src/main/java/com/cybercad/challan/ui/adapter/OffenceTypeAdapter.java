package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.util.SystemUtil;

import java.util.List;

public class OffenceTypeAdapter extends ArrayAdapter<OffenceType> {

    private static final String TAG = OffenceTypeAdapter.class.getName();

    public OffenceTypeAdapter(Context context, List<OffenceType> offenceTypes) {
        super(context, R.layout.item_offence_type, offenceTypes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OffenceType offenceType = getItem(position);
        ToggleButton button = new ToggleButton(getContext());
        button.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.toggle));
        button.setText(offenceType.getDescription());
        button.setTextOff(offenceType.getDescription());
        button.setTextOn(offenceType.getDescription());
        return button;
    }

}