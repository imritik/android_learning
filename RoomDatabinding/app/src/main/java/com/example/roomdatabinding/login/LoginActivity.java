package com.example.roomdatabinding.login;

import android.content.Intent;
import android.os.Bundle;

import com.example.roomdatabinding.MainActivity;
import com.example.roomdatabinding.externalStorageImage.CameraActivity;
import com.example.roomdatabinding.qrCodeExample.QrCodeActivity;
import com.example.roomdatabinding.sessionManager.SessionManagement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabinding.R;

import java.net.Inet4Address;

public class LoginActivity extends AppCompatActivity {
    EditText txtUsername,txtPassword;
    Button btnLogin,btnGoToScanBarcode,btnCamera;

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session=new SessionManagement(getApplicationContext());

        txtUsername=(EditText) findViewById(R.id.txtUsername);
        txtPassword=(EditText) findViewById(R.id.txtPassword);

        Toast.makeText(getApplicationContext(),"Login Status: "+session.isLoggedIn(),Toast.LENGTH_LONG).show();

        btnLogin=(Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txtUsername.getText().toString();
                String password=txtPassword.getText().toString();

                //check if fields are filled
                if(username.trim().length()>0&&password.trim().length()>0){

                    if(username.equals("test")&&password.equals("test")){
                        session.createLoginSession("Android test","test@gmail.com");

                        //starting main Activity
                        Intent i=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter username/password",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGoToScanBarcode=(Button) findViewById(R.id.goToScanPage);
        btnGoToScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, QrCodeActivity.class));
            }
        });

        btnCamera=(Button) findViewById(R.id.goToCameraPage);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CameraActivity.class));
            }
        });

    }
}