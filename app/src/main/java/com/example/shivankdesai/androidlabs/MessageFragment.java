package com.example.shivankdesai.androidlabs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by shivankdesai on 2018-04-03.
 */

public class MessageFragment extends Fragment{

    TextView txtId ;
    TextView txtMessage;

    boolean doublePane;

    public MessageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        View tabletView = view.findViewById(R.id.frameLayout);
        txtId= view.findViewById(R.id.txtID);
        txtMessage= view.findViewById(R.id.MessageTxtHere);
        Bundle data= getArguments();
         String message=data.getString("Message");
         int id = data.getInt("Position");
         long databaseId = data.getLong("DatabaseId");
         txtMessage.setText("Message: " + message);
         txtId.setText("ID: "+id);
         doublePane = data.getBoolean("FrameExit",false);
        Button btnDeleteMsg = view.findViewById(R.id.Delete);
        btnDeleteMsg.setOnClickListener((View v) -> {
            getActivity().setResult(10,getActivity().getIntent().putExtras(data));
            if(doublePane) {
                ChatWindow.delete((int)databaseId);
            }else{

                getActivity().finish();
            }


        });
        return view;
    }
}
