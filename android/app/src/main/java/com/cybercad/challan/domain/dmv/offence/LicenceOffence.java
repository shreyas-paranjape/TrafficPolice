package com.cybercad.challan.domain.dmv.offence;

import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.people.Licensee;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LicenceOffence extends SugarRecord implements Serializable {

    private static final long serialVersionUID = 1;

    @NotNull
    private Licence licence;
    @NotNull
    private Date issueDate;
    @NotNull
    private OffenceType offenceType;
    @Ignore
    private Integer repetetion;


    public LicenceOffence() {
    }

    public LicenceOffence(Licence licence, Date issueDate, OffenceType offenceType) {
        this.licence = licence;
        this.issueDate = issueDate;
        this.offenceType = offenceType;
    }

    public static List<LicenceOffence> getAll() {
        return listAll(LicenceOffence.class);
    }

    public static List<LicenceOffence> getForLicence(Licence licence) {
        if (licence.getId() != null) {
            return SugarRecord.find(LicenceOffence.class, "licence = ?", new String[]{licence.getId().toString()});
        } else {
            return new ArrayList<>();
        }
    }

    public double getPenalty() {
        return repetetion * offenceType.getPenalty();
    }

    public LicenceOffence calculateRepetetion() {
        List<LicenceOffence> sameOffenceType = SugarRecord.find(LicenceOffence.class, "offence_type = ?", new String[]{});
        if (sameOffenceType != null) {
            repetetion = sameOffenceType.size();
        } else {
            repetetion = 1;
        }
        return this;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
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

    public int getRepetetion() {
        return repetetion;
    }

    @Override
    public String toString() {
        return "LicenceOffence{" +
                "licence=" + licence +
                ", issueDate=" + issueDate +
                ", offenceType=" + offenceType +
                '}';
    }
}