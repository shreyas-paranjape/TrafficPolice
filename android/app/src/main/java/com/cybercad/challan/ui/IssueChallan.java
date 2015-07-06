package com.cybercad.challan.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.Licence.Licence;
import com.cybercad.challan.domain.Licence.LicenceVehicleClass;
import com.cybercad.challan.domain.Vehicle;
import com.cybercad.challan.domain.offence.Offence;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.domain.person.Licensee;
import com.cybercad.challan.domain.person.PersonalDetails;

import java.util.Date;

public class IssueChallan extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_challan);
        dummyData();
    }

    private void dummyData() {
        new Vehicle("GA05", "K", "7100", "Yamaha FZ-S", "BLUE").save();

        OffenceType noHelmet = new OffenceType("NH", "No helmet", 500);
        noHelmet.save();

        Licence licence = new Licence("12345", new Date(), new Date());
        LicenceVehicleClass twoWheeler = new LicenceVehicleClass("TW", new Date(), "Two wheeler", licence);

        PersonalDetails personalDetails = new PersonalDetails("Shreyas", "Mahesh", "Paranjape", new Date());
        Licensee shrep = new Licensee(personalDetails, licence);

        Offence shrepNoHelmet = new Offence(shrep, new Date(), OffenceType.searchByCode("NH"));

        shrep.save();
        shrep.addOffence(shrepNoHelmet);
        twoWheeler.save();

        // Log.d(TAG, "Offence Types :" + CollectionUtil.toString(OffenceType.getAll()));
        // Log.d(TAG, "licensees :" + CollectionUtil.toString(Licensee.getAll()));
        // Log.d(TAG, "Offences :" + CollectionUtil.toString(Offence.getAll()));
    }
}
