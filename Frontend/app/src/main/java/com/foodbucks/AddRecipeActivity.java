package com.foodbucks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.androidapp.R;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_recipe);
        Toolbar toolbar = findViewById (R.id.recipeDetailsToolbar);
        setSupportActionBar (toolbar);

    }

}
