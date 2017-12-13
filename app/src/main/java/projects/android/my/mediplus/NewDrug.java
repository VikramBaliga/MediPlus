package projects.android.my.mediplus;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewDrug extends AppCompatActivity {

    EditText etDrugName,etDrugDesc,etDrugPrice;
    TextView txtHeader;
    SQLiteDatabase db;
    String operation;
    String drugName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_drug);
        Intent intent = getIntent();
        operation =  intent.getStringExtra("operation");

        etDrugName = (EditText) findViewById(R.id.etDrugName);
        etDrugDesc = (EditText) findViewById(R.id.etDrugDesc);
        etDrugPrice = (EditText) findViewById(R.id.etPrice);
        txtHeader = (TextView) findViewById(R.id.txtHeader);

        MediPlusDB mediPlusDB = new MediPlusDB(this);

        db = mediPlusDB.getWritableDatabase();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        txtHeader.setLayoutParams(params);
        if(operation.equals("new"))
        {
            txtHeader.setText("Add Drug Details");

        }
        else
        {
            txtHeader.setText("Edit Drug Details");
            etDrugName.setHint(intent.getStringExtra("DRUG_NAME"));
            etDrugDesc.setHint(intent.getStringExtra("DRUG_DESC"));
            etDrugPrice.setHint(intent.getStringExtra("DRUG_PRICE"));
            drugName = intent.getStringExtra("DRUG_NAME");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_drug,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.save :
                Log.i("NewDrug","Save");
                if(ValidateData())
                {
                    if(operation.equals("new"))
                    {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("DRUG_NAME", etDrugName.getText().toString());
                        contentValues.put("DRUG_DESC", etDrugDesc.getText().toString());
                        contentValues.put("DRUG_PRICE", Integer.parseInt(etDrugPrice.getText().toString()));
                        db.insert("tblDrug", null, contentValues);
                        Toast.makeText(NewDrug.this, "Saved", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Get Id of the drug
                        Log.i("Query",drugName);
                        Cursor cursor = db.rawQuery("SELECT KEY_ID FROM tblDrug where DRUG_NAME = '"+drugName+"'",null);
                        Log.i("Count",String.valueOf(cursor.getCount()));

                        if(cursor!=null)
                        {
                            cursor.moveToFirst();
                            int keyId = cursor.getInt(0);

                            Log.i("Index",String.valueOf(keyId));

                            ContentValues updateContent = new ContentValues();
                            updateContent.put("DRUG_NAME", etDrugName.getText().toString());
                            updateContent.put("DRUG_DESC", etDrugDesc.getText().toString());
                            updateContent.put("DRUG_PRICE", Integer.parseInt(etDrugPrice.getText().toString()));

                           db.update("tblDrug",updateContent,"KEY_ID = "+keyId,null);

                            Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show();
                        }
                    }
                    Intent intent = new Intent(NewDrug.this,DrugDetails.class);
                    intent.putExtra("DRUG_NAME",etDrugName.getText().toString());
                    intent.putExtra("DRUG_DESC",etDrugDesc.getText().toString());
                    intent.putExtra("DRUG_PRICE",etDrugPrice.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(NewDrug.this,"Enter all Fields",Toast.LENGTH_LONG).show();
                }
                break;
        }

        return true;
    }

    boolean ValidateData()
    {

        if(etDrugDesc.length()==0 ||
           etDrugName.length()==0 ||
           etDrugPrice.length()==0)
        {
            return false;
        }
        return  true;
    }

}
