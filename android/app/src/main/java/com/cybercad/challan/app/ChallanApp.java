package com.cybercad.challan.app;

import android.app.Application;
import android.util.Log;

import com.cybercad.challan.domain.dmv.licence.Licence;
import com.cybercad.challan.domain.dmv.offence.LicenceOffence;
import com.cybercad.challan.domain.dmv.offence.OffenceType;
import com.cybercad.challan.domain.dmv.offence.VehicleOffence;
import com.cybercad.challan.domain.dmv.people.Licensee;
import com.cybercad.challan.domain.dmv.people.PersonalDetails;
import com.cybercad.challan.domain.dmv.people.VehicleOwner;
import com.cybercad.challan.domain.dmv.vehicle.Vehicle;
import com.cybercad.challan.domain.dmv.vehicle.VehicleClass;
import com.cybercad.challan.util.CollectionUtil;
import com.orm.SugarContext;

import java.util.Date;


public class ChallanApp extends Application {

    public static final String TAG = ChallanApp.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        dummyData();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    private void dummyData() {

        // OFFENCE TYPE
        OffenceType noHelmet = new OffenceType("NH", "Helmet", 500, "l");
        noHelmet.save();

        OffenceType nsb = new OffenceType("NSB", "Seat belt", 1000, "l");
        nsb.save();

        OffenceType tintedGlass = new OffenceType("TG", "Tinted glass", 1000, "v");
        tintedGlass.save();

        OffenceType noMirror = new OffenceType("NMR", "Mirrors", 1000, "v");
        noMirror.save();

        OffenceType seatbelt = new OffenceType("SB", "Seatbelt", 500, "l");
        seatbelt.save();

        OffenceType overspeeding = new OffenceType("OS", "Overspeeding", 500, "l");
        overspeeding.save();

        OffenceType overtaking = new OffenceType("OT", "Overtaking", 500, "l");
        overtaking.save();

        OffenceType parking = new OffenceType("PK", "Parking", 500, "l");
        parking.save();

        OffenceType mobile = new OffenceType("UM", "Mobile", 500, "l");
        mobile.save();

        OffenceType drunkDriving = new OffenceType("DD", "Drunk", 500, "l");
        drunkDriving.save();

        OffenceType excess = new OffenceType("EC", "Excess capacity", 500, "l");
        excess.save();

        OffenceType pollution = new OffenceType("PC", "Pollution", 1000, "v");
        pollution.save();

        OffenceType horn = new OffenceType("HR", "Horn", 500, "l");
        horn.save();

        OffenceType number_plate = new OffenceType("FNB", "Number plate", 1000, "v");
        number_plate.save();


        // PEOPLE
        PersonalDetails shrep = new PersonalDetails("Shreyas", "Mahesh", "Paranjape", new Date(89, 8, 10));
        shrep.save();

        PersonalDetails shelton = new PersonalDetails("Shelton", "", "Desouza", new Date());
        shelton.save();

        PersonalDetails shawn = new PersonalDetails("Shawn", "", "Vaz", new Date());
        shawn.save();

        PersonalDetails prabhav = new PersonalDetails("Prabhav", "", "Siddhaye", new Date());
        prabhav.save();

        PersonalDetails amruta = new PersonalDetails("Amruta", "", "Bharne", new Date());
        amruta.save();

        PersonalDetails roshan = new PersonalDetails("Roshan", "", "Kumar", new Date());
        roshan.save();

        PersonalDetails siddhi = new PersonalDetails("Siddhi", "", "Chikalikar", new Date());
        siddhi.save();

        PersonalDetails punit = new PersonalDetails("Punit", "", "Naik", new Date());
        punit.save();

        PersonalDetails ruchita = new PersonalDetails("Ruchita", "", "Elekar", new Date());
        ruchita.save();

        PersonalDetails gautam = new PersonalDetails("Gautam", "", "Chidbe", new Date());
        gautam.save();

        PersonalDetails vaibhav = new PersonalDetails("Vaibhav", "", "Kharangate", new Date());
        vaibhav.save();

        PersonalDetails diksha = new PersonalDetails("Vaibhav", "", "Kharangate", new Date());
        diksha.save();


        // Licensee
        Licensee licensee_shrep = new Licensee(shrep);
        licensee_shrep.save();

        Licensee licensee_shelton = new Licensee(shelton);
        licensee_shelton.save();

        Licensee licensee_shawn = new Licensee(shawn);
        licensee_shawn.save();

        Licensee licensee_prabhav = new Licensee(prabhav);
        licensee_prabhav.save();

        Licensee licensee_amruta = new Licensee(amruta);
        licensee_amruta.save();

        Licensee licensee_roshan = new Licensee(roshan);
        licensee_roshan.save();

        Licensee licensee_siddhi = new Licensee(siddhi);
        licensee_siddhi.save();

        Licensee licensee_punit = new Licensee(punit);
        licensee_punit.save();

        Licensee licensee_ruchita = new Licensee(ruchita);
        licensee_ruchita.save();

        Licensee licensee_gautam = new Licensee(gautam);
        licensee_gautam.save();

        Licensee licensee_vaibhav = new Licensee(vaibhav);
        licensee_vaibhav.save();

        Licensee licensee_diksha = new Licensee(diksha);
        licensee_diksha.save();

        // Licence
        Licence licence_shrep = new Licence("GA0320110079947", licensee_shrep, new Date(2010, 5, 9), new Date(131, 5, 9));
        licence_shrep.save();

        Licence licence_shelton = new Licence("GA031110004234", licensee_shelton, new Date(110, 5, 9), new Date(131, 5, 9));
        licence_shelton.save();

        Licence licence_shawn = new Licence("GA032011005201", licensee_shawn, new Date(110, 5, 9), new Date(131, 02, 11));
        licence_shawn.save();

        Licence licence_prabhav = new Licence("GA0320140000143", licensee_prabhav, new Date(110, 5, 9), new Date(134, 07, 01));
        licence_prabhav.save();

        Licence licence_amruta = new Licence("GA07201000419000", licensee_amruta, new Date(110, 5, 9), new Date(131, 07, 9));
        licence_amruta.save();

        Licence licence_roshan = new Licence("GA0320120002528", licensee_roshan, new Date(110, 5, 9), new Date(130, 11, 5));
        licence_roshan.save();

        Licence licence_siddhi = new Licence("GA0320130002232", licensee_siddhi, new Date(110, 5, 9), new Date(133, 29, 5));
        licence_siddhi.save();

        Licence licence_punit = new Licence("GA0420120000232", licensee_punit, new Date(110, 5, 9), new Date(133, 26, 5));
        licence_punit.save();

        Licence licence_ruchita = new Licence("GA0320110004635", licensee_ruchita, new Date(110, 5, 9), new Date(132, 02, 02));
        licence_ruchita.save();

        Licence licence_gautam = new Licence("GA0320110000081", licensee_gautam, new Date(110, 5, 9), new Date(131, 28, 9));
        licence_gautam.save();

        Licence licence_vaibhav = new Licence("GA082013000966", licensee_vaibhav, new Date(110, 5, 9), new Date(131, 9, 01));
        licence_vaibhav.save();

        Licence licence_diksha = new Licence("GA0320130003249", licensee_diksha, new Date(110, 5, 9), new Date(133, 17, 02));
        licence_diksha.save();

        // VEHICLE CLASS
        VehicleClass lmv = new VehicleClass("LMV", "LMV");
        lmv.save();

        VehicleClass mcwog = new VehicleClass("MCWOG", "MCWOG");
        mcwog.save();

        VehicleClass mcwg = new VehicleClass("MCWG", "MCWG");
        mcwg.save();

        // VEHICLE OWNER
        VehicleOwner owner = new VehicleOwner(shrep);
        owner.save();

        // VEHICLE
        Vehicle shrep_fzs_blue = new Vehicle("GA05", "K", "7100", "Yamaha FZ-S", "BLUE", lmv, owner);
        shrep_fzs_blue.save();

        Vehicle omni = new Vehicle("GA5", "B", "7326", "MARUTI SUZUKI OMNI", "BLUE", lmv, owner);
        omni.save();

        Vehicle shrep_fzs_red = new Vehicle("GA08", "F", "8133", "Yamaha FZ-S", "RED", lmv, owner);
        shrep_fzs_red.save();

        Vehicle liva = new Vehicle("GA07", "C", "2775", "TOYOTA LIVA", "BLUE", lmv, owner);
        liva.save();

        Vehicle swift_black = new Vehicle("GA07", "F", "4523", "MARUTI SUZUKI SWIFT", "BLACK", lmv, owner);
        swift_black.save();

        Vehicle swift_blue = new Vehicle("GA04", "C", "9079", "MARUTI SUZUKI SWIFT", "BLUE", lmv, owner);
        swift_blue.save();

        Vehicle xwnon = new Vehicle("GA04", "C", "7100", "TATA XENON", "WHITE", lmv, owner);
        xwnon.save();

        Vehicle gypsy = new Vehicle("GA07", "K", "7100", "MARUTI SUZUKI GYPSY", "BLUE", lmv, owner);
        gypsy.save();

        Vehicle city = new Vehicle("GA05", "K", "9079", "HONDA CITY", "BLUE", lmv, owner);
        city.save();

        Vehicle i10 = new Vehicle("GA08", "K", "9079", "HUNDAI I10", "GREEN", lmv, owner);
        i10.save();

        // Licence offence
        LicenceOffence shrep_no_helmet = new LicenceOffence(licence_shrep, new Date(111, 9, 9), noHelmet);
        shrep_no_helmet.save();

        LicenceOffence shrep_no_helmet_2 = new LicenceOffence(licence_shrep, new Date(114, 11, 10), noHelmet);
        shrep_no_helmet_2.save();

        // Vehicle offence
        VehicleOffence fzs_no_pollution = new VehicleOffence(shrep_fzs_blue, new Date(112, 5, 4), pollution);
        fzs_no_pollution.save();

        VehicleOffence fzs_no_pollution_2 = new VehicleOffence(shrep_fzs_blue, new Date(113, 1, 28), pollution);
        fzs_no_pollution_2.save();

        Log.i(TAG, "Second offence : " + fzs_no_pollution_2);


        //Log.d(TAG, "Vehicle Offence : " + CollectionUtil.toString(VehicleOffence.getAll()));
        //Log.d(TAG, "Vehicle for Offence : " + CollectionUtil.toString(VehicleOffence.getForVehicle(shrep_fzs_blue)));

        //Log.d(TAG, "Vehicles : " + CollectionUtil.toString(Vehicle.getAll()));
        //Log.d(TAG, "Offence Types : " + CollectionUtil.toString(OffenceType.getAll()));
        //Log.d(TAG, "Search by licence : " + Licence.searchByNumber("79947"));
        //Log.d(TAG, "Search by number : " + Vehicle.findByNumber("7100"));

       /* new Vehicle("GA5", "K", "7100", "Yamaha FZ-S", "BLUE").save();

        OffenceType noHelmet = new OffenceType("NH", "No helmet", 500);
        noHelmet.save();

        OffenceType notWearingSeatBelt = new OffenceType("NSB", "Not wearing a seat belt", 300);
        notWearingSeatBelt.save();

        OffenceType tintedGlass = new OffenceType("TG", "tinted glass", 1000);
        tintedGlass.save();

        OffenceType noDrivingLicence = new OffenceType("NDL", "No driving licence", 400);
        noDrivingLicence.save();

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
        // Log.d(TAG, "Offences :" + CollectionUtil.toString(Offence.getAll()));*/
    }
}
