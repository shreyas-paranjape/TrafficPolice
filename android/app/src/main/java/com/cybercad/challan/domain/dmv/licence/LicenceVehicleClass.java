package com.cybercad.challan.domain.dmv.licence;

import com.cybercad.challan.domain.dmv.vehicle.VehicleClass;
import com.orm.SugarRecord;
import com.orm.dsl.UniqueComposite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LicenceVehicleClass extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @UniqueComposite
    private VehicleClass vehicleClass;
    @UniqueComposite
    private Licence licence;
    @UniqueComposite
    private Date dateOfIssue;

    public LicenceVehicleClass() {
    }

    public LicenceVehicleClass(VehicleClass vehicleClass, Date dateOfIssue, Licence licence) {
        this.vehicleClass = vehicleClass;
        this.dateOfIssue = dateOfIssue;
        this.licence = licence;
    }

    public static List<LicenceVehicleClass> getForLicence(Licence licence) {
        if (licence.getId() != null) {
            return SugarRecord.find(LicenceVehicleClass.class, "licence = ?", new String[]{licence.getId().toString()});
        } else {
            return new ArrayList<>();
        }
    }

    public VehicleClass getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
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
                "vehicleClass=" + vehicleClass +
                ", dateOfIssue=" + dateOfIssue +
                ", licence=" + licence +
                '}';
    }
}
