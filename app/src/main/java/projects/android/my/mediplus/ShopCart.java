package projects.android.my.mediplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopCart extends AppCompatActivity {

    String[] drugName = {"Abacavir","Acamprosate","Acarbose","Acebrophylline","Amisulpride","Azelastine","Haemocoagulase","Halcinonide","Homatropine"};
    String[] drugPrice = {"100","200","300","400","500","600","700","800","900"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        ListView shopList = (ListView) findViewById(R.id.shopList);

        ShopAdapter adapter = new ShopAdapter(drugName,drugPrice);

        shopList.setAdapter(adapter);

        shopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView medicineName = (TextView) view.findViewById(R.id.txtMedicineName);
                TextView medicinePrice = (TextView) view.findViewById(R.id.txtMedicinePrice);

                Intent buyIntent = new Intent(ShopCart.this,BuyMedicine.class);
                buyIntent.putExtra("pName",medicineName.getText().toString());
                buyIntent.putExtra("pPrice",medicinePrice.getText().toString());
                ShopCart.this.startActivity(buyIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.home :
                Intent home = new Intent(ShopCart.this,MainActivity.class);
                startActivity(home);
                break;
            case R.id.sAlarm:
                Intent addAlarm = new Intent(this,AlarmList.class);
                startActivity(addAlarm);
                break;

        }

        return true;
    }

    public class ShopAdapter extends BaseAdapter
    {
        String[] mName,mPrice;
        public ShopAdapter(String[] mName,String[] mPrice)
        {
            this.mName=mName;
            this.mPrice=mPrice;
        }

        @Override
        public int getCount() {
            return mName.length;
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = LayoutInflater.from(ShopCart.this).inflate(R.layout.custom_shop_site,null);
            TextView medicineName = (TextView) view.findViewById(R.id.txtMedicineName);
            TextView medicinePrice = (TextView) view.findViewById(R.id.txtMedicinePrice);

            medicineName.setText(mName[position]);
            medicinePrice.setText(mPrice[position]);

            return  view;
        }
    }
}



