package com.example.ricardomoreira.passwordgeneratorfinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class SaveActivity extends Activity {

    EditText editTextURL , editTextUsername ;
    TextView textViewMyPassword;
    Button buttonSave ,buttonSendEmail;
    protected DatabaseAdapter a;
    List<String> asURL;
    int indice;
    Cursor c ;

    private void executarOutraActivity(Class<?> subActividade, ArrayList<String> oValor) {
        Intent x = new Intent(this, subActividade);
        x.putExtra("asURL", oValor);
        startActivity(x);
    }

    @Override
    protected void onStart() {
        super.onStart();
        a = new DatabaseAdapter(this).open();
        indice = -1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        a.close();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        String password = intent.getStringExtra("password");
        editTextURL = (EditText) findViewById(R.id.editTextURL);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        textViewMyPassword = (TextView) findViewById(R.id.textViewMyPassword);
        textViewMyPassword.setText(password);
        buttonSave = (Button)findViewById(R.id.buttonSave);




       buttonSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if (!editTextURL.getText().toString().equals("") || !editTextUsername.getText().toString().equals("")) {
                    a.inserir(editTextURL.getText().toString(), editTextUsername.getText().toString(), textViewMyPassword.getText().toString());
//                    editTextURL.setText("");
//                    editTextUsername.setText("");
//                    textViewMyPassword.setText("");
                    asURL = a.obterTodasURLS();
                    executarOutraActivity(ListActivity.class, (ArrayList<String>)asURL);
                    Toast.makeText(getApplicationContext(), "Password Saved.", Toast.LENGTH_SHORT).show();
                } else if(editTextURL.getText().toString().equals("") ) {
                    Toast.makeText(getApplicationContext(), "Assign a URL to your password.", Toast.LENGTH_SHORT).show();
                } else if(editTextUsername.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Assign a Email to your password.", Toast.LENGTH_SHORT).show();
                }



            }
        });


        buttonSendEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = editTextUsername.getText().toString();
                String url = editTextURL.getText().toString();
                String password = textViewMyPassword.getText().toString();
                String[] TO = {email};
                String[] CC = {email};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Password ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "URL : " + url + "/n" + "Password : " + password);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();


                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SaveActivity.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}

