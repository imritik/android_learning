package com.example.mybroadcast;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.example.mybroadcast.SwipeRefresh.SwipeToRefresh;
import com.example.mybroadcast.checkboxExample.CheckboxActivity;
import com.example.mybroadcast.runtimepermissions.RunTimePermissionActivity;
import com.example.mybroadcast.tabexample.TabActivity;


public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver MyReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyReceiver = new MyReceiver();
        TextView click=findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastIntent();
            }
        });

//        Button nextActivityBtn=findViewById(R.id.get_intent);
//        nextActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override

//        });
    }
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }
    public void onNextActivityButtonClick(View view) {
        Intent intent=new Intent(getBaseContext(),intentActivity.class);
        startActivity(intent);
    }
    public void onRecyclerViewBtn(View view){
        Intent recyclerIntent= new Intent(getBaseContext(), RecyclerActivity.class);
        startActivity(recyclerIntent);
    }

    public void  onBottomNavigationBtn(View view){

        Intent bottomNavigationIntent=new Intent(getBaseContext(),BottomNavigate.class);
        startActivity(bottomNavigationIntent);
    }

    public void onAutocompleteBtn(View v){
        Intent typeAheadAdapter=new Intent(getBaseContext(),AutoAdapter.class);
        startActivity(typeAheadAdapter);
    }

    public void onShimmerBtn(View view){
        Intent shimmerIntent=new Intent(getBaseContext(),ShimmeringActivity.class);
        startActivity(shimmerIntent);
    }

    public void onTabLayoutBtn(View view){
        Intent tabIntent=new Intent(getBaseContext(), TabActivity.class);
        startActivity(tabIntent);
    }
    public void onSwipeRefreshBtn(View view){
        Intent swipeIntent=new Intent(getBaseContext(), SwipeToRefresh.class);
        startActivity(swipeIntent);
    }
public void onRunTimeBtn(View view){
        Intent runTimPermissionIntent=new Intent(getBaseContext(), RunTimePermissionActivity.class);
        startActivity(runTimPermissionIntent);
}
public void onCheckboxBtn(View view){
        Intent checkboxIntent=new Intent(getBaseContext(), CheckboxActivity.class);
        startActivity(checkboxIntent);
}
    public void onSendNotificationClick(View view){


        Intent notificationIntent=new Intent(this,NotificationView.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);
       PendingIntent contentIntent=PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=26){
            String id="channel_1";
            String description="143";
            int importance=NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel=new NotificationChannel(id,description,importance);
            channel.enableLights(true);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);

            Notification notification=new Notification.Builder(MainActivity.this,id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notification Example")
                    .setContentText("This is test notification")
                    .setContentIntent(contentIntent)
                    .build();
            manager.notify(1,notification);

        }
        else{
            Notification notification=new NotificationCompat.Builder(MainActivity.this)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            manager.notify(1,notification);
        }
       }

}