package com.example.catappadam.Fragments;


import android.content.Context;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.catappadam.Adapters.CatAdapter;
import com.example.catappadam.FakeDatabase;
import com.example.catappadam.Models.Cat;
import com.example.catappadam.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView catSearchView;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        final CatAdapter catAdapter = new CatAdapter();

        //Creating search
        catSearchView = view.findViewById(R.id.search_bar);
        catSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        catSearchView.setQueryHint("Click on icon to search for cat");

        //Prevent the keyboard from pushing up the entire layout
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        catSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                catAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //URL string

        String catUrl = "https://api.thecatapi.com/v1/breeds?api_key=2a827b18-cdf0-4410-9aeb-8a7c031db2af\n" +
                "\n";

        //Create the context:
        Context context = getContext();
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Gson gson = new Gson();

                Cat[] catsArray = gson.fromJson(response, Cat[].class);
                ArrayList<Cat> catArrayList = new ArrayList<Cat>(Arrays.asList(catsArray));

                catAdapter.setData(catArrayList);
                FakeDatabase.saveCatsToFakeDatabase(catArrayList);
                recyclerView.setAdapter(catAdapter);


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                System.out.println(error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, catUrl, responseListener, errorListener);
        requestQueue.add(stringRequest);


        return view;
    }

}
