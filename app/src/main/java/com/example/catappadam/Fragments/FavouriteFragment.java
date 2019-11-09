package com.example.catappadam.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catappadam.Adapters.CatAdapter;
import com.example.catappadam.Adapters.FavouriteAdapter;
import com.example.catappadam.Models.Cat;
import com.example.catappadam.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;

    public static ArrayList<Cat> favouriteCats = new ArrayList<>();

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = view.findViewById(R.id.fav_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        final FavouriteAdapter favouriteAdapter = new FavouriteAdapter();
        recyclerView.setAdapter(favouriteAdapter);

        return view;
    }

}
