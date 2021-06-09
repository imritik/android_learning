package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ListViewAdapter;
import com.example.myapplication.viewmodel.FishViewModel;
import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FishViewModel fishViewModel;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBinding(savedInstanceState);
    }
    private void setUpBinding(Bundle savedInstanceState){
        ActivityMainBinding activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //bind recycleView
        RecyclerView recyclerView=activityMainBinding.requestResponse;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //init the view model
        fishViewModel= ViewModelProviders.of(this).get(FishViewModel.class);
        if(savedInstanceState==null){
            fishViewModel.init();
        }

        //init the adapter
//        listViewAdapter=new ListViewAdapter();
//        recyclerView.setAdapter(listViewAdapter);

        activityMainBinding.setFishModel(fishViewModel);
        Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
        fishViewModel.sendRequest(getApplicationContext());
    }

}