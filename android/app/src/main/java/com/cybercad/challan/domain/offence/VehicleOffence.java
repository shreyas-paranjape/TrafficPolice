package com.cybercad.challan.domain.offence;

import com.cybercad.challan.domain.Vehicle;
import com.orm.SugarRecord;

import java.util.Date;

public class VehicleOffence extends SugarRecord {

    private Date issueDate;
    private OffenceType offenceType;
    private Vehicle vehicle;

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public OffenceType getOffenceType() {
        return offenceType;
    }

    public void setOffenceType(OffenceType offenceType) {
        this.offenceType = offenceType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
