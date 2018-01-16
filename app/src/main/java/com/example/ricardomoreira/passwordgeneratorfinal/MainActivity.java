package com.example.ricardomoreira.passwordgeneratorfinal;



        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.ArrayList;
        import java.util.Random;
/*falta
        proteger input
        adicionar autocomplete com ligação a internet
        mudar design*/


public class MainActivity extends Activity {
    TextView  textViewPassword ;
    CheckBox checkboxLetters, checkboxNumbers, checkBoxSymbols;
    Button buttonGenerate , buttonSave;
    EditText editTextLength;


    ArrayList<Character> selection = new ArrayList<>();
    String strLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Character[] letters = new Character[strLetters.length()];
    String strNumbers = "0123456789";
    Character[] numbers= new Character[strNumbers.length()];
    String strSymbols = "@£§€%&/(){}[]=?*-+;:";
    Character[] symbols = new Character[strSymbols.length()];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewPassword.setEnabled(false);
        editTextLength = (EditText) findViewById(R.id.editTextLength);
        buttonGenerate = (Button) findViewById(R.id.buttonGenerate);
        buttonSave = (Button) findViewById(R.id.buttonSave);


        for (int k = 0; k < letters.length; ++k){
            letters[k] = Character.valueOf(strLetters.toLowerCase().charAt(k));
            selection.add(letters[k]);

        }

        buttonGenerate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String value = editTextLength.getText().toString();
                if(!value.equals("")){
                    final int length = Integer.parseInt(value);
                    textViewPassword.setText(generatePassword(length));

                }else{
                    Toast.makeText(MainActivity.this, "You have to define a length to your password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String password = textViewPassword.getText().toString();
                Intent myIntent =new Intent(MainActivity.this,SaveActivity.class);
                myIntent.putExtra("password" , password);
                MainActivity.this.startActivity(myIntent);
         }
        });
    }

    public void selectItem(View view){

        checkboxLetters =(CheckBox)view.findViewById(R.id.checkBoxLetters);
        checkboxNumbers =(CheckBox)view.findViewById(R.id.checkBoxNumbers);
        checkBoxSymbols = (CheckBox)view.findViewById(R.id.checkBoxSymbols);

        boolean checked = ((CheckBox)view).isChecked();

        //switch para adicionar uppercase, numeros e simbolos ao arraylist , caso as checkbox estejam selecionadas
        switch(view.getId()){
            case R.id.checkBoxLetters:
                if(checked){

                    for (int i = 0 ; i < strLetters.length() ; i++){
                        letters[i] = Character.valueOf(strLetters.charAt(i));
                        selection.add(letters[i]);
                    }
                }else {
                    for (int i = 0; i < strLetters.length(); i++) {
                        letters[i] =  Character.valueOf(strLetters.charAt(i));
                        selection.remove(letters[i]); // crash remove
                    }
                }
                break;

            case R.id.checkBoxNumbers:
                if(checked){
                    for (int i = 0 ; i < strNumbers.length() ; i++){
                        numbers[i] = Character.valueOf(strNumbers.charAt(i));
                        selection.add(numbers[i]);
                    }
                }else {
                    for (int i = 0 ; i < strNumbers.length() ; i++){
                        numbers[i] = Character.valueOf(strNumbers.charAt(i));
                        selection.remove(numbers[i]);
                    }
                }
                break;
            case R.id.checkBoxSymbols:
                if(checked){
                    for (int i = 0 ; i < strSymbols.length() ; i++){
                        symbols[i] = Character.valueOf(strSymbols.charAt(i));
                        selection.add(symbols[i]);
                    }


                }else {
                    for (int i = 0 ; i < strSymbols.length() ; i++){
                        symbols[i] = Character.valueOf(strSymbols.charAt(i));
                        selection.remove(symbols[i]);
                    }
                }
                break;
        }
    }
    public String generatePassword (int length){
        StringBuilder stringBuilder = new StringBuilder();
        Random random =  new Random();
        for (int i= 0 ;i < length  ; i++ ){
            int index = random.nextInt(selection.size());
            char c = selection.get(index);
            stringBuilder.append(c);
        }
        return  stringBuilder.toString();
    }
}

