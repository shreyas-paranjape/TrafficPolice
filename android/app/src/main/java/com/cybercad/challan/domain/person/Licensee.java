package com.cybercad.challan.domain.person;

import android.util.Log;

import com.cybercad.challan.domain.Licence.Licence;
import com.cybercad.challan.domain.offence.Offence;
import com.cybercad.challan.util.CollectionUtil;
import com.orm.SugarRecord;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Licensee extends SugarRecord {

    private static final String TAG = Licensee.class.getName();
    private PersonalDetails personalDetails;
    private Licence licence;

    public Licensee() {
    }

    public Licensee(PersonalDetails personalDetails, Licence licence) {
        this.personalDetails = personalDetails;
        this.licence = licence;
    }

    public List<Offence> getOffences() {
        if (getId() != null) {
            return SugarRecord.find(Offence.class, "licensee = ?", new String[]{getId().toString()});
        }
        return null;
    }

    public static List<Licensee> getAll() {
        return listAll(Licensee.class);
    }

    public static Collection<Licensee> searchByLicence(String licenceNumber) {
        Collection<Licensee> result = new HashSet<>();
        Collection<Licence> licences = find(Licence.class, "licence_number like ?", new String[]{"%" + licenceNumber});
        Log.d(TAG, "Licences : " + CollectionUtil.toString(licences));
        for (Licence licence : licences) {
            result.add(getLicensee(licence));
        }
        return result;
    }

    private static Licensee getLicensee(Licence licence) {
        Log.d(TAG, "Licence : " + licence);
        List<Licensee> licensees = find(Licensee.class, "licence = ?", new String[]{licence.getId().toString()});
        if (licensees.size() == 1)
            return licensees.get(0);
        else
            return null;
    }

    @Override
    public long save() {
        personalDetails.save();
        licence.save();
        return super.save();
    }

    public void save(Collection<Offence> offences) {
        save();
        for (Offence offence : offences) {
            addOffence(offence);
        }
    }

    public void addOffence(Offence offence) {
        if (getId() != null) {
            offence.setLicensee(this);
            offence.save();
        }
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "Licensee{" +
                "id=" + this.getId() +
                "personalDetails=" + personalDetails +
                ", licence=" + licence +
                '}';
    }
}
