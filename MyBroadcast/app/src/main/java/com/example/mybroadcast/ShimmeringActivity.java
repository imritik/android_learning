package com.example.mybroadcast;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ShimmeringActivity extends AppCompatActivity {

    private static final String TAG=ShimmeringActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Recipe> cartList;
    private RecipeListAdapter adapter;

    private ShimmerFrameLayout mShimmerViewContainer;

    private static final String URL="https://api.androidhive.info/json/shimmer/menu.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shimmering);

        mShimmerViewContainer=findViewById(R.id.shimmer_view_container);

        recyclerView=findViewById(R.id.recycler_view);
        cartList=new ArrayList<>();
        adapter=new RecipeListAdapter(this,cartList);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        fetchRecipes(getApplicationContext());

    }

    private void fetchRecipes(Context context){
        JsonArrayRequest request=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response == null) {
                    Toast.makeText(getApplicationContext(), "Couldn't fetch the menu !", Toast.LENGTH_LONG).show();
                    return;
                }
                List<Recipe> recipes = new Gson().fromJson(response.toString(), new TypeToken<List<Recipe>>() {

                }.getType());
                cartList.clear();
                cartList.addAll(recipes);
                adapter.notifyDataSetChanged();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
        MyApplication.getInstance(context).addToRequestQueue(request,"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();

    }
}