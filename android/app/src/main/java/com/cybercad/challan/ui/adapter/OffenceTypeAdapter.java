package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.util.SystemUtil;

import java.util.List;

public class OffenceTypeAdapter extends ArrayAdapter<OffenceType> {

    private static final String TAG = OffenceTypeAdapter.class.getName();

    public OffenceTypeAdapter(Context context, List<OffenceType> offenceTypes) {
        super(context, R.layout.item_offence_type, offenceTypes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_offence_type, parent, false);
        }
        final OffenceType offenceType = getItem(position);
        setFields(convertView, offenceType);
        final CheckBox addOffence = (CheckBox) convertView.findViewById(R.id.chkbx_add_offence);
        addOffence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Offence added " + offenceType.getDescription());
                } else {
                    Log.i(TAG, "Offence removed " + offenceType.getDescription());
                }
            }
        });
        return convertView;
    }

    private void setFields(View row, OffenceType offenceType) {
        SystemUtil.setTextViewText(row, R.id.offence_type_description, offenceType.getDescription());
        SystemUtil.setTextViewText(row, R.id.offence_type_penalty, offenceType.getPenalty().toString());
    }

}