package com.carlos.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    Button takePhoto;
    static final int PERMISSION_CODE = 10;
    static final int code_camera=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo_plano);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadComonents();


    }

    private void loadComonents() {
        takePhoto=this.findViewById(R.id.registro);
        takePhoto.setOnClickListener(this);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            this.requestPermissions(new String[]{Manifest.permission.CAMERA},PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_CODE){
            if (permissions.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    callCamera();
                } else {
                    Toast.makeText(this, "NO TIENES PERMISOS con el registro por q es necesario una foto tuya ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean checkPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result== PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onClick(View view) {
        if(checkPermission(Manifest.permission.CAMERA)){
            callCamera();
            //Toast.makeText(this, "TIENES PERMISOS ", Toast.LENGTH_LONG).show();
        }else {
            requestPermissions();
            //Toast.makeText(this, "NO TIENES PERMISOS ", Toast.LENGTH_LONG).show();
        }
    };
    private void callCamera(){
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(this.getPackageManager())!= null){
            this.startActivityForResult(camera,code_camera);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code_camera && resultCode == RESULT_OK) {
            Bundle photo = data.getExtras();
            Bitmap imageBitmap = (Bitmap) photo.get("data");
            ImageView img = this.findViewById(R.id.image2);
            img.setImageBitmap(imageBitmap);
        }
    }




    }