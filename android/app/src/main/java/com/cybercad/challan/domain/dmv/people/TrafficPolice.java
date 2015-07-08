package com.cybercad.challan.domain.dmv.people;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

public class TrafficPolice extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @Unique
    private String badgeNumber;
    @Unique
    private PersonalDetails personalDetails;

    @Override
    public String toString() {
        return "TrafficPolice{" +
                "personalDetails=" + personalDetails +
                ", badgeNumber='" + badgeNumber + '\'' +
                '}';
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

}
