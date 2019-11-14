package com.example.catappadam.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catappadam.Activities.CatDetailActivity;
import com.example.catappadam.Models.Cat;
import com.example.catappadam.R;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> implements Filterable {

    public ArrayList<Cat> cats;
    private List<Cat> catListFull;


    public void setData(ArrayList<Cat> catsToAdapt) {

        this.cats = catsToAdapt;

        //creating a copy of the CatsToAdapt list
        catListFull = new ArrayList<>(catsToAdapt);

    }


    //Creating viewholder
    public static class CatViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView catName;

        public CatViewHolder(View v) {
            super(v);
            view = v;
            catName = v.findViewById(R.id.cat_name);

        }
    }
    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat, parent, false);

        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder holder, final int position) {
        final Cat currentCat = cats.get(position);
        holder.catName.setText(currentCat.getName());

        //Transition to details of the selected breed

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent explicitIntent = new Intent(context, CatDetailActivity.class);
                explicitIntent.putExtra("id", currentCat.getId());
                context.startActivity(explicitIntent);
            }
        });
    }

    @Override
    public int getItemCount() { return cats.size();}

    @Override
    public Filter getFilter() {
        return catFilter;
    }

    private Filter catFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            //return filtered results
            List<Cat> filteredCatList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                //show all the results
                filteredCatList.addAll(catListFull);
            } else {

                //Show filtered result

                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Cat catFilters : catListFull) {

                    //Removing case sensitivity and seeing if there is a match

                    if (catFilters.getName().toLowerCase().contains(filterPattern)) {
                        filteredCatList.add(catFilters);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCatList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cats.clear();
            cats.addAll((ArrayList) results.values);

            //Refresh Adapter
            notifyDataSetChanged();;


        }
    };
}
