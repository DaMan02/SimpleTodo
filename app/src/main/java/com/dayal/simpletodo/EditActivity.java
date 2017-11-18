package com.dayal.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    String msg;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText  editText;
        TextView txt;
        String editInput;
        editText=(EditText)findViewById(R.id.editTxt2);
        //Intent intent=new Intent();
        msg=getIntent().getStringExtra("edit_data");
        pos=getIntent().getIntExtra("edit_position",1);
        txt=(TextView)findViewById(R.id.txtView);
        txt.setText(R.string.edit +" "+msg);
        editInput=editText.getText().toString();
         editText.setText(editInput);
    }
    public void BtnSaveClicked(View v){

        Intent data=new Intent();
        String toSend=((EditText)findViewById(R.id.editTxt2)).getText().toString();
        data.putExtra("edited_text",toSend);
        data.putExtra("position",pos);
        setResult(1,data);
        Log.w(IntentValuesClass.Msg,"Button SAVE Clicked");
        Log.w(IntentValuesClass.Msg,"finished");
        finish();

    }
}
