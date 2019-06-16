package com.foodbucks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {
    private EditText newRecipeNameEditText;
    private EditText newRecipeDescriptionEditText;
    private EditText newRecipeServingsEditText;
    private EditText newRecipeCaloriesEditText;
    private EditText newRecipeCookingTimeEditText;

    private Button addIngredientButton;
    private Button removeLastIngredientButton;
    private LinearLayout newRecipeIngredientsLinearLayout;

    private Button addInstructionStepButton;
    private Button removeLastInstructionButton;
    private LinearLayout newRecipeInstructionStepsLinearLayout;

    private Button addRecipeButton;

    private int newIngredientNumber;
    private int newInstructionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_recipe);
        Toolbar toolbar = findViewById (R.id.recipeDetailsToolbar);
        toolbar.setTitle ("Add a new recipe");

        newIngredientNumber = 0;
        newInstructionNumber = 0;

        addRecipeButton = findViewById (R.id.addRecipeButton);

        newRecipeNameEditText = findViewById (R.id.newRecipeNameEditText);
        newRecipeDescriptionEditText = findViewById (R.id.newRecipeDescriptionEditText);
        newRecipeServingsEditText = findViewById (R.id.newRecipeServingsEditText);
        newRecipeCaloriesEditText = findViewById (R.id.newRecipeCaloriesEditText);
        newRecipeCookingTimeEditText = findViewById (R.id.newRecipeCookingTimeEditText);

        addIngredientButton = findViewById (R.id.addIngredientButton);
        removeLastIngredientButton = findViewById (R.id.removeLastIngredientButton);
        newRecipeIngredientsLinearLayout = findViewById (R.id.newRecipeIngredientsLinearLayout);

        addInstructionStepButton = findViewById (R.id.addInstructionStepButton);
        removeLastInstructionButton = findViewById (R.id.removeLastInstructionButton);
        newRecipeInstructionStepsLinearLayout = findViewById (R.id.newRecipeInstructionStepsLinearLayout);

        addIngredientButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                newIngredientNumber += 1;
                LinearLayout ingredientLayout = new LinearLayout (AddRecipeActivity.this);
                ingredientLayout.setLayoutParams (lp);
                ingredientLayout.setOrientation (LinearLayout.HORIZONTAL);

                EditText newIngredientEditText = new EditText (AddRecipeActivity.this);
                newIngredientEditText.setHint ("Enter new ingredient");
                newIngredientEditText.setTextSize (15);

                EditText newIngredientQuantityEditText = new EditText (AddRecipeActivity.this);
                newIngredientQuantityEditText.setHint ("Enter quantity");
                newIngredientQuantityEditText.setInputType (InputType.TYPE_NUMBER_FLAG_DECIMAL);
                newIngredientQuantityEditText.setTextSize (15);

                Spinner unitOfMeasurementSpinner = new Spinner (AddRecipeActivity.this);
                ArrayList<String> spinnerArray = new ArrayList<String> ();
                spinnerArray.add("millilitre");
                spinnerArray.add ("fluid ounce");
                spinnerArray.add ("pint");
                spinnerArray.add ("gallon");
                spinnerArray.add ("cup");
                spinnerArray.add ("quart");
                spinnerArray.add ("litre");
                spinnerArray.add ("gram");
                spinnerArray.add ("ounce");
                spinnerArray.add ("kilogram");
                spinnerArray.add ("pound");
                spinnerArray.add ("package");
                spinnerArray.add ("slice");
                spinnerArray.add ("chunk");
                spinnerArray.add ("bunch");
                spinnerArray.add ("bottle");
                spinnerArray.add ("piece");
                spinnerArray.add ("container");
                spinnerArray.add ("box");
                spinnerArray.add ("stalk");
                spinnerArray.add ("can");
                spinnerArray.add ("ball");
                spinnerArray.add ("pack");
                spinnerArray.add ("to taste");
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddRecipeActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                unitOfMeasurementSpinner.setAdapter (spinnerArrayAdapter);

                ingredientLayout.addView (newIngredientEditText);
                ingredientLayout.addView (newIngredientQuantityEditText);
                ingredientLayout.addView (unitOfMeasurementSpinner);
                newRecipeIngredientsLinearLayout.addView (ingredientLayout, lp);
            }
        });

        removeLastIngredientButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(newRecipeIngredientsLinearLayout.getChildCount () != 0){
                    newRecipeIngredientsLinearLayout.removeViewAt (newRecipeIngredientsLinearLayout.getChildCount ()-1);
                    newIngredientNumber -= 1;
                }
            }
        });

        addInstructionStepButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                LinearLayout instructionsLayout = new LinearLayout (AddRecipeActivity.this);
                instructionsLayout.setLayoutParams (lp);
                instructionsLayout.setOrientation (LinearLayout.HORIZONTAL);

                newInstructionNumber +=1;

                TextView instructionNumber = new TextView (AddRecipeActivity.this);
                instructionNumber.setText (newInstructionNumber + ". ");

                EditText newInstructionEditText = new EditText (AddRecipeActivity.this);
                newInstructionEditText.setHint ("Enter instruction " + newInstructionNumber);
                newInstructionEditText.setWidth (1000);

                instructionsLayout.addView (instructionNumber);
                instructionsLayout.addView (newInstructionEditText);

                newRecipeInstructionStepsLinearLayout.addView (instructionsLayout, lp);
            }
        });

        removeLastInstructionButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(newRecipeInstructionStepsLinearLayout.getChildCount () != 0){
                    newRecipeInstructionStepsLinearLayout.removeViewAt (newRecipeInstructionStepsLinearLayout.getChildCount ()-1);
                    newInstructionNumber -= 1;
                }
            }
        });

        addRecipeButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                boolean isValidInput = true;
                if( newRecipeNameEditText.getText ().toString ().trim ().isEmpty ()){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have a name set!");
                }
                else if(newRecipeInstructionStepsLinearLayout.getChildCount () == 0){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have at least one instruction!");
                }
                else if(newRecipeInstructionStepsLinearLayout.getChildCount () != 0){
                    for (int i = 0; i < newRecipeInstructionStepsLinearLayout.getChildCount (); i ++){
                        LinearLayout instructionLinearLayout = (LinearLayout) newRecipeInstructionStepsLinearLayout.getChildAt (i);
                        EditText instructionText = (EditText) instructionLinearLayout.getChildAt (1);
                        Log.d("TAG", instructionText.getText ().toString ());
                        if(instructionText.getText ().toString ().trim ().isEmpty ()){
                            ShowAlertDialogError("Set the text of instruction " + (i + 1) + "! ");
                            isValidInput = false;
                            break;
                        }
                    }
                }
                else if(newRecipeIngredientsLinearLayout.getChildCount () == 0){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have at least one ingredient!");
                }
                else if(newRecipeIngredientsLinearLayout.getChildCount () !=0){
                    for (int i = 0; i < newRecipeIngredientsLinearLayout.getChildCount (); i++){
                        LinearLayout ingredientLinearLayout = (LinearLayout) newRecipeIngredientsLinearLayout.getChildAt (i);
                        EditText ingredientName = (EditText) ingredientLinearLayout.getChildAt (0);
                        EditText ingredientQuantity = (EditText) ingredientLinearLayout.getChildAt (1);
                        if(ingredientName.getText ().toString ().trim ().isEmpty ()){
                            isValidInput = false;
                            ShowAlertDialogError("Set the name of ingredient " + (i +1) + "! ");
                            break;
                        }
                        else if (ingredientQuantity.getText ().toString ().trim ().isEmpty ()){
                            isValidInput = false;
                            ShowAlertDialogError("Set the quantity of ingredient " + (i +1) + "! ");
                            break;
                        }
                    }
                }
                else if(newRecipeDescriptionEditText.getText ().toString ().trim ().isEmpty ()){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have a description set!");
                }
                else if(newRecipeServingsEditText.getText ().toString ().trim ().isEmpty ()){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have servings set!");
                }
                else if(newRecipeCaloriesEditText.getText ().toString ().trim ().isEmpty ()){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have calories set!");
                }
                else if(newRecipeCookingTimeEditText.getText ().toString ().trim ().isEmpty ()){
                    isValidInput = false;
                    ShowAlertDialogError("Your recipe must have cooking time set!");
                }

                if (isValidInput == true){
                    try {
                        AddNewRecipe ();
                    } catch (JSONException e) {
                        e.printStackTrace ();
                    }
                }
            }
        });

    }

    void ShowAlertDialogError(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddRecipeActivity.this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setNeutralButton (
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    void AddNewRecipe() throws JSONException {

        JSONObject postRecipe = new JSONObject ();

        /* Build ingredients JSON*/
        JSONArray postIngredients = new JSONArray ();
        for(int i = 0; i < newRecipeIngredientsLinearLayout.getChildCount (); i++){
            LinearLayout ingredientLinearLayout = (LinearLayout) newRecipeIngredientsLinearLayout.getChildAt (i);
            EditText ingredientName = (EditText) ingredientLinearLayout.getChildAt (0);
            EditText ingredientQuantity = (EditText) ingredientLinearLayout.getChildAt (1);
            Spinner ingredientUOM = (Spinner) ingredientLinearLayout.getChildAt (2);

            JSONObject ingredientJSON = new JSONObject ();
            ingredientJSON.put ("name", ingredientName.getText ());
            ingredientJSON.put ("quantity", Double.parseDouble (ingredientQuantity.getText ().toString ()));
            ingredientJSON.put ("unitOfMeasurement", ingredientUOM.getSelectedItem ());
            ingredientJSON.put ("cost", 0);
            ingredientJSON.put ("nrofproductsnecessary", 0);

            postIngredients.put (ingredientJSON);
        }


        /* Build instructions JSON */
        JSONArray postInstructions = new JSONArray ();
        for(int i = 0; i < newRecipeInstructionStepsLinearLayout.getChildCount (); i++){
            LinearLayout instructionLinearLayout = (LinearLayout) newRecipeInstructionStepsLinearLayout.getChildAt (i);
            EditText instructionText = (EditText) instructionLinearLayout.getChildAt (1);
            JSONObject instructionJSON = new JSONObject ();

            instructionJSON.put ("description", instructionText.getText ().toString ());
            instructionJSON.put ("instructionNr", i + 1);

            postInstructions.put(instructionJSON);
        }

        postRecipe.put ("name", newRecipeNameEditText.getText ());
        postRecipe.put ("description", newRecipeDescriptionEditText.getText ());
        postRecipe.put ("ingredients", postIngredients);
        postRecipe.put ("instructionSteps", postInstructions);
        postRecipe.put ("servings", Integer.parseInt (newRecipeServingsEditText.getText ().toString ()));
        postRecipe.put ("calories", Integer.parseInt (newRecipeCaloriesEditText.getText ().toString ()));
        postRecipe.put ("cookingTime", Integer.parseInt (newRecipeCookingTimeEditText.getText ().toString ()));
        postRecipe.put ("likes", 0);
        postRecipe.put ("votes", 0);
        postRecipe.put ("cost", 0);
        postRecipe.put ("rating", 0);

        Log.d("TAG", postRecipe.toString ());


        String url = AppConfig.URL_POST_RECIPE;
        RequestQueue requestQueue = Volley.newRequestQueue(AddRecipeActivity.this);
        JSONObject postparams = new JSONObject ();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.POST,
                url, postRecipe,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString ("name") + "Was added", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent (AddRecipeActivity.this, RecipeDetailsActivity.class);
                            intent.putExtra ("recipeId", response.getString ("id").toString ());
                            startActivity (intent);
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

}
