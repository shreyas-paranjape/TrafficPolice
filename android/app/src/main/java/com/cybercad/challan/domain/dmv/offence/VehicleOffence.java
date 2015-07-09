package com.cybercad.challan.domain.dmv.offence;

import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleOffence extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    private Date issueDate;
    private OffenceType offenceType;
    private Vehicle vehicle;
    @Ignore
    private Integer repetetion = new Integer(1);

    public VehicleOffence() {
    }

    public VehicleOffence(Vehicle vehicle, Date issueDate, OffenceType offenceType) {
        this.vehicle = vehicle;
        this.issueDate = issueDate;
        this.offenceType = offenceType;
        calculateRepetetion();
    }

    public static List<VehicleOffence> getAll() {
        return listAll(VehicleOffence.class);
    }

    public static List<VehicleOffence> getForVehicle(Vehicle vehicle) {
        if (vehicle.getId() != null) {
            return SugarRecord.find(VehicleOffence.class, "vehicle = ?", new String[]{vehicle.getId().toString()});
        } else {
            return new ArrayList<>();
        }
    }

    public double getPenalty() {
        return repetetion * offenceType.getPenalty();
    }

    public VehicleOffence calculateRepetetion() {
        List<VehicleOffence> sameOffenceType = SugarRecord.find(VehicleOffence.class, "offence_type = ?", new String[]{offenceType.getId().toString()});
        if (sameOffenceType != null) {
            repetetion = sameOffenceType.size() + 1;
        }
        return this;
    }

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


    public Integer getRepetetion() {
        return repetetion;
    }

    public void setRepetetion(Integer repetetion) {
        this.repetetion = repetetion;
    }

    @Override
    public String toString() {
        return "VehicleOffence{" +
                "issueDate=" + issueDate +
                ", offenceType=" + offenceType +
                ", vehicle=" + vehicle +
                ", repetetion=" + repetetion +
                '}';
    }
}
