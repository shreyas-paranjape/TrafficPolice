package com.cybercad.challan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.people.Licensee;
import com.cybercad.challan.domain.dmv.people.PersonalDetails;
import com.cybercad.challan.util.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LicenceAdapter extends ArrayAdapter<Licence> {

    public LicenceAdapter(Context context, List<Licence> licences) {
        super(context, R.layout.item_licensee, licences);
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

    private void setFields(View row, Licence current) {
        setName(row, current.getLicensee().getPersonalDetails());
        setBirthDate(row, current.getLicensee().getPersonalDetails());
        setLicenceNumber(row, current);
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
