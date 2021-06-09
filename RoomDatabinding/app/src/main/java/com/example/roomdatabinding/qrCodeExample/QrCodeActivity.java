package com.example.roomdatabinding.qrCodeExample;

import android.content.Intent;
import android.os.Bundle;

import com.example.roomdatabinding.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.example.roomdatabinding.R;

public class QrCodeActivity extends AppCompatActivity {
    Button btnScanBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        btnScanBarCode=findViewById(R.id.btnScanBarcode);

        btnScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QrCodeActivity.this,ScannedBarcodeActivity.class));
            }
        });
    }
}