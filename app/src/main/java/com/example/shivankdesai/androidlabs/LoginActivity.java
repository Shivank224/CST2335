package com.example.shivankdesai.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

    SharedPreferences prefs;

    protected static final String ACTIVITY_NAME = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginText = (EditText)findViewById(R.id.UserName);
        prefs= this.getSharedPreferences("DefaultEmail", Context.MODE_PRIVATE);
        String defaultText = prefs.getString("DefaultEmail","email@domain.com");
        if(defaultText != null){
           loginText.setText(defaultText);

       }
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

    public void callBack(View view) {
        EditText loginText = (EditText)findViewById(R.id.UserName);
        SharedPreferences.Editor edit =prefs.edit();
        edit.putString("DefaultEmail", String.valueOf(loginText.getText()));
        edit.commit();
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);

    }
}
