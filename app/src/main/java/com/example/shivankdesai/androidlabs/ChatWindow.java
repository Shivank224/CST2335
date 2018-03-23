package com.example.shivankdesai.androidlabs;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private ListView listView;
    private EditText editText;
    private Button button;
    public static final String ACTIVITY_NAME ="ChatWindow";
    final ArrayList<String> arrayList = new ArrayList<>();
    public ChatDatabaseHelper obj;
    public SQLiteDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView= findViewById(R.id.ListView);
        editText= findViewById(R.id.ChatText);
        button= findViewById(R.id.sendChat);

        obj = new ChatDatabaseHelper(this);
        dataBase = obj.getWritableDatabase();

       Cursor cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"
            +cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)) );
            Log.i(ACTIVITY_NAME,"COLUMN NAME: "+ cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE))); //add the item
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );
        final ChatAdapter chatAdapter = new ChatAdapter(this);
        listView.setAdapter(chatAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.add(editText.getText().toString());
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, editText.getText().toString());
                dataBase.insert(obj.TABLE_NAME,null,values);
                chatAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dataBase.close();
    }


public class ChatAdapter extends ArrayAdapter<String>{


    public ChatAdapter( Context context) {
        super(context,0);
    }

    public int getCount(){

        return arrayList.size();
    }

    public String getItem (int position)
    {
        return arrayList.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
        View result = null;
        TextView message;
        if(position%2==0) {
            result = inflater.inflate(R.layout.chat_row_incoming, null);
            message = (TextView) result.findViewById(R.id.textView);
        }
        else {
            result = inflater.inflate(R.layout.chat_row_outgoing, null);
            message = (TextView) result.findViewById(R.id.message_text);
        }
        message.setText(getItem(position));
        return result;
    }
}
}