package com.example.template_dpr_now;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class InputAspirasi extends AppCompatActivity implements View.OnClickListener {

    private static final String CHANNEL_ID = ".notificationDemo.channelId";
    private static final int PICK_IMAGE_REQUEST=1;
    private static final String[] temp = new String[]{
            "Arif", "Aan", "Bambang", "Budi", "Babeh", "Cece"};
    EditText text3,text4,text5,text6;
    AutoCompleteTextView text1, text2;
    TextView txtTime,txtDate, Lihat;
    Button Save;
    Spinner spinner;
    RadioGroup radioGroup;
    DatabaseManager mDatabase;
    ImageButton back;
    CheckBox cb1, cb2, cb3, cb4;
    ArrayList Selection;
    private int  mHour, mMinute, mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        this.setTheme(R.style.DefaultTheme);

        text1 = (AutoCompleteTextView) findViewById(R.id.actv);
        text2 =(AutoCompleteTextView) findViewById(R.id.emailview);
        text3 = (EditText) findViewById(R.id.phoneview);
        text4 = (EditText) findViewById(R.id.Date);
        text5 = (EditText) findViewById(R.id.Time);
        text6 = (EditText) findViewById(R.id.essai);
        cb1 = (CheckBox) findViewById(R.id.checkbox1);
        cb1.setOnClickListener(this);
        cb2 = (CheckBox) findViewById(R.id.checkbox2);
        cb2.setOnClickListener(this);
        cb3 = (CheckBox) findViewById(R.id.checkbox3);
        cb3.setOnClickListener(this);
        cb4 = (CheckBox) findViewById(R.id.checkbox4);
        cb4.setOnClickListener(this);

        txtTime = (EditText) findViewById(R.id.Time);
        txtTime.setOnClickListener(this);
        txtDate = (EditText) findViewById(R.id.Date);
        txtDate.setOnClickListener(this);

        String[] test = getResources().getStringArray(R.array.Test);

        spinner = (Spinner) findViewById(R.id.spinner);
        radioGroup = (RadioGroup) findViewById(R.id.rb);
        Save = (Button) findViewById(R.id.simpan);
        Lihat = (TextView) findViewById(R.id.Viewpilihan);

        AutoCompleteTextView editText = findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, temp);
        editText.setAdapter(adapter);

        back = findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InputAspirasi.this, MainActivity.class));
                finish();
            }
        });

        Save.setOnClickListener(this);
        Lihat.setOnClickListener(this);


        mDatabase = new DatabaseManager(this);
    }

    private void addpilihan(){
        String name = text1.getText().toString().trim();
        String email = text2.getText().toString().trim();
        String phone = text3.getText().toString().trim();
        String time = text4.getText().toString().trim();
        String date = text5.getText().toString().trim();

        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-YYYY");
        try {
            Date baru = dt.parse(date);
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("Baru = " + dt1.format(date));
            date = dt1.format(baru);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String essai = text6.getText().toString().trim();
        String pilihan = spinner.getSelectedItem().toString().trim();

//        if (cb1.isChecked()){
//            Selection.add("AA");
//        } else {
//            Selection.remove("AA");
//        }
//
//        if (cb2.isChecked()){
//            Selection.add("BB");
//        } else {
//            Selection.remove("BB");
//        }
//
//        if (cb3.isChecked()){
//            Selection.add("CC");
//        } else {
//            Selection.remove("CC");
//        }
//
//        if (cb4.isChecked()){
//            Selection.add("DD");
//        } else {
//            Selection.remove("DD");
//        }

        if (name.isEmpty()){
            text1.setError("pengisian nama diperlukan");
            text1.requestFocus();
            return ;
        }

        if (email.isEmpty()){
            text2.setError("pengisian alamat email diperlukan");
            text2.requestFocus();
            return ;
        }

        if (phone.isEmpty()) {
            text3.setError("pengisian nomor handphone diperlukan");
            text3.requestFocus();
            return ;
        }

        if (time.isEmpty()){
            text4.setError("pengisian waktu diperlukan");
            text4.requestFocus();
            return ;
        }

        if (date.isEmpty()) {
            text5.setError("pengisian tanggal diperlukan");
            text5.requestFocus();
            return ;
        }

        if(essai.isEmpty()) {
            text6.setError("pengisian keterangan essai diperlukan");
            text6.requestFocus();
            return ;
        }

        if (mDatabase.addpilihan(name, email, phone, date, time, essai, pilihan)){
            //Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InputAspirasi.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(InputAspirasi.this, 0, intent, 0);

            Notification.Builder builder = new Notification.Builder(InputAspirasi.this);

            Notification notification = builder.setContentTitle("Notifikasi Baru")
                    .setContentText("Pegawai selesai ditambahkan")
                    .setTicker("Pesan baru")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent).build();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID);
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "NotificationDemo",
                        IMPORTANCE_DEFAULT
                );
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, notification);
            startActivity(intent);
        }

        else
        Toast.makeText(this, "Could not add employee", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick (View view){

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get (Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        switch (view.getId()) {
            case R.id.simpan:

                addpilihan();

                break;
            case R.id.Viewpilihan:

                startActivity(new Intent(this, Aspirasi.class));
                finish();

                break;

            case R.id.Date:

                final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int dayOfMonth, int month, int year) {
                                String tanggal = dayOfMonth + "-" + month + "-" + year;

                                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = dt.parse(tanggal);
                                    SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YYYY");
                                    //System.out.println("Baru = " + dt1.format(date));
                                    tanggal = dt1.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                txtDate.setText(tanggal);
                            }
                        }, mYear,mMonth,mDay);
                datePickerDialog.show();

                break;

            case R.id.Time:

                final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;
        }
    }
}
