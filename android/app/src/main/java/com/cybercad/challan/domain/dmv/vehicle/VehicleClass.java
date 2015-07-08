package com.cybercad.challan.domain.dmv.vehicle;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.util.Collection;

public class VehicleClass extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @Unique
    private String code;
    private String description;

    public VehicleClass() {
    }

    public VehicleClass(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Collection<VehicleClass> searchByCode(String code) {
        return find(VehicleClass.class, "code like ?", new String[]{"%" + code});
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VehicleClass{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
