package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private static String archivoXML = "https://admonpersonal.000webhostapp.com/catalogo1.xml";
    ArrayList<Gasto> gastos = new ArrayList<>();
    private Button descargaXML;
    private Button visualizaXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descargaXML = (Button) findViewById(R.id.btnInserta);
        visualizaXML = (Button) findViewById(R.id.btnVisualiza);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(getApplicationContext(), "Conexion ok", Toast.LENGTH_SHORT).show();
            descargaXML.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gastos = leerGastos();
                    Toast.makeText(getApplicationContext(), "Descargados", Toast.LENGTH_SHORT).show();
                }
            });
            visualizaXML.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), VisualizaProducto.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ListaDeActPrincipal", gastos);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Conexion error", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Gasto> leerGastos(){
        HttpURLConnection conn = null;
        ArrayList<Gasto> gastos = null;
        try {
            URL url = new URL(archivoXML);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.connect();
            int respondeCode = conn.getResponseCode();
            if (respondeCode == HttpsURLConnection.HTTP_OK){
                Document doc = parseXML(conn.getInputStream());
                gastos = parseGastos(doc.getDocumentElement());

            }
            else if (respondeCode == HttpsURLConnection.HTTP_CONFLICT){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null){
                conn.disconnect();
            }
        }
        return gastos;
    }

    private ArrayList<Gasto> parseGastos(Element raiz) {
        ArrayList<Gasto> gastos = new ArrayList<Gasto>();
        NodeList items = raiz.getElementsByTagName("gasto");
        for (int i = 0 ; i < items.getLength(); i++){
            Node nodeGasto = items.item(i);
            Gasto gasto = new Gasto();
            for (int j=0; j< nodeGasto.getChildNodes().getLength(); j++) {
                Node nodoActual = nodeGasto.getChildNodes().item(j);
                if (nodoActual.getNodeType() == Node.ELEMENT_NODE) {
                    if (nodoActual.getNodeName().equalsIgnoreCase("folio")) {
                        gasto.setFolio(nodoActual.getChildNodes().item(0).getNodeValue());
                    }else if(nodoActual.getNodeName().equalsIgnoreCase("concepto")){
                        gasto.setConcepto(nodoActual.getChildNodes().item(0).getNodeValue());
                    }else if(nodoActual.getNodeName().equalsIgnoreCase("costo")){
                        gasto.setCosto(nodoActual.getChildNodes().item(0).getNodeValue());
                    }else if(nodoActual.getNodeName().equalsIgnoreCase("numeroPiezas")){
                        gasto.setNumeroPiezas(nodoActual.getChildNodes().item(0).getNodeValue());
                    }
                }
            }
            gastos.add(gasto);
        }
        return gastos;
    }

    private Document parseXML(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(stream);
        return document;
    }
}