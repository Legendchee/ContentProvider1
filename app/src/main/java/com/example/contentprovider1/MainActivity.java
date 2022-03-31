package com.example.contentprovider1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri_user = Uri.parse("content://com.example.contentprovider1/user");

        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name","Iverson");

        Log.d(TAG, "onCreate: puppet111111111111");
        ContentResolver resolver = getContentResolver();
        resolver.insert(uri_user,values);

        Log.d(TAG, "onCreate: puppet2222222222");


        Cursor cursor = resolver.query(uri_user,new String[]{"_id","name"},null,null,null);
        while ((cursor.moveToNext())){
            System.out.println("query bool:"+cursor.getInt(0)+" "+cursor.getString(1));
        }
        cursor.close();

        Uri uri_job = Uri.parse("content://com.example.contentprovider1/job");
        ContentValues values1 = new ContentValues();
        values1.put("_id",3);
        values1.put("job","NBA Player");
        Log.d(TAG, "onCreate: puppet33333");

        ContentResolver resolver1 = getContentResolver();
        resolver1.insert(uri_job,values1);
        Log.d(TAG, "onCreate: puppet444444444");

        Cursor cursor1 = resolver1.query(uri_job,new String[]{"_id","job"},null,null,null);
        while (cursor1.moveToNext()){
            System.out.println("query job:"+cursor1.getInt(0)+" "+cursor1.getString(1));
        }
        cursor1.close();
    }
}