package com.cybercad.challan.service.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/*
    TODO :  Communicate status with the calling activity
    TODO :  Pair printer if not paired
 */
public class BluetoothPrinterImpl implements BluetoothPrinter {
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private OutputStream mOutputStream;
    private Context context;
    private BluetoothSocket mSocket;

    public BluetoothPrinterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void connect(String printerName) {
        if (printerName == null) {
            return;
        }
        BluetoothDevice printer = null;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            throw new BluetoothNotAvailableException("Device does not have bluetooth");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device != null && printerName.equalsIgnoreCase(device.getName())) {
                    printer = mBluetoothAdapter.getRemoteDevice(device.getAddress());
                    new ConnectTask(printer).execute();
                }
            }
        }
        if (printer == null) {
            throw new NotPairedException("Printer not paired");
        }
    }

    @Override
    public void print(String message) {
        if (mOutputStream == null) {
            return;
        }
        try {
            mOutputStream.write(message.getBytes("GBK"));
            mOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                //ignore
            }
            mOutputStream = null;
        }
    }

    @Override
    public void pair(BluetoothDevice device) {
        try {
            Class<?> cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class<?>[] par = {};
            Method method = cl.getMethod("createBond", par);
            method.invoke(device);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private class ConnectTask extends AsyncTask<Void, Integer, Long> {
        BluetoothDevice device;
        String error = "";

        public ConnectTask(BluetoothDevice device) {
            this.device = device;
        }

        protected Long doInBackground(Void... urls) {
            long result = 0;
            try {
                mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
                mSocket.connect();
                mOutputStream = mSocket.getOutputStream();
                result = 1;
            } catch (IOException e) {
                e.printStackTrace();
                error = e.getMessage();
            }
            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
            Toast.makeText(BluetoothPrinterImpl.this.context, "Connected to printer", Toast.LENGTH_LONG).show();
        }
    }
}
