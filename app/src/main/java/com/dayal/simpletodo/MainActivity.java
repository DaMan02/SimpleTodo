package com.dayal.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText txt;
    Button btn;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SIMPLE TODO");
        //toolbar.setTitleTextAppearance(getApplicationContext(),R.style.AppTheme);       //make text bold(sic)
        toolbar.setTitleTextColor(Color.parseColor("#2E7D32"));
        txt = (EditText) findViewById(R.id.editTxt);
        btn = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.list);
       // registerForContextMenu(listView);
        // txt.clearFocus();

        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = txt.getText().toString();
                        if (arrayList.contains(input))
                            Toast.makeText(getBaseContext(), "Item already added", Toast.LENGTH_SHORT).show();
                        else if (input == null || input.trim().equals(""))
                            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_SHORT).show();
                        else
                            arrayList.add(input);
                        listView.setAdapter(adapter);
                        txt.setText(" ");
                    }
                }
        );
        try {
            Scanner sc = new Scanner(openFileInput("todo.txt"));
            while (sc.hasNextLine()) {                  //until data has a next line
                String data = sc.nextLine();                 //jump to next line saving curr into "data"
                arrayList.add(data);
                listView.setAdapter(adapter);            //to show list on startup

            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
              //  intent.setClass(MainActivity.this, EditActivity.class);
                intent.putExtra("edit_data", arrayList.get(position).toString());
                intent.putExtra("edit_position", position);
                startActivityForResult(intent, IntentValuesClass.REQUEST_CODE);
            }


        });
    }
        @Override
        protected void onActivityResult( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
           Log.w(IntentValuesClass.Msg,"Inside onActivityResult");
            int position;
            if (requestCode == IntentValuesClass.REQUEST_CODE) {
                if (resultCode==1) {
                    Log.w(IntentValuesClass.Msg,"Result ok");
                    String s = data.getStringExtra("edited_text");
                    position = data.getIntExtra("position",1);
                    arrayList.remove(position);
                    arrayList.add(position, s);
                    adapter.notifyDataSetChanged();


                }
                //else  Toast.makeText(getApplicationContext(),"Failed to edit",Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onStop() {
        super.onStop();
        try {
            PrintWriter pw = new PrintWriter(openFileOutput("todo.txt", Context.MODE_PRIVATE)); //open file .txt for writing
            for (String data : arrayList)
                pw.println(data);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.w(IntentValuesClass.Msg,"Stopped");
        finish();
    }
}

