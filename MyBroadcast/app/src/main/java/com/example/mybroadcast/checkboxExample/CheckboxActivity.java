package com.example.mybroadcast.checkboxExample;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.mybroadcast.R;

import java.util.ArrayList;
import java.util.List;

public class CheckboxActivity extends AppCompatActivity {
    FruitAdapter adapter;
    List<Fruits> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view_fruits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] fruits=getResources().getStringArray(R.array.item_fruits);
        String[] price=getResources().getStringArray(R.array.item_price);
        for(int i=0;i<fruits.length;i++){
            Fruits fruits1=new Fruits(fruits[i],price[i],false);
            list.add(fruits1);
        }

        adapter=new FruitAdapter(CheckboxActivity.this,list);
        recyclerView.setAdapter(adapter);
    }
}