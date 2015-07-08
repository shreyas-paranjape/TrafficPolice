package com.cybercad.challan.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.util.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OffenceAdapter extends ArrayAdapter<LicenceOffence> {

    public OffenceAdapter(Activity context, List<LicenceOffence> licenceOffences) {
        super(context, R.layout.item_offence, licenceOffences);
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

    private void setFields(View row, LicenceOffence current) {
        setDate(row, current);
        setDescription(row, current.getOffenceType());
    }

    private void setDate(View row, LicenceOffence current) {
        String issueDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(current.getIssueDate());
        SystemUtil.setTextViewText(row, R.id.ofc_date, issueDate);
    }

    private void setDescription(View row, OffenceType current) {
        SystemUtil.setTextViewText(row, R.id.ofc_desc, current.getDescription());
    }

}
