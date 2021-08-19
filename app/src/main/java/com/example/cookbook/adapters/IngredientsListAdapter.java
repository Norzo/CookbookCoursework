package com.example.cookbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder>
{
    class IngredientsViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView ingredientItemView;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.textView);
        }

        void bind(final Ingredient ingredient)
        {
            if (ingredient != null)
            {
                ingredientItemView.setText(ingredient.getIngredientName());
            }
        }

    }

    private Context context;
    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients;


    public IngredientsListAdapter(Context context)
    { this.context = context; mInflater = LayoutInflater.from(context); this.mIngredients = new ArrayList<>(); }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position)
    {
        holder.bind(mIngredients.get(position));
    }

    public void setIngredients (List<Ingredient> newIngredients)
    {
        if (mIngredients != null)
        {
            mIngredients.clear();
            mIngredients.addAll(newIngredients);
            notifyDataSetChanged();
        }
        else
            mIngredients = newIngredients;
    }

    @Override
    public int getItemCount()
    {
        return mIngredients.size();
    }

}