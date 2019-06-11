package com.foodbucks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import com.foodbucks.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class AddStoreActivity extends AppCompatActivity {
    private EditText newStoreNameEditText;
    private EditText newStoreDescriptionEditText;
    private EditText newStoreWebsiteEditText;
    private Button createStoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_store);

        newStoreNameEditText = findViewById (R.id.newStoreNameEditText);
        newStoreDescriptionEditText = findViewById (R.id.newStoreDescriptionEditText);
        newStoreWebsiteEditText = findViewById (R.id.newStoreWebsiteEditText);
        createStoreButton = findViewById (R.id.createStoreButton);

        createStoreButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String url = AppConfig.URL_POST_RECIPE;
                RequestQueue requestQueue = Volley.newRequestQueue(AddStoreActivity.this);
                JSONObject postparams = new JSONObject ();
                try {
                    postparams.put ("name", newStoreNameEditText.getText ());
                    postparams.put ("description", newStoreDescriptionEditText.getText ());
                    postparams.put ("website", newStoreWebsiteEditText.getText ());
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.POST,
                        url, postparams,
                        new Response.Listener<JSONObject> () {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString ("name") + "Was added", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {

                                    Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
                                    e.printStackTrace ();
                                }
                            }
                        },
                        new Response.ErrorListener () {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage (), Toast.LENGTH_LONG).show();
                            }
                        });
                requestQueue.add (jsonObjReq);
            }
        });
    }
}
