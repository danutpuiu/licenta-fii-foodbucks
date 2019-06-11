package com.foodbucks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class StoresActivity extends AppCompatActivity {
    private LinearLayout storesLinearLayout;
    private Button addStoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_stores);

        storesLinearLayout = findViewById (R.id.storesLinearLayout);
        addStoreButton = findViewById (R.id.addStoreButton);

        addStoreButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StoresActivity.this, AddStoreActivity.class);
                startActivity(intent);
            }
        });

        GetAndListAllStores ();


    }

    void GetAndListAllStores(){
        String url = AppConfig.URL_GET_ALL_STORES;

        RequestQueue requestQueue = Volley.newRequestQueue(StoresActivity.this);

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
                                final String website = product.getString ("website");
                                final String description = product.getString("description");

                                final TextView newProductTextView = new TextView (StoresActivity.this);
                                newProductTextView.setTextColor (Color.BLACK);

                                newProductTextView.setClickable (true);
                                newProductTextView.setPadding (50, 30, 50, 30);


                                newProductTextView.setText (name + "\n" +
                                        description + "\n" +
                                        website);
                                newProductTextView.setTag (id);

                                newProductTextView.setOnClickListener (new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (StoresActivity.this, StoreDetailsActivity.class);
                                        intent.putExtra ("productId", v.getTag ().toString ());
                                        startActivity(intent);
                                    }
                                });

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                storesLinearLayout.addView (newProductTextView, lp);
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
