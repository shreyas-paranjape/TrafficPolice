package com.cybercad.challan.domain.dmv.vehicle;

import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.domain.dmv.people.VehicleOwner;
import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.UniqueComposite;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public class Vehicle extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @UniqueComposite
    private String stateCode;
    @UniqueComposite
    private String numberPrefix;
    @UniqueComposite
    private String number;
    @NotNull
    private String make;
    @NotNull
    private String color;
    @NotNull
    private VehicleClass vehicleClass;
    private VehicleOwner vehicleOwner;

    public Vehicle() {
    }

    public Vehicle(String stateCode, String numberPrefix, String number,
                   String make, String color, VehicleClass vehicleClass) {
        this.stateCode = stateCode;
        this.numberPrefix = numberPrefix;
        this.number = number;
        this.make = make;
        this.color = color;
        this.vehicleClass = vehicleClass;
    }

    public Vehicle(String stateCode, String numberPrefix, String number,
                   String make, String color, VehicleClass vehicleClass,
                   VehicleOwner vehicleOwner) {
        this.stateCode = stateCode;
        this.numberPrefix = numberPrefix;
        this.number = number;
        this.make = make;
        this.color = color;
        this.vehicleClass = vehicleClass;
        this.vehicleOwner = vehicleOwner;
    }

    public String getNumberPlateString() {
        return new StringBuilder(stateCode)
                .append(" - ")
                .append(numberPrefix)
                .append(" - ")
                .append(number).toString();
    }

    public static Collection<Vehicle> getAll() {
        return SugarRecord.listAll(Vehicle.class);
    }

    public static Collection<Vehicle> findByNumber(String number) {
        return find(Vehicle.class, "number like ?", new String[]{"%" + number + "%"});
    }

    /*public void addOffence(VehicleOffence offence) {
        if (getId() != null) {
            offence.setVehicle(this);
            offence.save();
        }
    }

    public List<VehicleOffence> getOffenses() {
        if (getId() != null) {
            return SugarRecord.find(VehicleOffence.class, "vehicle = ?", new String[]{getId().toString()});
        }
        return null;
    }*/

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getNumberPrefix() {
        return numberPrefix;
    }

    public void setNumberPrefix(String numberPrefix) {
        this.numberPrefix = numberPrefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleClass getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public VehicleOwner getVehicleOwner() {
        return vehicleOwner;
    }

    public void setVehicleOwner(VehicleOwner vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "stateCode='" + stateCode + '\'' +
                ", numberPrefix='" + numberPrefix + '\'' +
                ", number='" + number + '\'' +
                ", make='" + make + '\'' +
                ", color='" + color + '\'' +
                ", vehicleClass=" + vehicleClass +
                ", vehicleOwner=" + vehicleOwner +
                '}';
    }
}
