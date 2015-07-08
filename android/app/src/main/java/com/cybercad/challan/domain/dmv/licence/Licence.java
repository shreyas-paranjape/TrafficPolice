package com.cybercad.challan.domain.dmv.licence;

import android.util.Log;

import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.people.Licensee;
import com.cybercad.challan.util.CollectionUtil;
import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Licence extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @Unique
    private String licenceNumber;
    @NotNull
    private Date issueDate;
    @NotNull
    private Date expiryDate;
    @NotNull
    private Licensee licensee;

    public Licence() {
    }

    public Licence(String licenceNumber, Licensee licensee, Date issueDate, Date expiryDate) {
        this.licenceNumber = licenceNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.licensee = licensee;
    }

    public static Collection<Licence> searchByNumber(String licenceNumber) {
        return find(Licence.class, "licence_number like ?", new String[]{"%" + licenceNumber + "%"});
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

    public Licensee getLicensee() {
        return licensee;
    }

    public void setLicensee(Licensee licensee) {
        this.licensee = licensee;
    }

    @Override
    public String toString() {
        return "Licence{" +
                "licenceNumber='" + licenceNumber + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                ", licensee=" + licensee +
                '}';
    }
}
