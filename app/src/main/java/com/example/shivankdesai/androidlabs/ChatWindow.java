package com.example.shivankdesai.androidlabs;

import android.app.Activity;


import android.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private ListView listView;
    private EditText editText;
    private Button button;
    public static final String ACTIVITY_NAME ="ChatWindow";
    final static ArrayList<String> arrayList = new ArrayList<>();
    public static ChatDatabaseHelper obj;
    public static SQLiteDatabase dataBase;
    public static Cursor cursor;
    public boolean frameExist ;
    private static ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatAdapter = new ChatAdapter(this);
        FrameLayout frame = findViewById(R.id.frameLayout);
        frameExist= (frame!=null);
        obj = new ChatDatabaseHelper(this);
        dataBase = obj.getWritableDatabase();

        listView= findViewById(R.id.ListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle shareData = new Bundle();
                shareData.putString("Message",arrayList.get(i));
                shareData.putInt("Position",i);
                shareData.putLong("DatabaseId",l);
                shareData.putBoolean("FrameExit",frameExist);
                if(frameExist){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    MessageFragment mf= new MessageFragment();
                    mf.setArguments(shareData);
                    ft.replace(R.id.frameLayout,mf);
                    ft.commit();
                }else{
                    Intent intent = new Intent(getApplicationContext(),MessageDetails.class);
                    intent.putExtra("Message",arrayList.get(i));
                    intent.putExtra("Position",i);
                    intent.putExtra("DatabaseId",l);
                    intent.putExtra("FrameExit",frameExist);
                    startActivityForResult(intent,1);
                }
            }
        });
        editText= findViewById(R.id.ChatText);
        button= findViewById(R.id.sendChat);



        cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"
            +cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)) );
            Log.i(ACTIVITY_NAME,"COLUMN NAME: "+ cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE))); //add the item
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 10){
            Bundle bundle= data.getExtras();
            int id = bundle.getInt("Position");
            long dataId = bundle.getLong("DatabaseId");

            Log.i("DtaId",id+"");
            //long dataId=  chatAdapter.getItemId(id);

            dataBase.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID+"="+dataId, null);

            arrayList.remove(id);
            chatAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Message has been deleted",Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public long getItemId(int position) {
        cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
        cursor.moveToPosition(position);
        Long id = cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));

        return id;

    }

}

    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"
                    +cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)) );
            Log.i(ACTIVITY_NAME,"COLUMN NAME: "+ cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE))); //add the item
            cursor.moveToNext();
        }
        chatAdapter.notifyDataSetChanged();
    }

    public static void delete(int id){
        dataBase.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID+"="+id, null);

        cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
        cursor.moveToFirst();
        arrayList.clear();
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"
                    +cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)) );
            Log.i(ACTIVITY_NAME,"COLUMN NAME: "+ cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE))); //add the item
            cursor.moveToNext();
        }
        chatAdapter.notifyDataSetChanged();

        
    }
}