package com.cybercad.challan.domain.Licence;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by shreyas on 12/6/15.
 *
 */
public class Licence extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    private String licenceNumber;
    private Date issueDate;
    private Date expiryDate;


    public Licence(){
    }

    public Licence(String licenceNumber, Date issueDate, Date expiryDate) {
        this.licenceNumber = licenceNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<LicenceVehicleClass> getVehicleClasses() {
        return find(LicenceVehicleClass.class,"Licence = ?",new String[]{getId().toString()});
    }

    public void setVehicleClasses(List<LicenceVehicleClass> vehicleClasses) {
        for(LicenceVehicleClass licenceVehicleClass : vehicleClasses){
            addVehicleClass(licenceVehicleClass);
        }
    }

    public void addVehicleClass(LicenceVehicleClass licenceVehicleClass){
        licenceVehicleClass.setLicence(this);
        licenceVehicleClass.save();
    }

    @Override
    public String toString() {
        return "Licence{" +
                "id=" + this.getId() +
                "licenceNumber='" + licenceNumber + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
