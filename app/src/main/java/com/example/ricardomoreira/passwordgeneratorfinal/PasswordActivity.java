package com.example.ricardomoreira.passwordgeneratorfinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PasswordActivity extends Activity {

    protected DatabaseAdapter a;
    Cursor c;
    Intent oIntent;
    TextView textViewURL, textViewUsername, textViewPassword;


    @Override
    protected void onStart() {
        super.onStart();

        a = new DatabaseAdapter(this).open();
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
        setContentView(R.layout.activity_password);
        oIntent = getIntent();
        Integer id = oIntent.getExtras().getInt("id");
        a = new DatabaseAdapter(this).open();
        c = a.getId(id);
        c.moveToFirst();
        textViewURL = (TextView)findViewById(R.id.textViewURL);
        textViewURL.setText(c.getString(1));
        textViewUsername = (TextView)findViewById(R.id.textViewUsername);
        textViewUsername.setText(c.getString(2));
        textViewPassword = (TextView)findViewById(R.id.textViewPassword);
        textViewPassword.setText(c.getString(3));

    }
}
