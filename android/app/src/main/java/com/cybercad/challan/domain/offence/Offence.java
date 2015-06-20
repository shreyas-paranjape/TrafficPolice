package com.cybercad.challan.domain.offence;

import com.cybercad.challan.domain.person.Licensee;
import com.orm.SugarRecord;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Offence extends SugarRecord {

    private Licensee licensee;
    private Date issueDate;

    private OffenceType offenceType;

    public Offence() {
    }

    public Offence(Licensee licensee, Date issueDate, OffenceType offenceType) {
        this.licensee = licensee;
        this.issueDate = issueDate;
        this.offenceType = offenceType;
    }

    public void setLicensee(Licensee licensee) {
        this.licensee = licensee;
    }

    @Override
    public String toString() {
        return "Offence{" +
                "id=" + this.getId() +
                ", licensee=" + licensee +
                ", issueDate=" + issueDate +
                ", offenceType=" + offenceType +
                '}';
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


    public static List<Offence> getAll() {
        return listAll(Offence.class);
    }
}