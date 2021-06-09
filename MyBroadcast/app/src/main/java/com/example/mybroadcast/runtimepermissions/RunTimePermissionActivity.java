package com.example.mybroadcast.runtimepermissions;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;

import com.example.mybroadcast.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class RunTimePermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE=200;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_time_permission);

        Button check_permission=(Button) findViewById(R.id.check_permission);
        Button request_permission=(Button) findViewById(R.id.request_permission);

        check_permission.setOnClickListener(this);
        request_permission.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        view=v;
        int id=v.getId();
        switch (id){
            case R.id.check_permission:
                if(checkPermission()){
                    Snackbar.make(view,"Permission already granted",Snackbar.LENGTH_LONG).show();

                }
                else{
                    Snackbar.make(view,"Please request permission",Snackbar.LENGTH_LONG).show();

                }
                break;
            case R.id.request_permission:
                if(!checkPermission()){
                    requestPermission();
                }
                else{
                    Snackbar.make(view,"Permission already granted",Snackbar.LENGTH_LONG).show();
                }
                break;
        }

    }
    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION);
        int result1=ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);

        return result== PackageManager.PERMISSION_GRANTED && result1==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION,CAMERA},PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean locationAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if(locationAccepted && cameraAccepted){
                        Snackbar.make(view,"Permission Granted, Now you can access location and camera",Snackbar.LENGTH_LONG).show();

                    }
                    else{
                        Snackbar.make(view,"Permission Denied",Snackbar.LENGTH_LONG).show();
                            //check if sdk version is > 23   M stands for 23
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)){
                                showMessageOkCancel("You need to allow access to both the permissions",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                                            requestPermissions(new String[]{ACCESS_FINE_LOCATION,CAMERA},PERMISSION_REQUEST_CODE);
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOkCancel(String message,DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(RunTimePermissionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }
}