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

public class CrudServicios extends AppCompatActivity implements View.OnClickListener {

    BaseDeDatos Conexion;
    SQLiteDatabase BD;

    Button btnRecuperar, btnGrabar, btnBorrar, btnActualizar;
    EditText txtNoOrden, txtIDAuto, txtKMServicio, txtImporte, txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_servicios);

        Conexion = new BaseDeDatos(this, "Agencia", null, BaseDeDatos.VERSION);

        if(Conexion == null){
            AlertDialog Alerta= new AlertDialog.Builder(this).create();
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

        btnRecuperar = findViewById(R.id.btnServRecuperar);
        btnGrabar = findViewById(R.id.btnServGrabar);
        btnBorrar = findViewById(R.id.btnServBorrar);
        btnActualizar = findViewById(R.id.btnServActualizar);

        txtNoOrden = findViewById(R.id.txtNoOrden);
        txtIDAuto = findViewById(R.id.txtIDAuto);
        txtKMServicio = findViewById(R.id.txtKMServicio);
        txtImporte = findViewById(R.id.txtImporte);
        txtFecha = findViewById(R.id.txtFecha);
        hazEscuchas();

    }

    public void hazEscuchas(){
        btnRecuperar.setOnClickListener(this);
        btnGrabar.setOnClickListener(this);
        btnBorrar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);

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
        }
    }

    public void recuperar(){
        int noOrden = Integer.parseInt(txtNoOrden.getText().toString());
        if(noOrden <= 0){
            Toast msg = Toast.makeText(getBaseContext(), "debe de capturar número de orden", Toast.LENGTH_LONG);msg.show();
            txtNoOrden.requestFocus();
            return;
        }
        String cadena="SELECT PLACA, KILOMETRAJE, IMPORTE, FECHA FROM SERVICIOS WHERE NOORDEN="+noOrden+"";
        Cursor c = BD.rawQuery(cadena, null);
        if (c.getCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Recuperando ");
            alertDialog.setMessage("LA ORDEN PROPORCIONADA NO ESTA REGISTRADA");
            alertDialog.show();
            return;
        }
        c.moveToFirst(); // POSICIONA EN LA TUPLA
        txtIDAuto.setText(c.getString(0)); // asignó el modelo a la caja de texto modelo
        txtKMServicio.setText(c.getInt(1)+"");
        txtImporte.setText(c.getDouble(2)+"");
        txtFecha.setText(c.getString(3));
    }

    public void grabar(){
        int noOrden = Integer.parseInt(txtNoOrden.getText().toString());
        String idAuto= txtIDAuto.getText().toString();
        int kmServicio = Integer.parseInt(txtKMServicio.getText().toString());
        double importe = Double.parseDouble(txtImporte.getText().toString());
        String fecha = txtFecha.getText().toString();

        if(noOrden <= 0 || idAuto.length() == 0 || kmServicio <= 0 || importe < 0 || fecha.length() == 0){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ CAPTURAR ALGÚN DATO",Toast.LENGTH_LONG);msg.show();
            txtNoOrden.requestFocus();
            return;
        }

        String cadena="INSERT INTO SERVICIOS (NOORDEN,PLACA,KILOMETRAJE,IMPORTE,FECHA) VALUES(" +noOrden + ",'" + idAuto + "',"+kmServicio+","+importe+",'"+fecha+"')";
        try {
            BD.execSQL(cadena);
        } catch (SQLiteConstraintException E){
            AlertDialog Alerta = new AlertDialog.Builder(this).create();
            Alerta.setMessage("se presentó una violación de integridad, se intentó grabar mas de una tupla con la misma orden");
            Alerta.show();
            return;
        }
        limpiar();
    }

    public void borrar(){
        int noOrden = Integer.parseInt(txtNoOrden.getText().toString());
        if(noOrden <= 0){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ proporcionar placa",Toast.LENGTH_LONG);msg.show();
            txtNoOrden.requestFocus();
            return;
        }
        String cadena="DELETE FROM SERVICIOS WHERE NOORDEN=" + noOrden + "";
        BD.execSQL(cadena);
        limpiar();
    }

    public void actualizar(){
        int noOrden = Integer.parseInt(txtNoOrden.getText().toString());
        String idAuto= txtIDAuto.getText().toString();
        int kmServicio = Integer.parseInt(txtKMServicio.getText().toString());
        double importe = Double.parseDouble(txtImporte.getText().toString());
        String fecha = txtFecha.getText().toString();

        if(noOrden <= 0 || idAuto.length() == 0 || kmServicio <= 0 || importe < 0 || fecha.length() == 0){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ CAPTURAR ALGÚN DATO",Toast.LENGTH_LONG);msg.show();
            txtNoOrden.requestFocus();
            return;
        }
        String cadena="UPDATE SERVICIOS SET PLACA='"+ idAuto + "', KILOMETRAJE="+ kmServicio +", IMPORTE="+ importe +", FECHA='"+fecha+"' WHERE NOORDEN="+ noOrden+"";
        BD.execSQL(cadena);
        limpiar();
    }

    public void limpiar(){
        txtNoOrden.setText("");
        txtIDAuto.setText("");
        txtKMServicio.setText("");
        txtImporte.setText("");
        txtFecha.setText("");
    }

}