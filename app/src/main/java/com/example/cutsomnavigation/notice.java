package com.example.cutsomnavigation;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class notice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText MessageText;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_activity);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.section, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        MessageText = findViewById(R.id.MessageText);
        done = findViewById(R.id.done);
        String[] numbers = {"20B91A05U1", "20B91A05U2", "20B91A05U3", "20B91A05U4", "20B91A05U5"};
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(notice.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sendSMS();
                    }
                } else {
                    ActivityCompat.requestPermissions(notice.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendSMS() {
        String[] phone = {"6309134317", "6309134317", "6309134317", "6309134317", "6309134317"};
        Editable SMS = MessageText.getText();
        SmsManager smsManager = SmsManager.getDefault();
        for (int j = 0; j < phone.length; j++) {
            smsManager.sendTextMessage(phone[j].toString().trim(), null, String.valueOf(SMS), null, null);
        }
        Toast.makeText(this, "Students are notified", Toast.LENGTH_SHORT).show();
        MessageText.setText("");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendSMS();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        String text = parent.getItemAtPosition(pos).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
