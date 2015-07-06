package com.cybercad.challan.service.print;

import android.bluetooth.BluetoothDevice;

public interface BluetoothPrinter {

    void connect(String printerName);

    void print(String message);

    void disconnect();

    void pair(BluetoothDevice device);

}
