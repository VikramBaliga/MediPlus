package projects.android.my.mediplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DrugDetails extends AppCompatActivity {

    TextView txtDrugNameDetails,txtDrugDescDetail,txtDrugPriceDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);
        Intent intent = getIntent();

        txtDrugNameDetails = (TextView)findViewById(R.id.txtDrugNameDetail);
        txtDrugDescDetail = (TextView) findViewById(R.id.txtDrugDescDetail) ;
        txtDrugPriceDetail = (TextView) findViewById(R.id.txtDrugPriceDetail);

        txtDrugNameDetails.setText(intent.getStringExtra("DRUG_NAME"));
        txtDrugDescDetail.setText(intent.getStringExtra("DRUG_DESC"));
        txtDrugPriceDetail.setText(intent.getStringExtra("DRUG_PRICE"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.drug_details,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.home:
                Log.i("DrugDetails","Home");
                Intent homeIntent = new Intent(DrugDetails.this,MainActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.share:
                Log.i("DrugDetails","Share");
                break;
            case R.id.edit:
                Log.i("DrugDetails","Edit");
                Intent editDrug = new Intent(DrugDetails.this,NewDrug.class);
                editDrug.putExtra("operation","edit");
                editDrug.putExtra("DRUG_NAME",txtDrugNameDetails.getText().toString());
                editDrug.putExtra("DRUG_DESC",txtDrugDescDetail.getText().toString());
                editDrug.putExtra("DRUG_PRICE",txtDrugPriceDetail.getText().toString());
                startActivity(editDrug);
                break;
        }
        return  true;
    }
}
