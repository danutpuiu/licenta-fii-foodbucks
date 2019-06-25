package com.foodbucks;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import com.foodbucks.app.AppConfig;
import com.foodbucks.app.FilterDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView2;
    private LinearLayout recipesLinearLayout;
    private JSONObject filterJSON;
    private Button filterRecipesButton;

    private EditText filterNameEditText;
    private EditText filterCostEditText;
    private Spinner filterRatingSpinner;
    private EditText filterNumberOfVotesEditText;
    private EditText filterIncludedProductsEditText;
    private EditText filterIncludedBrandsEditText;
    private EditText filterOnlyStoresEditText;
    private EditText filterCookingTimeEditText;

    private EditText searchRecipesEditText;
    private Button searchRecipesButton;

    private Button sortRecipesButton;

    private FilterDTO appliedFilter;

    private AlertDialog filterDialog;
    private AlertDialog sortDialog;

    private Spinner sortCriteriaSpinner;
    private Spinner sortTypeSpinner;

    private View filterLayout;
    private View sortLayout;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortRecipesButton = findViewById (R.id.sortRecipesButton);

        appliedFilter = new FilterDTO (
                "", 0, 0, 0, 0, "", "", ""
        );

        recipesLinearLayout = findViewById (R.id.recipesLinearLayout);
        filterRecipesButton = findViewById (R.id.filterRecipesButton);
        searchRecipesEditText = findViewById (R.id.searchRecipesEditText);
        searchRecipesButton = findViewById (R.id.searchRecipesButton);

        searchRecipesButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                GetAndListRecipesBySearch();
            }
        });

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


        LayoutInflater inflater = (LayoutInflater) getSystemService (LAYOUT_INFLATER_SERVICE);
        filterLayout = inflater.inflate (R.layout.content_filter, (ViewGroup) findViewById (R.id.filterLinearLayout));
        AlertDialog.Builder builder = new AlertDialog.Builder (this);

        builder.setView (filterLayout);
        builder.setPositiveButton ("Filter", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filterNameEditText = filterLayout.findViewById (R.id.filterNameEditText);
                filterCostEditText = filterLayout.findViewById (R.id.filterCostEditText);
                filterNumberOfVotesEditText = filterLayout.findViewById (R.id.filterNumberOfVotesEditText);
                filterIncludedProductsEditText = filterLayout.findViewById (R.id.filterIncludedProductsEditText);
                filterRatingSpinner = (Spinner) filterLayout.findViewById (R.id.filterRatingSpinner);
                filterIncludedBrandsEditText = filterLayout.findViewById (R.id.filterIncludedBrandsEditText);
                filterOnlyStoresEditText = filterLayout.findViewById (R.id.filterOnlyStoresEditText);
                filterCookingTimeEditText = filterLayout.findViewById (R.id.filterCookingTimeEditText);

                if(!searchRecipesEditText.getText ().toString ().trim ().isEmpty ()){
                    filterNameEditText.setText (searchRecipesEditText.getText ());
                }
                GetAndListRecipesByFilter();
            }
        });
        builder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        filterDialog = builder.create ();
        filterRecipesButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                filterDialog.show ();
            }
        });

        sortLayout = inflater.inflate (R.layout.content_sort, (ViewGroup) findViewById (R.id.sortLinearLayour));
        AlertDialog.Builder builder1 = new AlertDialog.Builder (this);
        builder1.setView (sortLayout);
        builder1.setPositiveButton ("Sort", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortCriteriaSpinner = sortLayout.findViewById (R.id.sortCriteriaSpinner);
                sortTypeSpinner = sortLayout.findViewById (R.id.sortTypeSpinner);

                GetAndListRecipesBySort(sortCriteriaSpinner.getSelectedItem ().toString () + "_" + sortTypeSpinner.getSelectedItem ().toString ());
            }
        });

        builder1.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        sortDialog = builder1.create ();

        sortRecipesButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                sortDialog.show ();
            }
        });


    }

    public void GetAndListRecipesBySort(String sortCriteria){
        String url = AppConfig.URL_SORT_RECIPES + sortCriteria;

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
                        recipesLinearLayout.removeAllViews ();
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
                                newRecipe.setAllCaps (false);
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
                            Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
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

    public void GetAndListRecipesBySearch(){
        String url = AppConfig.URL_GET_RECIPES_BY_NAME + searchRecipesEditText.getText ();

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
                        // Do something with response
                        recipesLinearLayout.removeAllViews ();
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
                                newRecipe.setAllCaps (false);
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
                            Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
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

    public void GetAndListRecipesByFilter(){
        String url = AppConfig.URL_POST_RECIPES_FILTER;

        RequestQueue requestQueue = Volley.newRequestQueue (MainActivity.this);
        if(!filterNameEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setName (filterNameEditText.getText ().toString ());
        }
        else{
            appliedFilter.setName ("");
        }

        if(!filterCostEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setCost (Integer.parseInt(filterCostEditText.getText ().toString ()));
        }else{
            appliedFilter.setCost (0);
        }

        appliedFilter.setRating (Integer.parseInt(filterRatingSpinner.getSelectedItem ().toString ()));

        if(!filterCookingTimeEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setCookingTime (Integer.parseInt(filterCookingTimeEditText.getText ().toString ()));
        }else{
            appliedFilter.setCookingTime (0);
        }
        if(!filterIncludedProductsEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setIncludedProducts (filterIncludedProductsEditText.getText ().toString ());
        }else{
            appliedFilter.setIncludedProducts ("");
        }

        if(!filterIncludedBrandsEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setIncludingBrands (filterIncludedBrandsEditText.getText ().toString ());
        }else{
            appliedFilter.setIncludingBrands ("");
        }

        if(!filterOnlyStoresEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setOnlyStores (filterOnlyStoresEditText.getText ().toString ());
        }else{
            appliedFilter.setOnlyStores ("");
        }

        if(!filterNumberOfVotesEditText.getText ().toString ().trim ().isEmpty ()){
            appliedFilter.setVotestNumber (Integer.parseInt (String.valueOf (filterNumberOfVotesEditText.getText ())));
        }else{
            appliedFilter.setVotestNumber (0);
        }

        JSONObject postparams = new JSONObject ();
        try {
            postparams.put ("name", appliedFilter.getName ());
            postparams.put ("cost", appliedFilter.getCost ());
            postparams.put ("rating", appliedFilter.getRating ());
            postparams.put ("cookingTime", appliedFilter.getCookingTime ());
            postparams.put ("includedProducts", appliedFilter.getIncludedProducts());
            postparams.put ("includingBrands", appliedFilter.getIncludingBrands ());
            postparams.put ("onlyStores",appliedFilter.getOnlyStores ());
            postparams.put ("votesNumber",appliedFilter.getVotestNumber ());
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
        }

        String value = postparams.toString ();
        Log.d("TAG", value.toString ());
        CustomJsonArrayRequest  jsonObjReq = new CustomJsonArrayRequest (Request.Method.POST,
                url, postparams,
                new Response.Listener<JSONArray> () {
                    @Override
                    public void onResponse(JSONArray response) {
                        recipesLinearLayout.removeAllViews ();
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
                                newRecipe.setAllCaps (false);
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
                            Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
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

    /*
    CODE SOURCE:
    https://stackoverflow.com/questions/39235567/volley-jsonarrayrequest-with-jsonobject-as-parameter
    user: Enzokie, date: Aug 31 '16 at 7:06
     */
    public class CustomJsonArrayRequest extends JsonRequest<JSONArray> {
            public CustomJsonArrayRequest(int method, String url, JSONObject jsonRequest,
                                          Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
                super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                        errorListener);
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError (e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
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
                                newRecipe.setAllCaps (false);
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
                            Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
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
                                newRecipe.setAllCaps (false);
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
