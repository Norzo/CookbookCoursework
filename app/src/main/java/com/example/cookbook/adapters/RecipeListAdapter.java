package com.example.cookbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>
{
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView recipeItemView;
        ItemClickListener itemClickListener;

        public RecipeViewHolder(@NonNull View itemView, ItemClickListener itemClickListener)
        {
            super(itemView);
            recipeItemView = itemView.findViewById(R.id.textView);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        void bind(final Recipe recipe)
        {
            if (recipe != null)
            {
                recipeItemView.setText(recipe.getName());
            }
        }

        @Override
        public void onClick(View v)
        {
            itemClickListener.onItemClicked(getAdapterPosition());
        }
    }

    private Context context;
    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes;
    private ItemClickListener mItemClickListener;

    public RecipeListAdapter(Context context, ItemClickListener itemClickListener) { this.context = context; mInflater = LayoutInflater.from(context); this.mRecipes = new ArrayList<>(); this.mItemClickListener = itemClickListener; }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RecipeViewHolder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position)
    {
        holder.bind(mRecipes.get(position));
    }

    public List<Recipe> getRecipes() { return mRecipes; }

    @Override
    public int getItemCount()
    {
        return mRecipes.size();
    }

    public void setRecipes (List<Recipe> newRecipes)
    {
        if (mRecipes != null)
        {
            mRecipes.clear();
            mRecipes.addAll(newRecipes);
            notifyDataSetChanged();
        }
        else
            mRecipes = newRecipes;
    }

    public interface ItemClickListener
    {
        void onItemClicked(int position);
    }
}
