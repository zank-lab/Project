package pl.edu.pwr.s241843.blutacz;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button polacz;
    public static String EXTRA_ADDRESS = "98:D3:11:FC:62:97";
    private static String Name = "HC05-Slave";
    private BluetoothAdapter bluetooth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polacz = findViewById(R.id.button);
        polacz.setOnClickListener(this);

    }
    public void onClick(View v) {
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        if ( bluetooth == null ) {
            Toast.makeText(getApplicationContext(), "Brak bluetooth", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (!bluetooth.isEnabled()) {
            Intent wlacz_bluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(wlacz_bluetooth, 1);
        }
        Intent i = new Intent(MainActivity.this, Parameters.class);
        i.putExtra(EXTRA_ADDRESS, EXTRA_ADDRESS);
        startActivity(i);
    }

}
