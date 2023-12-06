package com.diana.plantpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
EditText title_input, lastday_input, period_input;
int row;
Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title_input=findViewById(R.id.title_input);
        lastday_input=findViewById(R.id.lastday_input);
        period_input=findViewById(R.id.period_input);
        add_button=findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(AddActivity.this);
                myDB.addPlant(title_input.getText().toString().trim(),
                        lastday_input.getText().toString().trim(),
                        Integer.valueOf(period_input.getText().toString().trim()));
                row= myDB.getLastInsertedRowId();
                LocalDate LastDay=date(lastday_input.getText().toString().trim());
                LastDay = LastDay.plusDays(Integer.parseInt(period_input.getText().toString().trim()));
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
        });
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
        intent.putExtra("name", title_input.getText().toString().trim());
        intent.putExtra("id", row);
        return PendingIntent.getBroadcast(this, row, intent, PendingIntent.FLAG_IMMUTABLE);
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
}