package com.example.elina.project_listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    EditText editTextStatus, editTextName, editTextPatronymic, editTextLastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        editTextStatus=(EditText)findViewById(R.id.editText);
        editTextName=(EditText)findViewById(R.id.editText2);
        editTextLastName=(EditText)findViewById(R.id.editText3);
        editTextPatronymic=(EditText)findViewById(R.id.editText4);
        Button button=(Button)findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("status", editTextStatus.getText().toString());
                intent.putExtra("firstName", editTextName.getText().toString());
                intent.putExtra("lastName",editTextLastName.getText().toString());
                intent.putExtra("patronymic",editTextPatronymic.getText().toString());
                startActivity(intent);

            }
        });
    }

}
