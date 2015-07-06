package com.cybercad.challan.domain.offence;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.List;

public class OffenceType extends SugarRecord {

    @Unique
    private String code;
    private String description;
    private double penalty;

    public OffenceType() {
    }

    public OffenceType(String code, String description, double penalty) {
        this.code = code;
        this.description = description;
        this.penalty = penalty;
    }

    public static List<OffenceType> getAll() {
        return listAll(OffenceType.class);
    }

    public static OffenceType searchByCode(String code) {
        List<OffenceType> offenceTypes = find(OffenceType.class, "code = ?", new String[]{code});
        if (offenceTypes.size() == 1)
            return offenceTypes.get(0);
        else
            return null;
    }

    public static OffenceType searchByDescription(String description) {
        List<OffenceType> offenceTypes = find(OffenceType.class, "description like ?", new String[]{"%"+description+"%"});
        if (offenceTypes.size() == 1)
            return offenceTypes.get(0);
        else
            return null;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "OffenceType{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", penalty=" + penalty +
                '}';
    }
}
