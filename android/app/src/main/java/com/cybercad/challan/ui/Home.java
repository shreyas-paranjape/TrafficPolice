package com.cybercad.challan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cybercad.challan.R;
import com.cybercad.challan.service.print.BluetoothPrinter;


public class Home extends ActionBarActivity {

    private static final String TAG = "Home";
    private BluetoothPrinter bluetoothPrinter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button issueFine = (Button) findViewById(R.id.issue_fine_button);
        issueFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, IssueChallan.class));
                finish();
                //bluetoothPrinter.print("Hello world\r\n");
            }
        });
        //bluetoothPrinter = new BluetoothPrinterImpl(this);
        // TODO Need to make this configurable
        //bluetoothPrinter.connect("MOB-80");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
