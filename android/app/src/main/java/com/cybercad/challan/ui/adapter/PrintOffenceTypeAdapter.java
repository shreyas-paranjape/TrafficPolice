package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.util.SystemUtil;

import java.util.List;

public class PrintOffenceTypeAdapter extends ArrayAdapter<OffenceType> {
    private static final String TAG = PrintOffenceTypeAdapter.class.getName();

    public PrintOffenceTypeAdapter(Context context, List<OffenceType> offenceTypes) {
        super(context, R.layout.item_print_offence_type, offenceTypes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_print_offence_type, parent, false);
        }
        final OffenceType offenceType = getItem(position);
        setFields(convertView, offenceType);
        return convertView;
    }

    private void setFields(View row, OffenceType offenceType) {
        SystemUtil.setTextViewText(row, R.id.tv_print_off_type_desc, offenceType.getDescription());
        SystemUtil.setTextViewText(row, R.id.tv_print_off_type_pen, offenceType.getPenalty().toString());
    }
}
