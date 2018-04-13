package com.example.shivankdesai.androidlabs;

import android.app.Activity;



import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


public class MessageDetails extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

         Intent intent = getIntent();


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        MessageFragment mf= new MessageFragment();
        Bundle shareData = new Bundle();
        shareData.putString("Message",intent.getStringExtra("Message"));
        shareData.putInt("Position",
                intent.getIntExtra("Position",0)
        );
        shareData.putLong("DatabaseId",intent.getLongExtra("DatabaseId",0));
        shareData.putBoolean("FrameExit",intent.getBooleanExtra("FrameExit",false));
        mf.setArguments(shareData);
        ft.add(R.id.emptyFrame,mf);
        ft.commit();
    }
}
