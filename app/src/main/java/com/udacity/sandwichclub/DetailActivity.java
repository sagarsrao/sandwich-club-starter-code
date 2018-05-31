package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import android.support.v7.widget.Toolbar;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mtv_also_known_as;
    private TextView mIngredients;
    private TextView mPlace_of_origin;
    private TextView mdecsription;
    private Toolbar mToolbar;
    private TextView mSandwichName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mtv_also_known_as = findViewById(R.id.tv_also_known_as);
        mIngredients = findViewById(R.id.tv_ingredients);
        mPlace_of_origin = findViewById(R.id.tv_place_of_origin);
        mdecsription = findViewById(R.id.tv_description);
        mToolbar = findViewById(R.id.toolbar);
        mSandwichName = findViewById(R.id.tv_sandwich_name);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = getIntent().getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);


            mSandwichName.setText(sandwich.getMainName());



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        /*populate all the data over here*/
        mPlace_of_origin.setText(getTheMissingData(sandwich.getPlaceOfOrigin()));
        mdecsription.setText((sandwich.getDescription()));
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String response = "";
        StringBuilder builder = new StringBuilder();
        for (String details : alsoKnownAs) {
            builder.append(details).append("\n");
        }

        mtv_also_known_as.setText(builder.toString());

        response = "";

        List<String> ingredientsList = sandwich.getIngredients();
        for (String data : ingredientsList) {


            response = data.concat("\n");
        }

        mIngredients.setText(getTheMissingData(response));

    }


    private String getTheMissingData(String data) {

        if (data.equals("")) {

            return getString(R.string.missing_data);
        } else {

            return data;

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
