package pl.edu.pwr.s241843.blutacz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class parameters extends AppCompatActivity {
    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    private ArrayList<Wartosci> parametry;
    private Wartosci ph,tlen,temp,ntu;
    static final UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        Intent intent = getIntent();
        address = intent.getStringExtra(MainActivity.EXTRA_ADDRESS);

        new parameters.ConnectBT().execute();
        handler = new Handler();
        ListView lista = findViewById(R.id.list);

        addItemsListView();

        final ParametryAdapter adapter = new ParametryAdapter(this,R.layout.adapter, parametry);
        lista.setAdapter(adapter);

        final Runnable r = new Runnable() {
            public void run() {
                UpdateResults();
                handler.postDelayed(this, 1000);
                adapter.notifyDataSetChanged();
            }
        };
        handler.postDelayed(r, 1000);
    }


    private void UpdateResults(){
        String tekst = receive();
        System.out.println(tekst);
        assert tekst != null;
        String[] lines = tekst.split("\\r?\\n");

        
        for(int i = 0; i<= lines.length-1;i++ ) {
            System.out.println(lines[i]);
            if (lines[i].contains("t")) {
                lines[i] = lines[i].replaceAll("t", "");
                lines[i] += "\u00B0" + "C";
                parametry.get(2).setWartosc(lines[i]);
            }
            //     else if(tekst.contains("ec")){tmp= tekst.replaceAll("ec", "");parametry.get(parametry.indexOf()).setWartosc(tekst);}
            else if (lines[i].contains("zm")) {
                lines[i] = lines[i].replaceAll("zm", "");
                parametry.get(3).setWartosc(lines[i]);
            } else if (lines[i].contains("do")) {
                lines[i] = lines[i].replaceAll("do", "");
                lines[i] += "mg/l";
                parametry.get(1).setWartosc(lines[i]);
            } else if (lines[i].contains("ph")) {
                lines[i] = lines[i].replaceAll("ph", "");
                parametry.get(0).setWartosc(lines[i]);
            }
        }
        


    }
    public void addItemsListView(){
        Wartosci ph = new Wartosci("PH:", "-");
        Wartosci tlen = new Wartosci("Rozpuszczony tlen:", "-");
        Wartosci temp = new Wartosci("Temperatura:", "-");
        Wartosci ntu = new Wartosci("NTU:", "-");
        parametry = new ArrayList<>();
        parametry.add(ph);
        parametry.add(tlen);
        parametry.add(temp);
        parametry.add(ntu);
    }

    private String receive () {
        byte[] buffer = new byte[256];  // buffer store for the stream
        int bytes; // bytes returned from read()
        InputStream tmpIn = null;
        String value;
        if ( btSocket != null ) {

            try {
                tmpIn = btSocket.getInputStream();
                DataInputStream mmInStream = new DataInputStream(tmpIn);
                bytes = mmInStream.read(buffer);
                value = new String(buffer, 0, bytes);
                return value;
            } catch (IOException e) {
                msg("Error");
            }
        }
        return null;
    }



    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {

        }

        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice hc = myBluetooth.getRemoteDevice(address);
                    try {
                        if (myBluetooth != null)
                        {
                            btSocket= hc.createRfcommSocketToServiceRecord(hc.getUuids()[0].getUuid());
                        }
                    }
                    catch (NullPointerException e)
                    {
                        try {
                            btSocket= hc.createRfcommSocketToServiceRecord(DEFAULT_UUID);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    catch (IOException e) { }
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected");
                isBtConnected = true;
            }

        }


    }
}
