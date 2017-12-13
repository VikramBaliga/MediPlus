package projects.android.my.mediplus;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView drugList;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drugList = (ListView) findViewById(R.id.lvDrugList);
        MediPlusDB mediPlusDB = new MediPlusDB(this);
        db=mediPlusDB.getWritableDatabase();

        drugList.setOnItemClickListener(new DrugListClickListner(MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this,"On Resume",Toast.LENGTH_LONG).show();
        Cursor records = db.query("tblDrug",null,null,null,null,null,null);
        if(records.getCount() > 0)
        {
            records.moveToFirst();
            String[] drugName = new String[records.getCount()];
            String[] drugDesc = new String[records.getCount()];
            String[] drugPrice =  new String[records.getCount()];
            int pos = 0;
            do {
                drugName[pos] = records.getString(1);
                drugDesc[pos] = records.getString(2);
                drugPrice[pos] = String.valueOf(records.getInt(3));
                Log.i("DrugList", drugName[pos]+" "+drugDesc[pos]+" "+drugPrice[pos]);
                pos++;
            } while (records.moveToNext());
            DrugListAdapter drugListAdapter = new DrugListAdapter(drugName,drugDesc,drugPrice);
            drugList.setAdapter(drugListAdapter);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.addDrug :
                Log.i("MenuOption","New Drug");
                Intent addDrug = new Intent(MainActivity.this,NewDrug.class);
                addDrug.putExtra("operation","new");
                startActivity(addDrug);
                break;
            case R.id.addAlarm:
                Log.i("MenuOption","New Alarm");
                Intent addAlarm = new Intent(this,AlarmList.class);
                startActivity(addAlarm);
                break;
            case R.id.searchDrug:
                Log.i("MenuOption","Shop");
                Intent shop = new Intent(this,ShopCart.class);
                startActivity(shop);
                break;
        }

        return true;
    }

    public class DrugListAdapter extends BaseAdapter
    {
        String[] drugName;
        String[] drugDesc;
        String[] drugPrice;

        public DrugListAdapter(String[] drugName,String[] drugDesc,String[] drugPrice)
        {
            this.drugName=drugName;
            this.drugDesc=drugDesc;
            this.drugPrice=drugPrice;
        }
        @Override
        public int getCount() {
            return drugName.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.drug_list,null);

            TextView txtDrugName = (TextView) view.findViewById(R.id.txtDrugName);
            TextView txtDrugDesc = (TextView) view.findViewById(R.id.txtDrugDesc);
            TextView txtDrugPrice = (TextView) view.findViewById(R.id.txtDrugPrice);

            txtDrugName.setText(drugName[position]);
            txtDrugDesc.setText(drugDesc[position]);
            txtDrugPrice.setText(drugPrice[position]);


            return  view;
        }
    }

}
