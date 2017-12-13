package projects.android.my.mediplus;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BuyMedicine extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        intent = getIntent();

        TextView prodName = (TextView) findViewById(R.id.txtProdName);
        TextView prodPrice = (TextView) findViewById(R.id.txtProdPrice);
        final EditText etQuan = (EditText) findViewById(R.id.etQuantity);
        final TextView totalCost = (TextView) findViewById(R.id.txtCost);

        etQuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                int price = 0,quan=0;
               if(s.length()==0)
               {
                 totalCost.setText("0");
               }
               else
               {
                   price=Integer.parseInt(intent.getStringExtra("pPrice"));
                   quan=Integer.parseInt(etQuan.getText().toString());

                   totalCost.setText(String.valueOf(price*quan));
               }
            }
        });


        prodName.setText(intent.getStringExtra("pName"));
        prodPrice.setText(intent.getStringExtra("pPrice"));


    }
}
