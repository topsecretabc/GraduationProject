package com.licong.graduationproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.licong.graduationproject.db.MyDatabaseHelper;

public class ToTextAppActivity extends AppCompatActivity {
    private MyDatabaseHelper  dbHelper;
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_text_app_layout);
        dbHelper =new MyDatabaseHelper(this);
        //getWritableDatabase()或getWritableDatabase()能创建数据
        //同时返回一个SQLiteDatabase对象
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        editText =(EditText)findViewById(R.id.textapp_edittext);
        button = (Button)findViewById(R.id.textapp_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                ContentValues values = new ContentValues();
                values.put("textapptext", inputText);
                db.insert("Textapp", null, values);
                Log.e("lciong","asd"+values);


            }
        });

    }
}
