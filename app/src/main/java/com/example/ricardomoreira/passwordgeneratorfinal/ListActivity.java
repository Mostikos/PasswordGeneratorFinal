package com.example.ricardomoreira.passwordgeneratorfinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    protected DatabaseAdapter a;
    Cursor c;
    List<String> asURL;
    List<Integer> osIds;
    ListView listView;

    @Override
    protected void onStart() {
        super.onStart();
        a = new DatabaseAdapter(this).open();
    }
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onStop() {
        super.onStop();
        a.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (c != null) {
            c.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

       a = new DatabaseAdapter(this).open();

       osIds = new ArrayList<>();
      asURL = new ArrayList<>();

        c = a.getIDURL();
        if (c.moveToFirst()) {
            do {
                osIds.add(c.getInt(0));
                asURL.add(c.getString(1));
            } while (c.moveToNext());
        }

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, asURL);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                c.moveToPosition(position);
                int i = c.getInt(0);
                executarActivity(PasswordActivity.class, i);
            }
        });
    }
    private void executarActivity(Class<?> subActividade, Integer id) {
        Intent x = new Intent(this, subActividade);
        x.putExtra("id", id);
        startActivity(x);
    }
}
