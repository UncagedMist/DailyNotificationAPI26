package kk.techbytecare.dailynotificationapi26;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import kk.techbytecare.dailynotificationapi26.Reciever.Reciever;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //registerAlarm();
    }

    private void registerAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, Reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(calendar.MINUTE, 3);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reminder_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_reminder) {
            showReminder();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showReminder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.layout_reminder,null);

        timePicker = itemView.findViewById(R.id.timePicker);

        builder.setView(itemView);

        builder.setPositiveButton("SET REMINDER", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                saveAlarm();
                Toast.makeText(MainActivity.this, "Alarm set successful...", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveAlarm() {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, Reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        Calendar calendar = Calendar.getInstance();
        Date today = Calendar.getInstance().getTime();

        calendar.set(today.getYear(),today.getMonth(),today.getDay(),timePicker.getHour(),timePicker.getMinute());

        manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        Log.d("DEBUG","Alarm will wake at : "+timePicker.getHour()+":"+timePicker.getMinute());
    }
}
