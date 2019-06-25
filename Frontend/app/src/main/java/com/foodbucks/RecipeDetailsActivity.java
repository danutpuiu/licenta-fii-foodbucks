package com.foodbucks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.R;
import com.foodbucks.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView recipeNameTextView;
    private TextView recipeCaloriesTextView;
    private TextView recipeCookingTimeTextView;
    private TextView recipeServingsTextView;
    private TextView recipeCostTextView;
    private TextView recipeRatingTextView;
    private TextView recipeDescriptionTextView;
    private TextView instructionStepsDescriptorTextView;
    private TextView ingredientsDescriptorTextView;
    private LinearLayout recipeIngredientsLinearLayout;
    private LinearLayout recipeInstructionStepsLinearLayout;
    private Button deleteRecipeButton;

    private Button likeRecipeButton;
    private Button dislikeRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recipe_details);

        Toolbar toolbar = findViewById (R.id.recipeDetailsToolbar);
        setSupportActionBar (toolbar);
        deleteRecipeButton = findViewById (R.id.deleteRecipeButton);


        likeRecipeButton = findViewById (R.id.likeRecipeButton);
        dislikeRecipeButton = findViewById (R.id.dislikeRecipeButton);

        likeRecipeButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                RateRecipe (true, getIntent ().getStringExtra ("recipeId"));
                likeRecipeButton.setClickable (false);
                dislikeRecipeButton.setClickable (true);
            }
        });

        dislikeRecipeButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                RateRecipe (false, getIntent ().getStringExtra ("recipeId"));
                likeRecipeButton.setClickable (true);
                dislikeRecipeButton.setClickable (false);
            }
        });

        deleteRecipeButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String url = AppConfig.URL_DELETE_RECIPE + getIntent ().getStringExtra ("recipeId");
                RequestQueue requestQueue = Volley.newRequestQueue (RecipeDetailsActivity.this);
                Log.d("TAG", url);
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                        url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Success Callback
                                Intent intent = new Intent (RecipeDetailsActivity.this, MainActivity.class);
                                startActivity (intent);
                                Toast.makeText(getApplicationContext(), "Recipe deleted!", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Intent intent = new Intent (RecipeDetailsActivity.this, MainActivity.class);
                                startActivity (intent);
                                Toast.makeText(getApplicationContext(), "Recipe deleted!", Toast.LENGTH_LONG).show();
                            }
                        });

                requestQueue.add(jsonObjReq);
            }
        });

        recipeCaloriesTextView = findViewById (R.id.recipeCaloriesTextView);
        recipeCookingTimeTextView = findViewById (R.id.recipeCookingTimeTextView);
        recipeCostTextView = findViewById (R.id.recipeCostTextView);
        recipeDescriptionTextView = findViewById (R.id.productQuantityAndUOMTextView);
        recipeInstructionStepsLinearLayout = findViewById (R.id.recipeInstructionStepsLiniarLayout);
        recipeIngredientsLinearLayout = findViewById (R.id.pricesAndStoresLiniarLayout);
        recipeNameTextView = findViewById (R.id.productNameAndBrandTextView);
        recipeServingsTextView  = findViewById (R.id.recipeServingsTextView);
        recipeRatingTextView = findViewById (R.id.recipeRatingTextView);
        instructionStepsDescriptorTextView = findViewById (R.id.instructionStepsDescriptorTextView);
        ingredientsDescriptorTextView = findViewById (R.id.ingredientsDescriptorTextView);


        String url = AppConfig.URL_GET_RECIPE_DETAILS + getIntent ().getStringExtra ("recipeId");

        RequestQueue requestQueue = Volley.newRequestQueue (this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                        try {
                            Log.d("TAG", response.toString ());
                            recipeNameTextView.setText (response.getString ("name"));
                            recipeDescriptionTextView.setText (response.getString ("description"));
                            recipeServingsTextView.setText (response.getInt ("servings") + " servings");
                            recipeCaloriesTextView.setText (response.getString ("calories") + " calories");
                            recipeCookingTimeTextView.setText (response.getString ("cookingTime") + " minutes");
                            recipeRatingTextView.setText ((int)(response.getDouble ("rating"))  + " stars out of 10 rating");
                            recipeCostTextView.setText ("Cost: " + response.getString ("cost")  + " lei");

                            ingredientsDescriptorTextView.setText ("Ingredients");
                            JSONArray ingredientsJSON = response.getJSONArray ("ingredients");
                            for (int i = 0; i < ingredientsJSON.length (); i++){
                                JSONObject ingredient = ingredientsJSON.getJSONObject (i);
                                TextView newIngredientTextView = new TextView (RecipeDetailsActivity.this);
                                newIngredientTextView.setTextColor (Color.rgb (254,254,254));
                                newIngredientTextView.setText (ingredient.getString ("name") + " - " +
                                        ingredient.getString ("quantity") + " " +
                                        ingredient.getString ("unitOfMeasurement") +
                                        " (~" +
                                        ingredient.getString ("cost") +
                                        " lei)");
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                recipeIngredientsLinearLayout.addView (newIngredientTextView, lp);
                            }

                            instructionStepsDescriptorTextView.setText ("Instructions");
                            JSONArray instructionStepsJSON = response.getJSONArray ("instructionSteps");
                            for (int i = 0; i< instructionStepsJSON.length (); i++){
                                JSONObject instructionStep = instructionStepsJSON.getJSONObject (i);
                                final TextView newInstructionStepTextView = new TextView ( RecipeDetailsActivity.this);
                                //newInstructionStepTextView.setTextColor (Color.rgb (254,254,254));
                                newInstructionStepTextView.setClickable (true);
                                newInstructionStepTextView.setText (instructionStep.getString ("instructionNr") +
                                        ". " +
                                        instructionStep.getString ("description"));
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                recipeInstructionStepsLinearLayout.addView (newInstructionStepTextView, lp);
                                newInstructionStepTextView.setTag ("NOT_DONE");
                                newInstructionStepTextView.setOnClickListener (new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        if(newInstructionStepTextView.getTag () != "DONE"){
                                            newInstructionStepTextView.setBackgroundColor (Color.rgb (68, 186, 81));
                                            newInstructionStepTextView.setTag ("DONE");
                                        }
                                        else{
                                            newInstructionStepTextView.setBackgroundColor (0x00000000);
                                            newInstructionStepTextView.setTag ("NOT_DONE");
                                        }
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

    public void RateRecipe (boolean vote, String recipeId){
        String url = AppConfig.URL_VOTE_RECIPE + recipeId;
        RequestQueue requestQueue = Volley.newRequestQueue(RecipeDetailsActivity.this);
        JSONObject postparams = new JSONObject ();
        try {
            postparams.put ("vote", vote);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage (), Toast.LENGTH_LONG).show();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.PUT,
                url, postparams,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            recipeRatingTextView.setText ((int)(response.getDouble ("rating"))  + " stars out of 10 rating");
                            Toast.makeText(getApplicationContext(), "vode added", Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (RecipeDetailsActivity.this, MainActivity.class);
        startActivity (intent);
    }

}
