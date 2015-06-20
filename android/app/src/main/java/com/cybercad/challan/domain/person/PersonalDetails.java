package com.cybercad.challan.domain.person;

import android.text.TextUtils;

import com.cybercad.challan.domain.Licence.Licence;
import com.cybercad.challan.domain.offence.Offence;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by shreyas on 12/6/15.
 *
 */
public class PersonalDetails extends SugarRecord {

    private String lastName;
    private String middleName;
    private String firstName;
    private Date dateOfBirth;

    public PersonalDetails(){
    }

    public PersonalDetails(String lastName, String middleName, String firstName, Date dateOfBirth) {
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "PersonalDetails{" +
                "id=" + this.getId() +
                "lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public String getName() {
        return TextUtils.join(" ",new String[]{lastName,middleName,firstName});
    }
}