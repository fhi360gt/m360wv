package com.org.fhi360.m360wv.xmlparser;

import android.util.Log;

//import com.example.george.reach.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by George on 21/02/2015.
 */
public class XMLParserTable {
    private String jsonSend;

    public String getJson() {
        return jsonSend;
    }

    public void convertToJSON (String xml_form, String xml_path, String macAdr)
            throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        String tagName="";
        String coma = "";
        String xml_res = "";
        int ncolums = 0;
        ArrayList<String> listColum = new ArrayList<String>();
        jsonSend =  "{\"" + xml_form.replace(".", "_").replace("-","_") + "\":[{\"maddress\":\"" + macAdr + "\"," ;

        boolean createTable = true;
        File file = new File(xml_path);

        FileInputStream fis = new FileInputStream(file);

        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");

            } else if(eventType == XmlPullParser.TEXT) {
                if (xpp.getText().contains("uuid:")){} else {System.out.println(tagName+" - "+xpp.getText());
                    xml_res = xml_res + tagName+" - "+xpp.getText()+"\n";
                    if (createTable) {
                        jsonSend = jsonSend + coma + "\"" + tagName + "\":";
                        coma = ", ";
                        listColum.add(tagName);
                    }

                    jsonSend = jsonSend + "\"" + xpp.getText() + "\"";
                    ncolums++;
                }
                System.out.println("Text "+xpp.getText());
            } else if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("data")){}
                else if (xpp.getName().equals("meta")){}
                else if (xpp.getName().equals("instanceID")) {}
                else if (xpp.getName().equals("primary")) {tagName="primary_";}
                else {tagName=xpp.getName();}
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End tag "+xpp.getName());
                createTable = false;
            }
            eventType = xpp.next();


        }
        System.out.println("End document");
        jsonSend =jsonSend + "}]}";
        Log.d("xml_res ->",  jsonSend);
        System.out.println(createTable);

    }


    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
