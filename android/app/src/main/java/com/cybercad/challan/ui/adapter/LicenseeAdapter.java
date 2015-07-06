package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.Licence.Licence;
import com.cybercad.challan.domain.person.Licensee;
import com.cybercad.challan.domain.person.PersonalDetails;
import com.cybercad.challan.util.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LicenseeAdapter extends ArrayAdapter<Licensee> {


    public LicenseeAdapter(Context context, List<Licensee> licensees) {
        super(context, R.layout.item_licensee, licensees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = SystemUtil.getLayoutInflator(getContext())
                    .inflate(R.layout.item_licensee, parent, false);
        }
        setFields(convertView, getItem(position));
        return convertView;
    }

    private void setFields(View row, Licensee current) {
        setName(row, current.getPersonalDetails());
        setBirthDate(row, current.getPersonalDetails());
        setLicenceNumber(row, current.getLicence());
    }

    private void setName(View row, PersonalDetails current) {
        SystemUtil.setTextViewText(row, R.id.licensee_name, current.getName());
    }

    private void setBirthDate(View row, PersonalDetails current) {
        String formattedBirthDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(current.getDateOfBirth());
        SystemUtil.setTextViewText(row, R.id.licensee_bdate, formattedBirthDate);
    }

    private void setLicenceNumber(View row, Licence current) {
        SystemUtil.setTextViewText(row, R.id.licensee_ls_no, current.getLicenceNumber());
    }
}

