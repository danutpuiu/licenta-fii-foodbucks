package com.foodbucks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import com.foodbucks.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductPricesActivity extends AppCompatActivity {

    private TextView productNameAndBrandTextView;
    private TextView productQuantityAndUOMTextView;
    private LinearLayout pricesAndStoresLiniarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_product_prices);



        productNameAndBrandTextView = findViewById (R.id.productNameAndBrandTextView);
        productQuantityAndUOMTextView = findViewById (R.id.productQuantityAndUOMTextView);
        pricesAndStoresLiniarLayout = findViewById (R.id.pricesAndStoresLiniarLayout);


        String url = AppConfig.URL_GET_PRODUCT_DETAILS + getIntent ().getStringExtra ("productId");

        RequestQueue requestQueue = Volley.newRequestQueue (this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                        try {
                            productNameAndBrandTextView.setText (response.getString ("brand") + "'s " +
                                    response.getString ("name"));
                            productQuantityAndUOMTextView.setText (response.getString ("quantity") + " " +
                                    response.getString ("unitOfMeasurement"));


                            JSONArray instructionStepsJSON = response.getJSONArray ("prices");
                            for (int i = 0; i< instructionStepsJSON.length (); i++){
                                JSONObject priceInfo = instructionStepsJSON.getJSONObject (i);
                                final TextView newPriceInfo = new TextView ( ProductPricesActivity.this);
                                newPriceInfo.setTextColor (Color.rgb (0,0,0));
                                newPriceInfo.setClickable (true);
                                newPriceInfo.setText ("Store: " + priceInfo.getString ("storeName") +
                                        " - " +
                                        priceInfo.getString ("price") + " lei");
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                pricesAndStoresLiniarLayout.addView (newPriceInfo, lp);

                                newPriceInfo.setOnClickListener (new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (ProductPricesActivity.this, StoreDetailsActivity.class);
                                        intent.putExtra ("storeName", v.getTag ().toString ());
                                        startActivity(intent);
                                    }
                                });
                            }

                        } catch (JSONException error){
                            Toast.makeText(getApplicationContext(), "Couldn't show the info. Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonObjReq);

    }
}
