package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConsultaAutos extends AppCompatActivity {

    BaseDeDatos Conexion;
    SQLiteDatabase BD;
    ListView lista1;

    private ArrayList<Auto> myAutos = new ArrayList<Auto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_autos);
        lista1 = findViewById(R.id.listViewAutos);

        String cadena = "";
        Conexion = new BaseDeDatos(this, "Agencia", null, BaseDeDatos.VERSION);
        if (Conexion == null) {
            AlertDialog Alerta = new AlertDialog.Builder(this).create();
            Alerta.setMessage("LA conexion NO se ha hecho");
            Alerta.show();
            return;
        }
        BD = Conexion.getWritableDatabase();
        if (BD == null) {
            AlertDialog Alerta = new AlertDialog.Builder(this).create();
            Alerta.setMessage("LA BD NO ESTÁ PREPARADA PARA LECTURA Y ESCRITURA");
            Alerta.show();
            return;
        }
        cadena = "SELECT * FROM AUTOS ORDER BY YEAR";
        Cursor c = BD.rawQuery(cadena, null);
        if (c.getCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Recuperando ");
            alertDialog.setMessage("NO HAY AUTOS REGISTRADOS");
            alertDialog.show();
            return;
        }
        // RECUPERO LOS AUTO CON QUE ESTÉN ASIGNADOS AL CURSOR.
        String placa, marca, modelo;
        int year, cveCliente;

        while (c.moveToNext()) {
            placa = c.getString(0);
            marca = c.getString(1);
            modelo = c.getString(2);
            year = c.getInt(3);
            cveCliente = c.getInt(4);
            myAutos.add(new Auto(placa,marca,modelo,year, cveCliente));
        }
        ArrayAdapter<Auto> adapter = new MyListAdapter();
        lista1.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Auto> {
        public MyListAdapter(){
            super(ConsultaAutos.this, R.layout.item_view_autos, myAutos);
        }
        public View getView(int Position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView == null)
                itemView=getLayoutInflater().inflate(R.layout.item_view_autos,parent,false);

            Auto CurrentAuto=myAutos.get(Position);


            TextView lblPlaca=(TextView) itemView.findViewById(R.id.lblPlaca);
            lblPlaca.setText(CurrentAuto.getPlaca());

            TextView lblModelo=(TextView) itemView.findViewById(R.id.lblModelo);
            lblModelo.setText(CurrentAuto.getModelo());

            TextView lblMarca=(TextView) itemView.findViewById(R.id.lblMarca);
            lblMarca.setText(CurrentAuto.getMarca());

            TextView lblYear=(TextView) itemView.findViewById(R.id.lblYear);
            lblYear.setText(CurrentAuto.getYear()+"");

            TextView lblCveCliente = itemView.findViewById(R.id.lblCveCliente);
            lblCveCliente.setText(CurrentAuto.getCveCliente()+"");

            return itemView;

        }
    }

}