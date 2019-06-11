package com.foodbucks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView2;
    private LinearLayout recipesLinearLayout;
    private JSONObject filterJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesLinearLayout = findViewById (R.id.recipesLinearLayout);

        filterJSON = new JSONObject ();
        try {
            filterJSON.put ("name", "");
            filterJSON.put ("cost", 0);
            filterJSON.put ("rating", 0);
            filterJSON.put ("votesNumber", 0);
            filterJSON.put ("cookingTime", 0);
            filterJSON.put ("includedProducts", new JSONArray ());
            filterJSON.put ("includingBrands", new JSONArray ());
            filterJSON.put ("onlyStores", new JSONArray ());
        } catch (JSONException e){

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.recipeDetailsToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GetAndListRecipes();

        textView2 = (TextView) findViewById (R.id.textView2);
        final Button searchButton = findViewById (R.id.search_button);
        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FilterRecipes ();
            }
        });
    }

    public void GetAndListRecipes(){
        String url = AppConfig.URL_GET_ALL_RECIPES;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

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
                                JSONObject recipe = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String id = recipe.getString ("id");
                                String name = recipe.getString("name");
                                String description = recipe.getString("description");
                                String calories = recipe.getString("calories");
                                Button newRecipe = new Button (MainActivity.this);
                                //textAppearance="@style/TextAppearance.AppCompat.Large"
                                newRecipe.setTextColor (Color.rgb (0, 0, 0));
                                newRecipe.setTextAppearance (R.style.TextAppearance_AppCompat_Small);
                                newRecipe.setText(name);
                                newRecipe.setId (i);
                                newRecipe.setTag (id);
                                newRecipe.setOnClickListener (new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (MainActivity.this, RecipeDetailsActivity.class);
                                        intent.putExtra ("recipeId", v.getTag ().toString ());
                                        startActivity(intent);
                                    }
                                });
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                recipesLinearLayout.addView (newRecipe, lp);
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

    public void FilterRecipes(){
        String url = AppConfig.URL_POST_RECIPE;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
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
                                JSONObject recipe = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String id = recipe.getString ("id");
                                String name = recipe.getString("name");
                                String description = recipe.getString("description");
                                String calories = recipe.getString("calories");
                                Button newRecipe = new Button (MainActivity.this);
                                //textAppearance="@style/TextAppearance.AppCompat.Large"
                                newRecipe.setTextColor (Color.rgb (0, 0, 0));
                                newRecipe.setTextAppearance (R.style.TextAppearance_AppCompat_Small);
                                newRecipe.setText(name);
                                newRecipe.setId (i);
                                newRecipe.setTag (id);
                                newRecipe.setOnClickListener (new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (MainActivity.this, RecipeDetailsActivity.class);
                                        intent.putExtra ("recipeId", v.getTag ().toString ());
                                        startActivity(intent);
                                    }
                                });
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                recipesLinearLayout.addView (newRecipe, lp);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage ());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String> ();
                try {
                    List<Map<String, String>> includedProducts;
                    for(int i = 0; i < filterJSON.getJSONArray ("includedProducts").length (); i++){
                    }

                    params.put("name", filterJSON.getString ("name"));
                    params.put("rating", filterJSON.getString ("rating"));
                    params.put("votesNumber", filterJSON.getString ("votesNumber"));
                    params.put("cookingTime", filterJSON.getString ("cookingTime"));
                    params.put("includedProducts", filterJSON.getString ("includedProducts"));
                    params.put("includingBrands", filterJSON.getString ("includingBrands"));
                    params.put("onlyStores", filterJSON.getString ("onlyStores"));

                } catch (JSONException e) {
                    e.printStackTrace ();
                }

                return params;
            }
        };

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.browse_recipes) {
            Toast.makeText(getApplicationContext(), "Reloading recipes...", Toast.LENGTH_SHORT).show();
            recipesLinearLayout.removeAllViews ();
            GetAndListRecipes();
        } else if (id == R.id.add_recipe) {
            startActivity(new Intent (MainActivity.this, AddRecipeActivity.class));

        } else if (id == R.id.add_product) {
            startActivity(new Intent (MainActivity.this, ProductsActivity.class));
        } else if (id == R.id.add_store) {
            startActivity(new Intent (MainActivity.this, StoresActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
