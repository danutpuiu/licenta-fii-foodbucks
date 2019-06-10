package com.foodbucks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView2;
    private LinearLayout recipesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesLinearLayout = findViewById (R.id.recipesLinearLayout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.recipeDetailsToolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        /*
        //====String GET method
        //=====================
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = AppConfig.URL_GET_ALL_RECIPES;
        //String url = "https://www.google.com";
        StringRequest stringRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView2.setText("Response is: "+ response.toString ());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView2.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        */
        /*
        //====Dynamic buttons method
        //==========================
        Button newRecipe = new Button (this);
        newRecipe.setText("New recipe");
        newRecipe.setId (0);
        LinearLayout ll = (LinearLayout) findViewById (R.id.recipesLinearLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView (newRecipe, lp);
        */
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
