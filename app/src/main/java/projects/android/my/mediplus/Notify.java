package projects.android.my.mediplus;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class Notify extends AppCompatActivity {

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        MediPlusDB mediPlusDB = new MediPlusDB(this);
        db= mediPlusDB.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int month = calendar.get(Calendar.MONTH)+1;
        int am_pm = calendar.get(Calendar.AM_PM);

        if(am_pm == 1)
        {
            hour+=12;
        }

        String temp =calendar.get(Calendar.YEAR)+"/"+month+"/"+calendar.get(Calendar.DATE)+" "+hour+":"+calendar.get(Calendar.MINUTE);

        Cursor cursor = db.rawQuery("SELECT ALARM_DESC FROM tbtAlarms WHERE ALARM_DATE ='"+temp+"'",null);
        cursor.moveToFirst();
        String msgFor = cursor.getString(0);

        Log.i("Count",String.valueOf(cursor.getCount()));

        TextView txtMsg = (TextView) findViewById(R.id.txtMsg);

        txtMsg.setText(msgFor);
    }
}
