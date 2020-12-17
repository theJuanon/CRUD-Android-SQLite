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

public class ConsultaClientes extends AppCompatActivity {

    BaseDeDatos Conexion;
    SQLiteDatabase BD;
    ListView lista1;
    private ArrayList<Cliente> myClientes = new ArrayList<Cliente>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_clientes);

        lista1 = findViewById(R.id.listViewClientes);

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
        cadena = "SELECT * FROM CLIENTES ORDER BY COLONIA";
        Cursor c = BD.rawQuery(cadena, null);
        if (c.getCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Recuperando ");
            alertDialog.setMessage("NO HAY CLIENTES REGISTRADOS");
            alertDialog.show();
            return;
        }

        int cve;
        String nombre, colonia, ciudad, estado;

        while (c.moveToNext()) {
            cve = c.getInt(0);
            nombre = c.getString(1);
            colonia = c.getString(2);
            ciudad = c.getString(3);
            estado = c.getString(4);
            myClientes.add(new Cliente(cve,nombre,colonia,ciudad, estado));
        }
        ArrayAdapter<Cliente> adapter = new MyListAdapter();
        lista1.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Cliente> {
        public MyListAdapter(){
            super(ConsultaClientes.this, R.layout.item_view_clientes, myClientes);
        }
        public View getView(int Position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView == null)
                itemView=getLayoutInflater().inflate(R.layout.item_view_clientes,parent,false);

            Cliente CurrentCliente = myClientes.get(Position);



            TextView lblNombre=(TextView) itemView.findViewById(R.id.lblNombre);
            lblNombre.setText(CurrentCliente.getNombre());

            TextView lblColonia=(TextView) itemView.findViewById(R.id.lblColonia);
            lblColonia.setText(CurrentCliente.getColonia());

            TextView lblCiudad=(TextView) itemView.findViewById(R.id.lblCiudad);
            lblCiudad.setText(CurrentCliente.getCiudad());

            TextView lblEstado = itemView.findViewById(R.id.lblEstado);
            lblEstado.setText(CurrentCliente.getEstado());

            TextView lblCve = (TextView) itemView.findViewById(R.id.lblCve);
            lblCve.setText(CurrentCliente.getCve()+"");

            return itemView;

        }
    }
}