package com.example.mybroadcast.SwipeRefresh;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.TextView;

import com.example.mybroadcast.R;

public class SwipeToRefresh extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_to_refresh);

        final TextView textView=findViewById(R.id.text);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i++;
                textView.setText("After refreshed "+i);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}