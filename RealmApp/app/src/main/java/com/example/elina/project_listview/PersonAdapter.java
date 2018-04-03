package com.example.elina.project_listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Elina on 13.10.2017.
 */

public class PersonAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Person> objects;
    CheckBox cbBuy;
    Realm realm=Realm.getDefaultInstance();

    PersonAdapter(Context context, ArrayList<Person> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.rows, parent, false);
        }

        Person p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        ((ImageView)view.findViewById(R.id.imageView)).setImageResource(R.drawable.person);
        ((TextView) view.findViewById(R.id.textViewStatus)).setText(p.getStatus());
        ((TextView) view.findViewById(R.id.textViewFIO)).setText(p.getFirstName() + " " + p.getLastName() + " " + p.getPatronymic());
        cbBuy = (CheckBox) view.findViewById(R.id.checkBox);
        // присваиваем чекбоксу обработчик


        cbBuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (compoundButton.isChecked()) {
                            getProduct((Integer) compoundButton.getTag()).setFlag(true);
                        }
                    }
                });
            }
        });
        // пишем позицию
        cbBuy.setTag(position);


        return view;
    }

    Person getProduct(int position) {
        return ((Person) getItem(position));
    }


    ArrayList<Person> getBox() {
        ArrayList<Person> box = new ArrayList<Person>();
        for (Person p : objects) {
            if (p.getFlag())
                box.add(p);
        }
        return box;
    }
}
