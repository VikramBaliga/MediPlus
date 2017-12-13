package projects.android.my.mediplus;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AlarmList extends AppCompatActivity {

    ListView alarmList;
    TextView txtMsg;
    String[]  alarmDesc;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        alarmList = (ListView) findViewById(R.id.lvAlarmList);
        txtMsg = (TextView) findViewById(R.id.txtNoAlarm);

        MediPlusDB mediPlusDB = new MediPlusDB(this);

        db = mediPlusDB.getWritableDatabase();

        LoadData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();
    }

    private void LoadData()
    {
        Cursor cursor = db.query("tbtAlarms",null,null,null,null,null,null);

        if (cursor.getCount() > 0)
        {
            txtMsg.setVisibility(View.INVISIBLE);
            alarmDesc = new String[cursor.getCount()];
            int pos=0;
            cursor.moveToFirst();
            do
            {
                alarmDesc[pos]=cursor.getString(1)+" "+cursor.getString(2);
                pos++;

            }while (cursor.moveToNext());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alarmDesc);
            alarmList.setAdapter(adapter);
        }
        else
        {
            alarmList.setVisibility(View.INVISIBLE);
            txtMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.alarm_list_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.home :
                Intent home = new Intent(this,MainActivity.class);
                startActivity(home);
            break;
            case R.id.newAlarm:
                Intent newalarm = new Intent(this,DrugAlarmManager.class);
                startActivity(newalarm);
                break;
            case R.id.shopDrug:
                Intent shop = new Intent(this,ShopCart.class);
                startActivity(shop);
                break;
        }

        return true;
    }
}
