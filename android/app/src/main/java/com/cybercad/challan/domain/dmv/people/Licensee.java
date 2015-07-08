package com.cybercad.challan.domain.dmv.people;

import com.cybercad.challan.domain.dmv.licence.Licence;
import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

public class Licensee extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;
    private static final String TAG = Licensee.class.getName();

    @Unique
    private PersonalDetails personalDetails;

    public Licensee() {
    }

    public Licensee(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    @Override
    public String toString() {
        return "Licensee{" +
                "personalDetails=" + personalDetails +
                '}';
    }
}
