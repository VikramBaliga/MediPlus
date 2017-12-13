package projects.android.my.mediplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class DrugAlarmManager extends AppCompatActivity {

    int yr,month,day,hour,min;
    String desc;
    EditText alarmMsg;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_alarm_manager);

        alarmMsg = (EditText) findViewById(R.id.etMsg);
        TimePicker alarmTime = (TimePicker) findViewById(R.id.tpalarmTime);

        alarmTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                Log.i("Hour",String.valueOf(hourOfDay));
                Log.i("Min",String.valueOf(minute));
                hour = hourOfDay;
                min=minute;
            }
        });

        DatePicker alarmDate = (DatePicker) findViewById(R.id.dpalarmDate);
        alarmDate.init(2017, 12, 11, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Log.i("Y",String.valueOf(year));
                Log.i("M",String.valueOf(monthOfYear));
                Log.i("D",String.valueOf(dayOfMonth));

                yr=year;
                month=monthOfYear;
                day=dayOfMonth;
            }
        });

        MediPlusDB mediPlusDB = new MediPlusDB(this);
        db=mediPlusDB.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.alarm_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.saveAlarm :
                Log.i("AlarmOption","Save Alarm");
                Log.i("Alarm",""+yr+" "+month+" "+day+" "+hour+" "+min);

                AlarmManager alarmManager =  (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi = PendingIntent.getActivity(this,100,new Intent(this,Notify.class),0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.YEAR,yr);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DATE,day);
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,min);

                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);

                Toast.makeText(this,"Alarm Set",Toast.LENGTH_LONG).show();

                ContentValues values = new ContentValues();

                desc = alarmMsg.getText().toString();

                values.put("ALARM_DESC",desc);
                int monthVal = month+1;
                values.put("ALARM_DATE",yr+"/"+monthVal+"/"+day+" "+hour+":"+min);

                db.insert("tbtAlarms",null,values);

                Intent list = new Intent(this,AlarmList.class);
                startActivity(list);
                break;

        }

        return true;
    }
}
