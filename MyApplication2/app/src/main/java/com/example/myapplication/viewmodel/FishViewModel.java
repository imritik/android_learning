package com.example.myapplication.viewmodel;
import android.content.Context;
import android.widget.Toast;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.adapters.ListViewAdapter;
import com.example.myapplication.AppController;
import com.example.myapplication.models.Fish;
import com.example.myapplication.models.Fishes;
import com.example.myapplication.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FishViewModel extends ViewModel {

    private Fish fish;
    private Fishes fishes;
    private ListViewAdapter adapter;

    ObservableBoolean showProgressBar=new ObservableBoolean(false);
    ObservableField<ListViewAdapter> setAdapter = new ObservableField<>();
    ObservableField<String> fishImg=new ObservableField<>("");
    ObservableField<String> fishName=new ObservableField<>("");
    ObservableField<String> fishCatName=new ObservableField<>("");
    ObservableField<String> fishSize=new ObservableField<>("");
    ObservableField<String> fishPrice=new ObservableField<>("");

    public static final String REQUEST_TAG = "JSON_ARRAY_REQUEST_TAG";
    private static final String JSON_URL="https://jsonblob.com/api/bf058206-6f53-11eb-8f1d-0769448f4d22";

    List<Fish> listdata=new ArrayList<>();

    public void init(){
        fishes=new Fishes(new JSONArray());
//        adapter=new ListViewAdapter(R.layout.list_items,this);
        adapter=new ListViewAdapter();
        setAdapter.set(adapter);
    }

    public ObservableBoolean getShowProgressBar() {
        return showProgressBar;
    }

    public ObservableField<String> getFishImg() {
        return fishImg;
    }

    public ObservableField<String> getFishName() {
        return fishName;
    }

    public ObservableField<String> getFishCatName() {
        return fishCatName;
    }

    public ObservableField<String> getFishSize() {
        return fishSize;
    }

    public ObservableField<String> getFishPrice() {
        return fishPrice;
    }

    public FishViewModel(){
        fish=new Fish(new JSONObject());
    }

    public ObservableField<ListViewAdapter> getSetAdapter() {
        return setAdapter;
    }

    public void sendRequest(Context context){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    Toast.makeText(context,"data fetched",Toast.LENGTH_LONG).show();
                    Toast.makeText(context,"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                    showProgressBar.set(false);
                    JSONArray jsonArray = response;
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            listdata.add(new Fish(jsonObject));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                showResponse(listdata);
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Unable to fetch data",Toast.LENGTH_LONG).show();
                    }
                });
        AppController.getInstance(context).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void showResponse(List<Fish> fishes){
        this.adapter.setFishData(fishes);
        this.adapter.notifyDataSetChanged();
    }
}
