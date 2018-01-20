package com.example.ricardomoreira.passwordgeneratorfinal;



        import java.util.Properties;

        import javax.mail.Address;
        import javax.mail.Authenticator;
        import javax.mail.Message;
        import javax.mail.MessagingException;
        import javax.mail.PasswordAuthentication;
        import javax.mail.Session;
        import javax.mail.Transport;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;



public class SendEmail extends Activity {

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    TextView reciep, sub, msg;
    String  message ,email;
    Button submit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String password = intent.getStringExtra("password");



        context = this;

        submit = (Button) findViewById(R.id.btn_submit);
        reciep = (TextView) findViewById(R.id.et_to);
        sub = (TextView) findViewById(R.id.et_sub);
        msg = (TextView) findViewById(R.id.et_text);
        reciep.setText(email);
        sub.setText("Your Password is::");
        msg.setText(message);





        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.port", "465");

                session = Session.getDefaultInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("passSendINC@gmail.com", "W:y[PdRI");
                    }
                });

                pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

                RetreiveFeedTask task = new RetreiveFeedTask();
                task.execute();
            }
        });

   }

    protected class  RetreiveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("passSendINC@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Your Generated Password");
                message.setContent(message, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            msg.setText("");
            sub.setText("");
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }

    }
}

