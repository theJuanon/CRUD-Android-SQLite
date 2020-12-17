package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrudAutos extends AppCompatActivity implements View.OnClickListener{

    BaseDeDatos Conexion;
    SQLiteDatabase BD;

    Button btnRecuperar, btnGrabar, btnBorrar, btnActualizar, btnConsultar, btnLimpiar;
    EditText txtPlaca, txtMarca, txtModelo, txtYear, txtCveCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_autos);
        Conexion = new BaseDeDatos(this, "Agencia", null, BaseDeDatos.VERSION);

        if(Conexion==null){
            AlertDialog  Alerta= new AlertDialog.Builder(this).create();
            Alerta.setMessage("LA conexion NO se ha hecho");
            Alerta.show();
            return;
        }
        BD = Conexion.getWritableDatabase();
        if(BD==null){
            AlertDialog  Alerta= new AlertDialog.Builder(this).create();
            Alerta.setMessage("LA BD NO ESTÁ PREPARADA PARA LECTURA Y ESCRITURA");
            Alerta.show();
            return;
        }

        btnRecuperar = findViewById(R.id.btnRecuperar);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        txtPlaca = findViewById(R.id.txtPlaca);
        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtYear = findViewById(R.id.txtYear);
        txtCveCliente = findViewById(R.id.txtCveCte);
        hazEscuchas();

    }

    public void hazEscuchas(){
        btnRecuperar.setOnClickListener(this);
        btnGrabar.setOnClickListener(this);
        btnBorrar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);
        btnConsultar.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);

    }

    @Override
    public void onClick(View evt) {
        if (evt == btnRecuperar){
            recuperar();
            return;
        }
        if (evt == btnGrabar){
            grabar();
            return;
        }
        if (evt == btnBorrar){
            borrar();
            return;
        }
        if (evt == btnActualizar){
            actualizar();
            return;
        }
        if (evt == btnConsultar){
            startActivity(new Intent(this, ConsultaAutos.class));
            return;
        }
        if (evt == btnLimpiar){
            limpiar();
        }
    }

    public void recuperar(){
        String placa=txtPlaca.getText().toString();
        if(placa.length() == 0){
            Toast msg = Toast.makeText(getBaseContext(), "debe de capturar número de placa", Toast.LENGTH_LONG);msg.show();
            txtPlaca.requestFocus();
            return;
        }
        String cadena="SELECT MARCA, MODELO, YEAR, CVECLIENTE FROM AUTOS WHERE PLACA='"+placa+"'";
        Cursor c = BD.rawQuery(cadena, null);
        if (c.getCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Recuperando ");
            alertDialog.setMessage("LA PLACA PROPORCIONADA NO ESTA REGISTRADA");
            alertDialog.show();
            return;
        }
        // RECUPERO EL AUTO CON LA PLACA PROPORCIONADO
        c.moveToFirst(); // POSICIONA EN LA TUPLA
        txtMarca.setText(c.getString(0)); // asignó el modelo a la caja de texto modelo
        txtModelo.setText(c.getString(1));
        txtYear.setText(c.getInt(2)+"");
        txtCveCliente.setText(c.getInt(3)+"");
    }

    public void grabar(){
        String placa = txtPlaca.getText().toString();
        String marca = txtMarca.getText().toString();
        String modelo = txtModelo.getText().toString();
        int year = Integer.parseInt(txtYear.getText().toString());
        int cveCliente = Integer.parseInt(txtCveCliente.getText().toString());

        if(placa.length() == 0 || marca.length() == 0 || modelo.length() == 0 || year<1980 || cveCliente <= 999){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ CAPTURAR ALGÚN DATO",Toast.LENGTH_LONG);msg.show();
            txtPlaca.requestFocus();
            return;
        }
        String cadena="INSERT INTO AUTOS (PLACA,MARCA,MODELO,YEAR,CVECLIENTE) VALUES('" +placa + "','" + marca + "','"+modelo+"',"+year+","+cveCliente+")";
        try {
            BD.execSQL(cadena);
        } catch (SQLiteConstraintException E){
            AlertDialog Alerta = new AlertDialog.Builder(this).create();
            Alerta.setMessage("se presentó una violación de integridad, se intentó grabar mas de una tupla con la misma placa");
            Alerta.show();
            return;
        }
        limpiar();
    }

    public void borrar(){
        String placa = txtPlaca.getText().toString();
        if(placa.length() == 0){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ proporcionar placa",Toast.LENGTH_LONG);msg.show();
            txtPlaca.requestFocus();
            return;
        }
        String cadena="DELETE FROM AUTOS WHERE PLACA='" + placa + "'";
        BD.execSQL(cadena);
        limpiar();
    }

    public void actualizar(){
        String placa = txtPlaca.getText().toString();
        String marca = txtMarca.getText().toString();
        String modelo = txtModelo.getText().toString();
        int year = Integer.parseInt(txtYear.getText().toString());
        int cveCliente = Integer.parseInt(txtCveCliente.getText().toString());
        if(placa.length()==0 || marca.length()==0 || modelo.length()==0 || year<1980 || cveCliente < 999){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ CAPTURAR ALGÚN DATO",Toast.LENGTH_LONG);msg.show();
            txtPlaca.requestFocus();
            return;
        }
        String cadena="UPDATE AUTOS SET MARCA='"+ marca + "', MODELO='"+ modelo +"', YEAR="+ year +", CVECLIENTE="+cveCliente+" WHERE PLACA='"+ placa+"'";
        BD.execSQL(cadena);
        limpiar();
    }

    public void limpiar(){
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtYear.setText("");
        txtCveCliente.setText("");
        txtPlaca.requestFocus();
    }

}