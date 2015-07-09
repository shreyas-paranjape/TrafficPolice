package com.cybercad.challan.service.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/*
    TODO :  Communicate status with the calling activity
 */
public class BluetoothPrinterImpl implements BluetoothPrinter {

    private static final String TAG = BluetoothPrinterImpl.class.getName();
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private final int CONNECT_RETRIES = 3;

    private final Context context;
    private final BluetoothAdapter mBluetoothAdapter;
    private final String printerName;


    private BluetoothDevice printer;
    private String message;
    private CountDownTimer timer;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(TAG, "Device : " + device.getName());
                if (printerName.equalsIgnoreCase(device.getName())) {
                    timer.cancel();
                    mBluetoothAdapter.cancelDiscovery();
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        Log.i(TAG, "Pairing with device.");
                        pair(device);
                    }
                    printer = device;
                    new PrintTask(printer).execute();
                }
            }
        }
    };

    public BluetoothPrinterImpl(Context context, String printerName) {
        this.context = context;
        this.printerName = printerName;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void print(String message) {
        this.message = message;
        connectAndPrint();
    }

    private void connectAndPrint() {
        checkBluetooth();
        enableBluetooth();
        registerReciever();
        discover();
    }

    private void disconnect() {
        try {
            context.unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
        Log.i(TAG, "Done");
    }

    private void checkBluetooth() {
        if (mBluetoothAdapter == null) {
            throw new BluetoothNotAvailableException("Device does not have bluetooth");
        }
    }

    private void enableBluetooth() {
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }

    private void registerReciever() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
    }

    private void discover() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
        startTimer();
    }

    private void startTimer() {
        if (timer == null) {
            Runnable printTask = new Runnable() {
                @Override
                public void run() {
                    print(message);
                }
            };
            timer = new RetryCounter(10000, 1000, CONNECT_RETRIES, printTask) {
                @Override
                public void onFinish() {
                    try {
                        context.unregisterReceiver(mReceiver);
                    } catch (Exception e) {
                    }
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    super.onFinish();
                }
            }.start();
        } else {
            timer.start();
        }
    }

    private void pair(BluetoothDevice device) {
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

    private boolean isConnected() {
        return printer != null;
    }


    private class PrintTask extends AsyncTask<Void, Integer, Integer> {

        BluetoothDevice device;
        private final int PRINT_RETRIES = 3;
        private final AtomicInteger print_retry_counter = new AtomicInteger(0);
        //private CountDownTimer printRetryCounter;

        public PrintTask(BluetoothDevice device) {
            this.device = device;
        }

        protected Integer doInBackground(Void... noData) {
            /*PrintTask printTaskInner = null;
            if (print_retry_counter.get() != 0 && print_retry_counter.get() <= PRINT_RETRIES) {
                printTaskInner = new PrintTask(printer);
                printTaskInner.execute();
            }
            try {
                printMessage();
                if (null != printTaskInner) {
                    printTaskInner.cancel(true);
                }
            } catch (IOException e) {
                Log.i(TAG, "Error : " + e.getMessage());
                return 1;
            }
            return 0;*/


             while (print_retry_counter.get() < PRINT_RETRIES) {
                try {
                    printMessage();
                    print_retry_counter.set(0);
                    return 0;
                } catch (IOException e) {
                    Log.i(TAG, "Error : " + e.getMessage());
                    print_retry_counter.incrementAndGet();
                    continue;
                }
            }
            return 0;

        }

        private void printMessage() throws IOException {
            Log.i(TAG, "Connecting");
            BluetoothSocket mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
            mSocket.connect();
            OutputStream mOutputStream = mSocket.getOutputStream();
            Log.i(TAG, "Printing");
            mOutputStream.write(message.getBytes("GBK"));
            mOutputStream.flush();
            Log.i(TAG, "Closing socket");
            mSocket.close();
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Integer result) {
            disconnect();
        }
    }

    /*
        Retries a task until retries expire.
        cancel() once done
        start() for every run
    */
    private class RetryCounter extends CountDownTimer {
        private final AtomicInteger retry_counter = new AtomicInteger(0);
        private final Runnable task;
        private final int NO_RETRIES;

        public RetryCounter(long millisInFuture, long countDownInterval, int retries, Runnable task) {
            super(millisInFuture, countDownInterval);
            this.NO_RETRIES = retries;
            this.task = task;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "Tick");
        }

        @Override
        public void onFinish() {
            Log.i(TAG, "Timer expired.");
            if (retry_counter.get() < NO_RETRIES) {
                Log.i(TAG, "Restarting");
                task.run();
                retry_counter.incrementAndGet();
            } else {
                Log.i(TAG, "Retries exhausted. exiting");
                retry_counter.set(0);
            }
        }
    }


}