package com.example.mybroadcast.checkboxExample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybroadcast.R;

import java.util.ArrayList;
import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitHolder> {

    Context context;
    List<Fruits> list=new ArrayList<>();

    public FruitAdapter(Context context,List<Fruits> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public FruitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_fruits,parent,false);
        return new FruitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitHolder holder, int position) {
        final Fruits fruits=list.get(position);
        holder.tv_name.setText(fruits.getName());
        holder.tv_price.setText(fruits.getPrice());
        holder.checkBox.setChecked(fruits.isSelected());
        holder.checkBox.setTag(list.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String data="";
                Fruits fruits1=(Fruits) holder.checkBox.getTag();
                fruits1.setSelected(holder.checkBox.isChecked());
                list.get(position).setSelected(holder.checkBox.isChecked());

                for(int j=0;j<list.size();j++){
                    if(list.get(j).isSelected()){
                        data=data+"\n"+list.get(j).getName().toString()+" "+list.get(j).getPrice().toString();
                    }
                }
                Toast.makeText(context,"Selected Fruits : \n "+data,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FruitHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_price;
        CheckBox checkBox;

        public FruitHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_fruit_name);
            tv_price=itemView.findViewById(R.id.tv_fruit_price);
            checkBox=itemView.findViewById(R.id.checkBox_select);
        }
    }
}
