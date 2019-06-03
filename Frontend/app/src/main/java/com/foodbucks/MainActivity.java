package com.foodbucks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.R;
import com.foodbucks.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button searchButton = findViewById (R.id.search_button);
        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AddNewRecipeToTheList();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        JSONObject data = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Method.POST, AppConfig.URL_GET_ALL_RECIPES, data, new Response.Listener<JSONObject> () {
            @Override
            public void onResponse(JSONObject response) {
                Log.d ("tag_danut", "Response: " + response.toString ());

                try {
                    JSONObject jsonObject = new JSONObject (response.toString ());

                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
        }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errorTAG_danut", "Error returned" + error.getMessage ());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void AddNewRecipeToTheList(){
        Button newRecipe = new Button (this);
        newRecipe.setText("New recipe");
        newRecipe.setId (0);
        LinearLayout ll = (LinearLayout) findViewById (R.id.recipesLinearLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView (newRecipe, lp);

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
            JSONObject data = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Method.POST, AppConfig.URL_GET_ALL_RECIPES, data, new Response.Listener<JSONObject> () {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d ("tag_danut", "Response: " + response.toString ());

                    try {
                        JSONObject jsonObject = new JSONObject (response.toString ());

                    } catch (JSONException e) {
                        e.printStackTrace ();
                    }
                }
            }, new Response.ErrorListener () {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("errorTAG_danut", "Error returned" + error.getMessage ());
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if (id == R.id.add_recipe) {

        } else if (id == R.id.add_product) {

        } else if (id == R.id.add_store) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
