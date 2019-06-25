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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import com.foodbucks.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductsActivity extends AppCompatActivity {
    private LinearLayout productsLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_products);

        productsLinearLayout = findViewById (R.id.productsLinearLayout);
        GetAndListAllProducts ();
    }

    void GetAndListAllProducts(){
        String url = AppConfig.URL_GET_ALL_PRODUCTS;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(ProductsActivity.this);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements

                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject product = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String id = product.getString ("id");
                                final String name = product.getString("name");
                                final String brand = product.getString ("brand");
                                final String quantity = product.getString("quantity");
                                final String unitOfMeasurement = product.getString ("unitOfMeasurement");

                                final TextView newProductTextView = new TextView (ProductsActivity.this);
                                newProductTextView.setTextColor (Color.WHITE);

                                newProductTextView.setClickable (true);
                                newProductTextView.setPadding (50, 30, 50, 30);


                                newProductTextView.setText (brand + "'s " +
                                        name + " " +
                                        quantity + " " +
                                        unitOfMeasurement);
                                newProductTextView.setTag (id);

                                newProductTextView.setOnClickListener (new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (ProductsActivity.this, ProductPricesActivity.class);
                                        intent.putExtra ("productId", v.getTag ().toString ());
                                        startActivity(intent);
                                    }
                                });

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                productsLinearLayout.addView (newProductTextView, lp);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }




}
