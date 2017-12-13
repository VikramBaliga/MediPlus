package projects.android.my.mediplus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * Created by User on 09-12-2017.
 */

public class DrugListClickListner implements AdapterView.OnItemClickListener
{
    Context context;
    public DrugListClickListner(Context context)
    {
        this.context=context;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
            TextView drugName = (TextView) view.findViewById(R.id.txtDrugName);
            TextView drugPrice = (TextView) view.findViewById(R.id.txtDrugPrice) ;
            TextView drugDesc = (TextView) view.findViewById(R.id.txtDrugDesc) ;
            Intent myIntent = new Intent(context, DrugDetails.class);
            myIntent.putExtra("DRUG_NAME",drugName.getText().toString());
            myIntent.putExtra("DRUG_DESC",drugDesc.getText().toString());
            myIntent.putExtra("DRUG_PRICE",drugPrice.getText().toString());

            context.startActivity(myIntent);
    }
}
