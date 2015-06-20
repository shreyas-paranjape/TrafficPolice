package com.cybercad.challan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.offence.Offence;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.util.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OffenceAdapter extends ArrayAdapter<Offence> {

    private Context context;

    public OffenceAdapter(Activity context, List<Offence> offences) {
        super(context, R.layout.item_offence, offences);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(context)
                    .inflate(R.layout.item_offence, parent, false);
        }
        setFields(convertView, getItem(position));
        return convertView;
    }

    private void setFields(View row, Offence current) {
        setDate(row, current);
        setDescription(row, current.getOffenceType());
    }

    private void setDate(View row, Offence current) {
        String formattedBirthDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(current.getIssueDate());
        SystemUtil.setTextViewText(row, R.id.ofc_date, formattedBirthDate);
    }

    private void setDescription(View row, OffenceType current) {
        SystemUtil.setTextViewText(row, R.id.ofc_desc, current.getDescription());
    }

}
