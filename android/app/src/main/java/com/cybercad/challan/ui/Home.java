package com.cybercad.challan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cybercad.challan.R;
import com.cybercad.challan.domain.offence.OffenceType;
import com.cybercad.challan.util.CollectionUtil;


public class Home extends ActionBarActivity {

    private static final String TAG = "Home";

   /* private static final String NAME = "BTPrinter";
    private static final UUID SerialPortService_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter mBluetoothAdapter;
    OutputStream mmOutputStream;
    InputStream mmInputStream;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /*mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not found", Toast.LENGTH_LONG).show();
            finish();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device != null && "Micromax AQ4501".equalsIgnoreCase(device.getName())) {
                    Toast.makeText(this, "Found micromax Canvas device", Toast.LENGTH_LONG).show();
                    Thread connectorThread = new Thread(new Connector(device));
                    connectorThread.start();
                }
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (mBluetoothAdapter.isEnabled()) {
          //  mBluetoothAdapter.disable();
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        final Button button = (Button) findViewById(R.id.issue_fine_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,IssueChallan.class));
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* private class Connector implements Runnable {
        private BluetoothServerSocket mmServerSocket;
        private BluetoothSocket mmSocket;
        private final BluetoothDevice device;

        public Connector(BluetoothDevice device) {
            this.device = device;
        }

        @Override
        public void run() {
            BluetoothSocket socket = null;
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(SerialPortService_UUID);
                mmSocket.connect();


                if (socket != null) {
                    print(socket);
                }
            } catch (IOException e) {
                Log.e(TAG, "accept() failed", e);
            }
        }

        private void print(BluetoothSocket socket){
            Thread printerThread = new Thread(new Printer(socket));
            printerThread.start();
        }
    }

    private class Printer implements Runnable {
        private BluetoothSocket socket;

        public Printer(BluetoothSocket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Home.this," Connection established : "+socket.isConnected(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void print(String message){

    }*/
}
