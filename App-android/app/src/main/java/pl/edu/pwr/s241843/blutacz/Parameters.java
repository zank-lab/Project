package pl.edu.pwr.s241843.blutacz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class Parameters extends AppCompatActivity {
    String adres = null;
    BluetoothAdapter bluetooth = null;
    BluetoothSocket socket = null;
    private boolean polaczono = false;
    private ArrayList<ListviewData> parametry;
    private ListviewData ph,tlen,temp,ntu,ec;
    static final UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        Intent intent = getIntent();
        adres = intent.getStringExtra(MainActivity.EXTRA_ADDRESS);

        new PolaczBluetooth().execute();
        handler = new Handler();
        ListView lista = findViewById(R.id.list);

        addItemsListView();

        final ListviewAdapter adapter = new ListviewAdapter(this,R.layout.adapter, parametry);
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
        String tekst = Odbierz();
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
                lines[i] += " mg/l";
                parametry.get(1).setWartosc(lines[i]);
            } else if (lines[i].contains("ph")) {
                lines[i] = lines[i].replaceAll("ph", "");
                parametry.get(0).setWartosc(lines[i]);
            }
            else if (lines[i].contains("ec")) {
                lines[i] = lines[i].replaceAll("ec", "");
                lines[i] += " mS/cm";
                parametry.get(4).setWartosc(lines[i]);
                System.out.println(lines[i]);
            }
        }

    }

    public void addItemsListView(){
        ListviewData ph = new ListviewData("PH:", "-");
        ListviewData tlen = new ListviewData("Rozpuszczony tlen:", "-");
        ListviewData temp = new ListviewData("Temperatura:", "-");
        ListviewData ntu = new ListviewData("NTU:", "-");
        ListviewData ec = new ListviewData("Przewodność:", "-");
        parametry = new ArrayList<>();
        parametry.add(ph);
        parametry.add(tlen);
        parametry.add(temp);
        parametry.add(ntu);
        parametry.add(ec);
    }

    private String Odbierz() {
        byte[] bufor = new byte[256];
        int bytes;
        InputStream wejscie_tmp = null;
        String value;
        if ( socket != null ) {
            try {
                wejscie_tmp = socket.getInputStream();
                DataInputStream strumien_in = new DataInputStream(wejscie_tmp);
                bytes = strumien_in.read(bufor);
                value = new String(bufor, 0, bytes);
                return value;
            } catch (IOException e) {
                wyswietlWiadomosc("blad przy odbiorze danych");
            }
        }
        return null;
    }



    private void wyswietlWiadomosc(String wiadomosc) {
        Toast.makeText(getApplicationContext(), wiadomosc, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("StaticFieldLeak")
    private class PolaczBluetooth extends AsyncTask<Void, Void, Void> {
        boolean polaczono = true;

        @Override
        protected void onPreExecute() {

        }

        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (socket == null || !polaczono) {
                    bluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice hc = bluetooth.getRemoteDevice(adres);
                    try {
                        if (bluetooth != null)
                        {
                            socket = hc.createRfcommSocketToServiceRecord(hc.getUuids()[0].getUuid());
                        }
                    }
                    catch (NullPointerException e)
                    {
                        try {
                            socket = hc.createRfcommSocketToServiceRecord(DEFAULT_UUID);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    catch (IOException ignored) { }
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    socket.connect();
                }
            } catch (IOException e) {
                polaczono = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!polaczono) {
                wyswietlWiadomosc("nie polaczono");
                finish();
            } else {
                wyswietlWiadomosc("polaczono");
                polaczono = true;
            }

        }


    }
}
