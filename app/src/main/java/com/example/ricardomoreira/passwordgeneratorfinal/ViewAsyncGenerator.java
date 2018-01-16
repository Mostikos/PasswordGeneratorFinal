package com.example.ricardomoreira.passwordgeneratorfinal;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

public class ViewAsyncGenerator extends AsyncTask<Integer, Double, String> {
    protected double percentage;
    protected Button b;
    protected TextView t;
    protected String savedLabel;
    protected int port;
    protected String host;
    protected String path;
    protected boolean ignorar;
    public ViewAsyncGenerator(Button botao, String host, String path, int port, TextView t) {
        b = botao;
        this.host = host;
        this.path = path;
        this.port = port;
        this.t = t;
    }
    @Override // runs on the GUI thread
    protected void onPreExecute() {
        ignorar = false;
        savedLabel = b.getText().toString();
        b.setText("Wait ... 0%");
        b.setEnabled(false);
    }
    @Override // runs on its own thread
    protected String doInBackground(Integer... args) {
        if (ignorar)
            return "";
        return Comunicar.contactar2(host, path, port);
    }
    @Override // runs on the GUI thread
    protected void onProgressUpdate(Double... percentComplete) {
        String theText;
        percentage = percentComplete[0];
        theText = "" + (int)(percentage * 100) + "%";
        b.setText(theText);
    }
    @Override // runs on the GUI thread
    protected void onPostExecute(String s) {
        List<Cliente> listaClientes = null;
        //b.setText(savedLabel);
        //b.setEnabled(true);
        ArrayList<String> aLista = new ArrayList<String>(10);
        try {
            SaxXmlParser<Cliente, SaxXmlClienteHandler> oMeuParser =
                    new SaxXmlParser<Cliente, SaxXmlClienteHandler>();
            oMeuParser.setHandler(new SaxXmlClienteHandler());
            listaClientes = oMeuParser.parse(new StringReader(s));
        } catch (Exception e) {
            aLista.add(e.toString());  // devolve a excepção obtida
            t.setText("excepção : " + s);
        }
        String osNomes = "";
        Cliente umCliente = null;
        for (int k = 0; k < listaClientes.size(); ++k) {
            umCliente = listaClientes.get(k);
            aLista.add(umCliente.getFirstName() + "-" + umCliente.getLastName() + "\n");
            osNomes = osNomes + umCliente.getFirstName() + "-" + umCliente.getLastName() + "\n";
        }
        s = osNomes;
        t.setText(s);
    }
}