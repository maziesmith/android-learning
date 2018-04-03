package com.example.elina.project_listview;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ArrayList<Person> persons = new ArrayList<>();
    PersonAdapter adapter;
    ListView listView;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        realm = Realm.getDefaultInstance();


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realm.beginTransaction();
                RealmResults<Person> tables = realm.where(Person.class).equalTo("flag", true).findAll();
                String result = "Товары в корзине:";
                if (!tables.isEmpty()) {
                    for (int i = 0; i < tables.size(); i++) {
                        if (tables.get(i).getFlag()) {
                            result += "\n" + tables.get(i).getStatus();
                            persons.remove(tables.get(i));
                            tables.get(i).deleteFromRealm();
                        }
                    }
                }
                realm.commitTransaction();
                Show();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });

        Button buttonclear = (Button) findViewById(R.id.buttonClear);
        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmResults<Person> tables = realm.where(Person.class).findAll();
                realm.beginTransaction();
                persons.clear();
                tables.deleteAllFromRealm();
                realm.commitTransaction();
                Show();
            }
        });
        Button buttonGenerate = (Button) findViewById(R.id.buttonGenerate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePerson();
                Show();
            }
        });

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("aaa0", "a00");
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                Intent intent2 = getIntent();

                String status = intent2.getStringExtra("status");
                String Name = intent2.getStringExtra("firstName");
                String LastName = intent2.getStringExtra("lastName");
                String patronymic = intent2.getStringExtra("patronymic");
                AddToDB(Name, LastName, patronymic, status);
                Show();
            }
        });
        Show();

    }

    void CreatePerson() {
        for (int i = 0; i < 10; i++) {
            String firstName = "Name" + i, lastName = "LastName" + i, patronymic = "Patronymic" + i, status = "status" + i;
            AddToDB(firstName, lastName, patronymic, status);
        }

    }

    public void AddToDB(final String firstName, final String lastName, final String patronymic, final String status) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setPatronymic(patronymic);
                person.setStatus(status);
                person.setFlag(false);
            }
        });

    }

    public void Show() {
        RealmResults<Person> tables = realm.where(Person.class).findAll();
        realm.beginTransaction();
        if (!tables.isEmpty()) {
            for (Person person : tables) {
                persons.add(person);
            }

        }
        realm.commitTransaction();
        adapter = new PersonAdapter(getApplicationContext(), persons);
        listView.setAdapter(adapter);
    }
}