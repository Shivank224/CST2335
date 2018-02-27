package com.example.shivankdesai.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";

    private static final int REQUEST_IMAGE_CAPTURE=1;
    ImageButton imageButton;
    Switch switchButton;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(e -> {
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.CAMERA  },100);
            }
            else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });
        switchButton = findViewById(R.id.switch1);
        checkBox = findViewById(R.id.checkBox);
        switchButton.setOnClickListener(e -> setOnCheckedChanged());
        checkBox.setOnClickListener(e->OnCheckChanged());
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);

        }
    }

    protected void setOnCheckedChanged(){
        Toast toast;
        if(switchButton.isChecked())
            toast = Toast.makeText(this ,"Switch is On" , Toast.LENGTH_SHORT);
        else {
            toast = Toast.makeText(this ,"Switch is Off" , Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    protected void OnCheckChanged(){
        if(checkBox.isChecked()) {
           AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
           builder.setTitle(R.string.dialog_title);
           builder.setMessage(R.string.dialog_message);
           builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   Intent resultIntent = new Intent( );
                   resultIntent.putExtra("Response", "Here is my response");
                   setResult(Activity.RESULT_OK, resultIntent);
                   finish();
               }
           });
           builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.dismiss();
                  checkBox.setChecked(false);
               }
           });

             builder.show();
        }
    }
}
