package com.example.shivankdesai.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    protected static final int REQUEST_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(e -> clickHandler());
        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME,"User clicked Start Chat");
                startActivity(new Intent(StartActivity.this,ChatWindow.class));
            }
        });
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME,"User clicked Forecast");
                startActivity(new Intent(StartActivity.this,WeatherForecast.class));
            }
        });
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
        super.onActivityResult(requestCode, resultCode, data);
        String message = data.getStringExtra("Response");
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
    }

    public void clickHandler() {

        Intent intent= new Intent(getApplicationContext(),ListItemsActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

}
