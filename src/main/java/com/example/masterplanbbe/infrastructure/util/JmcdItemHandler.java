package com.example.masterplanbbe.infrastructure.util;

import com.example.masterplanbbe.presentation.response.JmcdApiResponse;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class JmcdItemHandler extends DefaultHandler {
    private final List<JmcdApiResponse.Item> items = new ArrayList<>();
    private StringBuilder content = new StringBuilder();

    private Integer jmcd;
    private String specName;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("item")) {
            jmcd = null;
            specName = null;
        }
        content.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "jmcd" -> jmcd = Integer.parseInt(content.toString());
            case "jmfldnm" -> specName = content.toString();
            case "item" -> items.add(new JmcdApiResponse.Item(jmcd, specName));
        }
    }

    public List<JmcdApiResponse.Item> getItems() {
        return items;
    }
}
