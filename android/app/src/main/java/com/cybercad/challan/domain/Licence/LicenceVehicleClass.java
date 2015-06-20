package com.cybercad.challan.domain.Licence;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.orm.dsl.UniqueComposite;

import java.util.Date;

/**
 * Created by shreyas on 12/6/15.
 */
public class LicenceVehicleClass extends SugarRecord {

    private String code;
    private Date dateOfIssue;
    private String description;

    private Licence licence;

    public LicenceVehicleClass() {
    }

    public LicenceVehicleClass(String code, Date dateOfIssue, String description, Licence licence) {
        this.code = code;
        this.dateOfIssue = dateOfIssue;
        this.description = description;
        this.licence = licence;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "LicenceVehicleClass{" +
                "id=" + this.getId() +
                "code='" + code + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", description='" + description + '\'' +
                ", licence=" + licence +
                '}';
    }
}
