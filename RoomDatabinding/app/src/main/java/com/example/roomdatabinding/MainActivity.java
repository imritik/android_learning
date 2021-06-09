package com.example.roomdatabinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomdatabinding.roomDatabase.MyDatabase;
import com.example.roomdatabinding.roomDatabase.MyUser;
import com.example.roomdatabinding.sessionManager.SessionManagement;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataListener {
    private RecyclerView recyclerView;
    public static MyDatabase myDatabase;
    private MyAdapter adapter;
    List<MyUser> myUsers;
    Button btn,btnLogout;

    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //session class instance
        session=new SessionManagement(getApplicationContext());
        //this will redirect to login acitivity if not logged in
        session.checkLogin();

        myDatabase= Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"myinfo").allowMainThreadQueries().build();

        recyclerView=(RecyclerView) findViewById(R.id.resyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn=(Button) findViewById(R.id.insertdata);
        btnLogout=(Button) findViewById(R.id.logout);
        getdata();
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              showEntryBox();
          }
      });



      //swipe to delete
      new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
              return false;
          }

          @Override
          public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
              MainActivity.myDatabase.myDao().DeleteUser(adapter.getUserAt(viewHolder.getAdapterPosition()));
              getdata();

              Toast.makeText(MainActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
          }
      }).attachToRecyclerView(recyclerView);
    }

    private void showEntryBox(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.entry_box,null);

        final EditText etname=(EditText) view.findViewById(R.id.etname);
        final EditText etemail=(EditText)view.findViewById(R.id.etemail);
        final EditText etcity=(EditText)view.findViewById(R.id.etcity);

        builder.setView(view);
        builder.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name=etname.getText().toString();
                final String email=etemail.getText().toString();
                final String city=etcity.getText().toString();
                MyUser myUserData=new MyUser();
                myUserData.setUsername(name);
                myUserData.setUseremail(email);
                myUserData.setUsercity(city);
                MainActivity.myDatabase.myDao().AddData(myUserData);
                getdata();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void getdata(){
        List<MyUser>myUsers=new ArrayList<>();
        MyUser myUser=new MyUser();
        myUsers=MainActivity.myDatabase.myDao().getMyData();
        adapter=new MyAdapter(myUsers, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_users:
                MainActivity.myDatabase.myDao().DeleteAll();
                getdata();
                Toast.makeText(this,"All users deleted",Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                session.logoutUser();
                return true;
            case R.id.search_by_name:
                SearchView searchView=(SearchView) item.getActionView();
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showDetailsToUpdate(MyUser myUser){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.entry_box,null);

        final EditText etname=(EditText) view.findViewById(R.id.etname);
        final EditText etemail=(EditText)view.findViewById(R.id.etemail);
        final EditText etcity=(EditText)view.findViewById(R.id.etcity);

        etname.setText(myUser.getUsername());
        etemail.setText(myUser.getUseremail());
        etcity.setText(myUser.getUsercity());

        builder.setView(view);
        builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String name=etname.getText().toString();
                final String email=etemail.getText().toString();
                final String city=etcity.getText().toString();
                MyUser myUserData=new MyUser();
                myUserData.setUsername(name);
                myUserData.setUseremail(email);
                myUserData.setUsercity(city);
                MainActivity.myDatabase.myDao().UpdateUser(myUser.getId(),myUserData.getUsername(),myUserData.getUseremail(),myUserData.getUsercity());
                getdata();
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onItemClicked(MyUser myUser) {
        Log.d("userCLicked",myUser.getUseremail());
        showDetailsToUpdate(myUser);
    }
}