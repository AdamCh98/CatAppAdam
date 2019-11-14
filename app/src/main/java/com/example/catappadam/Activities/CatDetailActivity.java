package com.example.catappadam.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.catappadam.Adapters.FavouriteAdapter;
import com.example.catappadam.FakeDatabase;
import com.example.catappadam.Models.Cat;
import com.example.catappadam.Models.Image;
import com.example.catappadam.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class CatDetailActivity extends AppCompatActivity {

    private ImageView catPhoto;
    private TextView catName;
    private TextView catDescription;
    private TextView catWeight;
    private TextView catTemperament;
    private TextView catOrigin;
    private TextView catLifespan;
    private TextView catWikilink;
    private TextView catDogFriend;
    private ArrayList<Image> imageArrayList;
    private String imageUrl;
    private Button favButton;
    private String catNameToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);
        //Setting all the links
        catName = findViewById(R.id.cat_name);
        catDescription = findViewById(R.id.cat_description);
        catOrigin = findViewById(R.id.cat_origin);
        catWeight = findViewById(R.id.cat_weight);
        catTemperament = findViewById(R.id.cat_temperament);
        catLifespan = findViewById(R.id.cat_lifespan);
        catWikilink = findViewById(R.id.cat_wikilink);
        catDogFriend = findViewById(R.id.cat_dogfriendliness);
        catPhoto = findViewById(R.id.cat_photo);
        favButton = findViewById(R.id.cat_button);

        //Grab the content that we were given
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        System.out.println(id);
        final Cat cat = FakeDatabase.getCatById(id);
        catNameToast = cat.getName();

        //Assigning attributes contingent on whether they exist
        if (cat.getName() != null) {
            catName.setText(cat.getName());
        } else {
            catName.setText("[Doesn't Exist]");
        }
        if (cat.getDescription() != null) {
            catDescription.setText(cat.getDescription());
        } else {
            catDescription.setText("[Doesn't exist]");
        }
        if (cat.getOrigin() != null) {
            catOrigin.setText(cat.getOrigin());
        } else {
            catOrigin.setText("[Doesn't exist]");
        }
        if(cat.getWeight()!= null) {
            //Using metric system
            catWeight.setText(cat.getWeight().getMetric() + " KG");
        } else {
            catWeight.setText("[Doesn't exist]");
        }
        if(cat.getTemperament()!= null) {
            catTemperament.setText(cat.getDescription());
        } else {
            catTemperament.setText("[Doesn't exist]");
        }
        if(cat.getLife_span()!= null) {
            catLifespan.setText(cat.getLife_span() + " years");
        } else {
            catLifespan.setText("[Doesn't exist]");
        }
        if(cat.getWikipedia_url()!= null) {
            catWikilink.setText(cat.getWikipedia_url());
        } else {
            catWikilink.setText("[Doesn't exist]");
        }
        if(cat.getDog_friendly() != 0) {
            catDogFriend.setText(" " + cat.getDog_friendly());
        } else {
            catDogFriend.setText("[Doesn't exist]");
        }

        //Image
        String potentialUrl = "https://api.thecatapi.com/v1/images/search?breed_ids=" + cat.getId();

        //Create the context:
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //If we get a response
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Gson gson = new Gson();
                Image[] imageArray = gson.fromJson(response, Image[].class);
                 imageArrayList = new ArrayList<Image>(Arrays.asList(imageArray));
                 Image thisImage = imageArrayList.get(0);
                System.out.println(thisImage.getUrl());
                imageUrl = thisImage.getUrl();
                Glide.with(CatDetailActivity.this).load(imageUrl).into(catPhoto);

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                System.out.println(error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, potentialUrl, responseListener, errorListener);
        requestQueue.add(stringRequest);


        //Adding item to favourite
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add to fav cats list
                FavouriteAdapter.favCats.add(cat);
                confirmToast(v);

            }
        });



    }

    public void confirmToast (View v) {
        Toast.makeText(CatDetailActivity.this, "You have added " + catNameToast + " to favourites!",
                Toast.LENGTH_SHORT).show();
    }
}
