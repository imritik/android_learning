package com.example.mybroadcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.MyViewHolder> {
    private Context context;
    private List<Recipe> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,description,price,chef,timestamp;
        public ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            chef=itemView.findViewById(R.id.chef);
            description=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.price);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            timestamp=itemView.findViewById(R.id.timestamp);
        }
    }

    public RecipeListAdapter(Context context,List<Recipe> cartList){
        this.cartList=cartList;
        this.context=context;
    }
    @NonNull
    @Override
    public RecipeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.MyViewHolder holder, int position) {

        final Recipe recipe=cartList.get(position);
        holder.name.setText(recipe.getName());
        holder.chef.setText("By"+recipe.getChef());
        holder.description.setText(recipe.getDescription());
        holder.price.setText("Price: Rs" + recipe.getPrice());
        holder.timestamp.setText(recipe.getTimestamp());

        Glide.with(context)
                .load(recipe.getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
