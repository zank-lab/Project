package pl.edu.pwr.s241843.blutacz;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button connect;
    public static String EXTRA_ADDRESS = "98:D3:11:FC:62:97";
    private static String Name = "HC05-Slave";
    private BluetoothAdapter myBluetooth = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = findViewById(R.id.button);
        connect.setOnClickListener(this);

    }
    public void onClick(View v) {
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if ( myBluetooth==null ) {
            Toast.makeText(getApplicationContext(), "Bluetooth device not available", Toast.LENGTH_LONG).show();
            finish();
        }
        else if ( !myBluetooth.isEnabled() ) {
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }

        Intent i = new Intent(MainActivity.this, parameters.class);
        i.putExtra(EXTRA_ADDRESS, EXTRA_ADDRESS);
        startActivity(i);
    }

}
