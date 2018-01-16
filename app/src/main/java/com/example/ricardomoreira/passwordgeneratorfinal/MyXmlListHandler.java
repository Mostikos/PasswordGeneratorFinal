package com.example.ricardomoreira.passwordgeneratorfinal;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class MyXmlListHandler<E> extends DefaultHandler {
    protected List<E> osElementos;
    public List<E> obterElementos() {
        return osElementos;
    }

}
