package com.example.cutsomnavigation;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.time.LocalDate;

public class attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner1;
    Spinner spinner2;
    private Integer images[] ={R.drawable.student,R.drawable.student1,R.drawable.student2,R.drawable.u1,R.drawable.u2,R.drawable.u3,R.drawable.u4,R.drawable.u5};
    String[] regds = {"20B91A05U1","20B91A05U2","20B91A05U3","20B91A05U4","20B91A05U5"};
    String[]  tempAttendance = {"0","0","0","0","0"};
    private int currimage = 0;
    NfcAdapter nfcAdapter;
    TextView varRegd;
    Button viewb,updateb,done,btndeny;
    DBHelper DB;
    REPORThelper RH;
    String section;
    String teach;
    int pcount=0;
    boolean check=false;
    String dating,teacheranddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_activity);
        Spinner spinner1=findViewById(R.id.spinner1);
        Spinner spinner2=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.teacher, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.section, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        varRegd = findViewById(R.id.getregd);
        viewb=findViewById(R.id.btnView);
        done = findViewById(R.id.done);
        btndeny = findViewById(R.id.btndeny);
        updateb=findViewById(R.id.btnShow);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        setInitialImage();
        DB = new DBHelper(this);
        RH = new REPORThelper(this);
        for (int i = 0; i < regds.length; i++) {
            Boolean checkinsertdata = DB.insertfirstdata(regds[i]);
        }

//----------------------------UPDATE DB _____________________________________________________________________________________________
        updateb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regdTXT = varRegd.getText().toString().substring(13);
                Boolean checkupdatedata = DB.updateuserdata(regdTXT);
                int i=Arrays.asList(regds).indexOf(regdTXT);
                if(checkupdatedata==true)
                {Toast.makeText(attendance.this, "PRESENT", Toast.LENGTH_SHORT).show();
                    tempAttendance[i]="1";
                    pcount=pcount+1;
                    currimage=0;
                    setCurrentImage();
                    varRegd.setText("    SCAN NFC ID CARD");}
                else
                    Toast.makeText(attendance.this, "NO STUDENT", Toast.LENGTH_SHORT).show();
            }
        });
        btndeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                varRegd.setText("   SCAN NFC ID CARD");
                currimage=0;
                setCurrentImage();
                Toast.makeText(attendance.this, "REJECTED STUDENT", Toast.LENGTH_SHORT).show();
            }
        });
