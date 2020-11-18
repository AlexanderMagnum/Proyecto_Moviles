package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class VisualizaProducto extends AppCompatActivity {

    private ListView listaGasto;
    private ArrayList<Gasto> arrayList = new ArrayList<Gasto>();
    private String[] milista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_producto);
        iniCompInterfaz();
        recibeDatos();
        try {
            imprimeObjeto();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        regresaMain();
    }

    public void iniCompInterfaz(){
        listaGasto = (ListView) findViewById(R.id.listaGasto);
    }

    public void recibeDatos(){
        if (getIntent().getExtras() != null){
            this.arrayList = (ArrayList<Gasto>) getIntent().getSerializableExtra("ListaDeActPrincipal");
        }
        this.milista = new String[this.arrayList.size()];
        for (int i=0; i<this.arrayList.size(); i++){
            milista[i] = (this.arrayList.get(i)).toString();
        }
    }

    private void imprimeObjeto() throws JSONException{
        this.arrayList = (ArrayList<Gasto>) getIntent().getSerializableExtra("ListaDeActPrincipal");
        if (arrayList!=null){
            ListView listViewGasto = (ListView) findViewById(R.id.listaGasto);
            ListAdapter adapGasto = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, milista);
            listViewGasto.setAdapter(adapGasto);
        }else {
            Toast.makeText(this,"Error en los datos", Toast.LENGTH_SHORT).show();
        }

    }


    private void regresaMain(){
        Button boton = (Button) findViewById(R.id.regresar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}