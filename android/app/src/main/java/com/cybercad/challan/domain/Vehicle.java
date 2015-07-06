package com.cybercad.challan.domain;

import com.cybercad.challan.domain.offence.VehicleOffence;
import com.orm.SugarRecord;
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

    private String make;
    private String color;

    //private Person owner;
    //private List<Offence> offences;

    public Vehicle() {
    }

    public Vehicle(String stateCode, String numberPrefix, String number, String make, String color) {
        this.stateCode = stateCode;
        this.numberPrefix = numberPrefix;
        this.number = number;
        this.make = make;
        this.color = color;
    }

    public String getLicencePlateString() {
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
        return find(Vehicle.class, "number like ?", new String[]{"%" + number});
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "stateCode='" + stateCode + '\'' +
                ", numberPrefix='" + numberPrefix + '\'' +
                ", number='" + number + '\'' +
                ", make='" + make + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

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

    public List<VehicleOffence> getOffenses() {
        if (getId() != null) {
            return SugarRecord.find(VehicleOffence.class, "vehicle = ?", new String[]{getId().toString()});
        }
        return null;
    }
}
