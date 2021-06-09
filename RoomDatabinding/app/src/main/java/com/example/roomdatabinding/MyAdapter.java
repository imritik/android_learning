package com.example.roomdatabinding;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabinding.databinding.MyfeedBinding;
import com.example.roomdatabinding.roomDatabase.MyUser;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyUser>myUsers;
    private LayoutInflater layoutInflater;
    private DataListener dataListener;
    private List<MyUser> myUsersFullList;

    public MyAdapter(List<MyUser> myUsers,DataListener listener){
        this.myUsers=myUsers;
        this.dataListener=listener;
        myUsersFullList=new ArrayList<>(myUsers);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        MyfeedBinding binding= DataBindingUtil.inflate(layoutInflater,R.layout.myfeed,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.myfeedBinding.setMyus(myUsers.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("item clicked",myUsers.get(position).username);
                dataListener.onItemClicked(myUsers.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myUsers.size();
    }


    public Filter getFilter(){
       return exampleFilter;
    }

    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MyUser> filteredUsers=new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filteredUsers.addAll(myUsersFullList);
            }
            else{
                String filterPattern=constraint.toString().toLowerCase().trim();
                for(MyUser item:myUsersFullList){
                    if(item.getUsername().toLowerCase().contains(filterPattern)){
                        filteredUsers.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredUsers;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myUsers.clear();
            myUsers.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public MyUser getUserAt(int position){
        return myUsers.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final MyfeedBinding myfeedBinding;
        public ViewHolder(MyfeedBinding myfeedBinding) {
            super(myfeedBinding.getRoot());
            this.myfeedBinding=myfeedBinding;
        }
    }
}
