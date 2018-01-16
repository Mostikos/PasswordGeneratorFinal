package com.example.ricardomoreira.passwordgeneratorfinal;

/**
 * Created by RicardoMoreira on 16/01/2018.
 */
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SaxXmlClienteHandler extends MyXmlListHandler<Cliente> {
    private String tempValue;
    private Cliente tempCliente;
    public SaxXmlClienteHandler() {
        osElementos = new ArrayList<Cliente>();
    }
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        tempValue = "";
        if (qName.equalsIgnoreCase("customer"))
            tempCliente = new Cliente();
    }
    @Override
    public void characters(char[] ch, int start, int end) {
        tempValue = new String(ch, start, end);
    }
    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (qName.equalsIgnoreCase("customer"))
            osElementos.add(tempCliente);
        else if (qName.equalsIgnoreCase("id"))
            tempCliente.setId(Integer.parseInt(tempValue));
        else if (qName.equalsIgnoreCase("firstname"))
            tempCliente.setFirstName(tempValue);
        else if (qName.equalsIgnoreCase("lastname"))
            tempCliente.setLastName(tempValue);
        else if (qName.equalsIgnoreCase("city"))
            tempCliente.setCity(tempValue);
        else if (qName.equalsIgnoreCase("street"))
            tempCliente.setStreet(tempValue);
    }
}