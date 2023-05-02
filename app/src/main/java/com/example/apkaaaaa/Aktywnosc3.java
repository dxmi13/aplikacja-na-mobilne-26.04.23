package com.example.apkaaaaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Aktywnosc3 extends AppCompatActivity {

    TextView userDataTextView;
    Button sendSmsButton;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc3);

        if (ContextCompat.checkSelfPermission(Aktywnosc3.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Aktywnosc3.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        userDataTextView = findViewById(R.id.imieTextView);
        sendSmsButton = findViewById(R.id.sendSmsButton);

        Intent intent = getIntent();
        String userData = intent.getStringExtra("userData");
        phoneNumber = intent.getStringExtra("telefon");
        String user = intent.getStringExtra("user");

        userDataTextView.setText(userData);

        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }

    private void sendSms() {
        String message = "ABNCDKDFJ";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(this, "Wiadomość do " + phoneNumber + "została wysłana. Treść: "+ message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.aktywnosc1:
                aktywnosc1();
                return true;
            case R.id.aktywnosc2:
                aktywnosc2();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aktywnosc1(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void aktywnosc2(){
        Intent i = new Intent(this, Aktywnosc2.class);
        startActivity(i);
    }
}
