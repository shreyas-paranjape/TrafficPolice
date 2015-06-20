package com.cybercad.challan.domain.person;

import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by shreyas on 12/6/15.
 *
 */
@Table
public class TrafficPolice {

    private PersonalDetails personalDetails;

    @Column(name="id_number")
    private String idNumber;

    @Override
    public String toString() {
        return "TrafficPolice{" +
                "personalDetails=" + personalDetails +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

}
