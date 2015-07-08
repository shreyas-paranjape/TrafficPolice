package com.cybercad.challan.domain.dmv.people;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

public class VehicleOwner extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @Unique
    private PersonalDetails personalDetails;

    public VehicleOwner() {
    }

    public VehicleOwner(PersonalDetails personalDetails) {
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
        return "VehicleOwner{" +
                "personalDetails=" + personalDetails +
                '}';
    }
}
