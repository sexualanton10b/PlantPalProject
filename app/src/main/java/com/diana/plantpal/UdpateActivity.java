package com.diana.plantpal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UdpateActivity extends AppCompatActivity {
    EditText title_input, lastday_input, period_input;
    Button change_button, update_button, delete_button;
    String id, name, lastday, period, nextday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpate);
        title_input = findViewById(R.id.title_input2);
        lastday_input = findViewById(R.id.lastday_input2);
        period_input = findViewById(R.id.period_input2);
        change_button = findViewById(R.id.change_button);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);
        //Сначала вызываем метод
        getaAndSetIntentData();
        ActionBar ab=getSupportActionBar();
        if (ab!=null){
            ab.setTitle(name);
        }
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(UdpateActivity.this);
                name=title_input.getText().toString().trim();
                lastday=lastday_input.getText().toString().trim();
                period=period_input.getText().toString().trim();
                // И только потом мы вызываем это
                myDB.updateData(id, name, lastday, Integer.valueOf(period));
                modifyNotification();
            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(UdpateActivity.this);
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                lastday = today.format(formatter);
                myDB.updateData(id, name, lastday, Integer.valueOf(period));
                modifyNotification();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });


    }
    void modifyNotification(){
        LocalDate LastDay=date(lastday);
        LastDay = LastDay.plusDays(Integer.parseInt(period));
        Calendar alarmTime = convertToLocalDateTime(LastDay);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager.AlarmClockInfo alarmClockInfo=new AlarmManager.AlarmClockInfo( alarmTime.getTimeInMillis(), getAlarmInfoPendingIntent());
        if (Build.VERSION.SDK_INT >= 31) {
            if (!alarmManager.canScheduleExactAlarms()) {
                // Если разрешение не предоставлено, запросите его у пользователя
                new Intent(
                        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                        Uri.parse("package:" + getPackageName())
                );
            } else {
                // Разрешение уже предоставлено, можно использовать AlarmManager
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(), getAlarmActionPendingIntent());
            }
        } else {
            // Версия SDK меньше 23, разрешение SET_ALARM не требуется
            String testTime = String.valueOf(alarmTime.getTimeInMillis());
            Log.d("test",testTime);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(), getAlarmActionPendingIntent());
        }
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getAlarmActionPendingIntent();

        if (alarmManager != null && alarmIntent != null) {
            alarmManager.cancel(alarmIntent);
        }
    }
    public static LocalDate date(String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(day, formatter);
    }
    private PendingIntent getAlarmInfoPendingIntent(){
        Intent alarmInfoIntent=new Intent(this, MainActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_IMMUTABLE);
    }
    private PendingIntent getAlarmActionPendingIntent(){
        Intent intent=new Intent(this, AlarmReceiver.class );
        intent.setAction("alarm_action");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("name", name);
        intent.putExtra("id", Integer.parseInt(id));
        return PendingIntent.getBroadcast(this, Integer.parseInt(id), intent, PendingIntent.FLAG_IMMUTABLE);
    }
    private Calendar convertToLocalDateTime(LocalDate localDate) {
        // Создаем LocalTime с нужным временем (7:00)
        LocalTime localTime = LocalTime.of(7, 0, 0, 0);
        // Создаем LocalDateTime из LocalDate и LocalTime
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        // Создаем Calendar и устанавливаем дату и время
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                localDateTime.getYear(),
                localDateTime.getMonthValue() - 1, // Месяцы в Calendar начинаются с 0
                localDateTime.getDayOfMonth(),
                localDateTime.getHour(),
                localDateTime.getMinute(),
                localDateTime.getSecond()
        );
        Log.d("Tag", String.valueOf(calendar));
        return calendar;
    }
    void getaAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("lastday")
                && getIntent().hasExtra("period") && getIntent().hasExtra("nextday")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            lastday = getIntent().getStringExtra("lastday");
            period = getIntent().getStringExtra("period");
            nextday=getIntent().getStringExtra("nextday");
            //Setting Intent Data
            title_input.setText(name);
            lastday_input.setText(lastday);
            period_input.setText(period);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Удалить "+name+"?");
        builder.setMessage("Вы уверены, что хотите удалить "+name+"?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(UdpateActivity.this);
                myDB.deleteOneRow(id);
                cancelAlarm();
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }
}