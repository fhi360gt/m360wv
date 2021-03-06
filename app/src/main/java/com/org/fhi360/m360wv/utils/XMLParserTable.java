package com.org.fhi360.m360wv.utils;

import android.util.Log;

import com.org.fhi360.m360wv.main;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jlgarcia on 03/01/2017.
 * XMLParserTable, construye un JSON partiendo de la lectura de un Archivo XML
 */

public class XMLParserTable {

    public static void main (String args[])
            throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        String tagName="";

        //obtener macAddress


        //System.out.println(ActivitySelForm.xml_form.replace(".", "_"));
        String coma = "", tipo = "",  jsonSend =  "{\"" + main.xml_form.replace(".", "_") + "\":[{\"maddress\":\"" + main.macAdr + "\"," , sql="CREATE TABLE IF NOT EXISTS " + main.xml_form.replace(".", "_") + " (";
        boolean createTable = true;
        //int tagN=0, tagT=0;

        // get a reference to the file.
        String xml_path = main.xml_path;
        File file = new File(xml_path);
        //File file = new File(Environment.getExternalStorageDirectory() + File.separator +"/odk/instances/TanzaniaGIS_2014-10-28_22-36-39/TanzaniaGIS_2014-10-28_22-36-39.xml");

        // create an input stream to be read by the stream reader.
        FileInputStream fis = new FileInputStream(file);

        // set the input for the parser using an InputStreamReader
        xpp.setInput(new InputStreamReader(fis));

        //FileInputStream fil = openFileInput("//storage/extSdCard/odk/instances/TanzaniaGIS_2014-10-28_17-23-04/prueba.xml");
        //file:///storage/extSdCard/odk/instances/TanzaniaGIS_2014-10-28_17-23-04;
        //xpp.setInput( new StringReader ( "<foo>Hello World!</foo>" ) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");

            } else if(eventType == XmlPullParser.TEXT) {
                if (xpp.getText().contains("uuid:")){} else {System.out.println(tagName+" - "+xpp.getText());
                    main.xml_res = main.xml_res + tagName+" - "+xpp.getText()+"\n";
                    if (createTable) {
                        // Adding items to arrayList
                        if (isNumeric(xpp.getText())) {tipo = " int";} else {tipo = " text";}
                        //sql = sql + coma + tagName + tipo;
                        jsonSend = jsonSend + coma + "\"" + tagName + "\":";
                        coma = ", ";
                        //if (tipo.equals(" int")) {ActivityForms.listColum.add(tagName);}  // solo muestra variables numericas
                        main.listColum.add(tagName);   // muestra variables numericas y alfanumericas
                    }

                    // Adding values to recordSet
                    jsonSend = jsonSend + "\"" + xpp.getText() + "\"";
                    //MainActivity.valores.put(tagName, xpp.getText());
                    //MainActivity.texts.add("Reg. " + MainActivity.nReg +  " : " + tagName + " \n" + xpp.getText());
                    main.ncolums++;
                    // temporal, solo para mostrar los datos
                    //ActivitySelForm.xml_temp = ActivitySelForm.xml_temp + xpp.getText().toString() + ", ";   // llena el string para mostrar

                }
                System.out.println("Text "+xpp.getText());
            } else if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("data")){}
                else if (xpp.getName().equals("meta")){}
                else if (xpp.getName().equals("instanceID")) {}
                else if (xpp.getName().equals("primary")) {tagName="primary_";}
                else {tagName=xpp.getName();}
                //System.out.println("Start tag "+xpp.getName());
                //} else if(eventType == XmlPullParser.END_TAG) {
                //System.out.println("End tag "+xpp.getName());
                //jsonSend = jsonSend + coma;
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End tag "+xpp.getName());
                createTable = false;

            }
            eventType = xpp.next();


        }
        System.out.println("End document");
        // End to build sql to create table in database
        sql = sql+")";
        //MainActivity.xml_res = sql;
        jsonSend =jsonSend + "}]}";
        main.jsonSend =jsonSend;
        //System.out.println("xml_res ->"+sql);
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
