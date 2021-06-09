package com.example.roomdatabinding.externalStorageImage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.roomdatabinding.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CameraActivity extends AppCompatActivity {
    ImageView viewImage;
    Button b;

//    private static final int PERMISSION_REQUEST_CODE<100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        b=(Button) findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView) findViewById(R.id.viewImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    private void selectImage(){
        final CharSequence[] options={"Take Photo","Choose from Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(CameraActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which].equals("Take Photo")){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f=new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                    startActivityForResult(intent,1);
                }
                else if(options[which].equals("Choose from Gallery")){
                    Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
                }
                else if(options[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==1){
                File f=new File(Environment.getExternalStorageDirectory().toString());
                for(File temp:f.listFiles()){
                    if(temp.getName().equals("temp.jpg")){
                        f=temp;
                        break;
                    }
                }
                try{
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
                    bitmap=BitmapFactory.decodeFile(f.getAbsolutePath(),bitmapOptions);
                    viewImage.setImageBitmap(bitmap);
                    String path=android.os.Environment.getExternalStorageDirectory()+File.separator+"Phoenix"+File.separator+"default";
                    f.delete();
                    OutputStream outFile=null;
                    File file=new File(path,String.valueOf(System.currentTimeMillis())+".jpg");
                    try{
                        outFile=new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,85,outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode==2){
                Uri selectedImage=data.getData();
                String[] filePath={MediaStore.Images.Media.DATA};
                Cursor cursor=getContentResolver().query(selectedImage,filePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex= cursor.getColumnIndex(filePath[0]);
                String picturePath= cursor.getString(columnIndex);
                cursor.close();
                Bitmap thumbnail=(BitmapFactory.decodeFile(picturePath));
                Log.w("path of from gallery",picturePath);
                viewImage.setImageBitmap(thumbnail);

            }
        }
    }

//    private boolean checkPermission() {
//        int result<ContextCompat.checkSelfPermission(CameraActivity.this,     android.Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (result<= PackageManager.PERMISSION_GRANTED) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(CameraActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            Toast.makeText(CameraActivity.this, "Write External Storage permission allows us to read  files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(CameraActivity.this, new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0]<= PackageManager.PERMISSION_GRANTED)  {
//                Log.e("value", "Permission Granted, Now you can use local drive .");
//            } else {
//                Log.e("value", "Permission Denied, You cannot use local drive .");
//            }
//            break;
//        }
//    }
}