package com.example.catappadam.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catappadam.Models.Cat;
import com.example.catappadam.R;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>{

    public static ArrayList<Cat> favCats = new ArrayList<>();


    public void setData(ArrayList<Cat> favCatsToAdapt) {

        this.favCats = favCatsToAdapt;

    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {

        public TextView favCatName;

        public FavouriteViewHolder(View v) {
            super(v);
            favCatName = v.findViewById(R.id.fav_name);

        }

    }
        @NonNull
        @Override
        public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_favourite, parent, false);

            FavouriteAdapter.FavouriteViewHolder favouriteViewHolder= new FavouriteAdapter.FavouriteViewHolder(view);
            return favouriteViewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull final FavouriteViewHolder holder, final int position) {

        final Cat currentCat = favCats.get(position);

        holder.favCatName.setText(currentCat.getName());

        }


    @Override
    public int getItemCount() {
        return favCats.size();
    }

}