//----------------------------VIEW DB _____________________________________________________________________________________________
        viewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata(1);
                Cursor k = DB.getdata(0);
                StringBuffer buffer = new StringBuffer();
                buffer.append("\nPRESENT:\n---------------------\n");
                while(res.moveToNext()){
                    if(res.getString(0)!=null)
                    {buffer.append(res.getString(0)+"\t\t");
                        buffer.append(res.getString(1)+"\n");}
                }
                buffer.append("\n\n");
                buffer.append("\nABSENT:\n---------------------\n");
                while(k.moveToNext()){
                   if(k.getString(0)!=null){
                       buffer.append(k.getString(0)+"\t\t");

                       buffer.append(k.getString(1)+"\n");
                   }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(attendance.this);
                builder.setCancelable(true);
                builder.setTitle("ATTENDANCE\n"+"\n");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(attendance.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                     sendSMS();
                }
                else
                {
                    ActivityCompat.requestPermissions(attendance.this,new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }
    private void setInitialImage(){setCurrentImage();}
    private void setCurrentImage()
    {
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(images[currimage]);
    }
 @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatchSystem();
    }
    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatchSystem();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        readTextFromMessage((NdefMessage) parcelables[0]);
    }
    private void readTextFromMessage(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        if(ndefRecords != null && ndefRecords.length>0){
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            String[] numbers2 = {"0","20B91A05U1","20B91A05U2","20B91A05U3","20B91A05U4","20B91A05U5"};

            int test = Arrays.asList(numbers2).indexOf(tagContent);
            if (test==-1) {
                currimage = 0;
                setCurrentImage();
                varRegd.setText("   SCAN NFC ID CARD");
                Toast.makeText(this, "NO STUDENT", Toast.LENGTH_SHORT).show();
            }
            else
            {
                varRegd.setText("Student ID : "+tagContent);
                currimage=test;
                setCurrentImage();

            }
        }else
        {
            Toast.makeText(this, "No NDEF records found!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, attendance.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }
    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }
    private NdefRecord createTextRecord(String content) {
        try {
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");
            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);
            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLength);
            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        } catch (UnsupportedEncodingException e) {
            Log.e("createTextRecord", e.getMessage());
        }
        return null;
    }
    private NdefMessage createNdefMessage(String content) {
        NdefRecord ndefRecord = createTextRecord(content);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});
        return ndefMessage;
    }
    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }


    public void sendSMS()
    {
        String[] phone = {"6309134317","6309134317","6309134317","6309134317","6309134317"};
        String[] numbers2 = {"20B91A05U1","20B91A05U2","20B91A05U3","20B91A05U4","20B91A05U5"};
        String SMS1 = "Hello,\nWe want to inform you that your child: ";
        String SMS2=" has not attend the school today.\nPlease make sure that this wouldn't happen again.\nThank you,\nSchool.";
        Cursor res = DB.getdata(0);
        StringBuffer buffer = new StringBuffer();
        SmsManager smsManager = SmsManager.getDefault();

        String listing = "";
        for(int i =0;i<tempAttendance.length;i++)
        {
            listing=listing+tempAttendance[i];
            tempAttendance[i]="0";
        }
        if(check==false)
        {
            boolean k = RH.insertreport(teacheranddate,pcount,(regds.length)-pcount,dating,listing);
        }
        if(check==true)
        {
            Toast.makeText(attendance.this, "Attendance done for this section", Toast.LENGTH_SHORT).show();
        }

        Cursor c = RH.getreport(dating);
        //Toast.makeText(attendance.this,""+c.getCount(), Toast.LENGTH_SHORT).show();
        while(res.moveToNext())
        {
            int i=Arrays.asList(numbers2).indexOf(res.getString(0).toUpperCase());
            smsManager.sendTextMessage(phone[i].toString().trim(), null,SMS1+res.getString(0).toUpperCase()+SMS2, null, null);
        }
        for(int i=0;i< regds.length;i++)
        {
            DB.updateback(regds[i]);
        }
        Toast.makeText(this, "Parents are informed", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_EMAIL, new String[]{"teamstackslam@gmail.com"});
        DateTimeFormatter dtf= null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            LocalDateTime now = LocalDateTime.now();
            String date=dtf.format(now);
            Cursor p = DB.getdata(1);
            Cursor a = DB.getdata(0);
            String data="\nPRESENT:\n---------------------\n";

            while(p.moveToNext()){
                data = data+(p.getString(0)).toUpperCase()+"\t\t";
                data = data + p.getString(1)+"\n";
            }
            data=data+"\n\n";
            data=data+"\nABSENT:\n---------------------\n";
            while(a.moveToNext()){

                data=data+(a.getString(0)).toUpperCase()+"\t\t";
                data=data+a.getString(1)+"\n";
            }

            it.putExtra(Intent.EXTRA_SUBJECT, date+"_"+section+"_"+teach);
            it.putExtra(Intent.EXTRA_TEXT, "TODAY ATTENDANCE"+data);
            it.setType("message/rfc822");
            startActivity(Intent.createChooser(it,"Choose MailApp"));


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendSMS();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        section = parent.getItemAtPosition(position).toString();
        teach = parent.getItemAtPosition(position).toString();
        teach=""+section;
        LocalDate date = LocalDate.now();
        String[] numbers = {"20B91A05U1", "20B91A05U2", "20B91A05U3", "20B91A05U4", "20B91A05U5"};

        teacheranddate=""+date+"_"+teach;
        boolean check=RH.checkreport(teacheranddate);
        dating=""+date;

        varRegd.setText("   SCAN NFC ID CARD");
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        int a =1;
    }
}