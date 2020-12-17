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

public class CrudClientes extends AppCompatActivity implements View.OnClickListener{

    BaseDeDatos Conexion;
    SQLiteDatabase BD;

    Button btnRecuperar, btnGrabar, btnBorrar, btnActualizar, btnConsultar, btnLimpiar;
    EditText txtCve, txtNombre, txtCiudad, txtEstado, txtColonia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_clientes);

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

        btnRecuperar = findViewById(R.id.btnCteRecuperar);
        btnGrabar = findViewById(R.id.btnCteGrabar);
        btnBorrar = findViewById(R.id.btnCteBorrar);
        btnActualizar = findViewById(R.id.btnCteActualizar);
        btnConsultar = findViewById(R.id.btnCteConsultar);
        btnLimpiar = findViewById(R.id.btnCteLimpiar);

        txtCve = findViewById(R.id.txtCve);
        txtNombre = findViewById(R.id.txtNombre);
        txtCiudad = findViewById(R.id.txtCiudad);
        txtEstado = findViewById(R.id.txtEstado);
        txtColonia = findViewById(R.id.txtColonia);
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
            startActivity(new Intent(this, ConsultaClientes.class));
            return;
        }
        if (evt == btnLimpiar){
            limpiar();
        }
    }

    public void recuperar(){
        int cve = Integer.parseInt(txtCve.getText().toString());
        if(cve <= 999){
            Toast msg = Toast.makeText(getBaseContext(), "debe de capturar clave de cliente", Toast.LENGTH_LONG);msg.show();
            txtCve.requestFocus();
            return;
        }
        String cadena="SELECT NOMBRE, COLONIA, CIUDAD, ESTADO FROM CLIENTES WHERE CVE="+cve+"";
        Cursor c = BD.rawQuery(cadena, null);
        if (c.getCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Recuperando ");
            alertDialog.setMessage("LA CLAVE PROPORCIONADA NO ESTA REGISTRADA");
            alertDialog.show();
            return;
        }
        // RECUPERO EL AUTO CON LA PLACA PROPORCIONADO
        c.moveToFirst(); // POSICIONA EN LA TUPLA
        txtNombre.setText(c.getString(0)); // asignó el modelo a la caja de texto modelo
        txtColonia.setText(c.getString(1));
        txtCiudad.setText(c.getString(2));
        txtEstado.setText(c.getString(3));
    }

    public void grabar(){
        int cve = Integer.parseInt(txtCve.getText().toString());
        String nombre = txtNombre.getText().toString();
        String colonia = txtColonia.getText().toString();
        String ciudad = txtCiudad.getText().toString();
        String estado = txtEstado.getText().toString();

        if(cve <= 999 || nombre.length() == 0 || colonia.length() == 0 || ciudad.length() == 0 || estado.length() == 0){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ CAPTURAR ALGÚN DATO",Toast.LENGTH_LONG);msg.show();
            txtCve.requestFocus();
            return;
        }
        String cadena="INSERT INTO CLIENTES (CVE,NOMBRE,COLONIA,CIUDAD,ESTADO) VALUES(" +cve + ",'" + nombre + "','"+colonia+"','"+ciudad+"','"+estado+"')";
        try {
            BD.execSQL(cadena);
        } catch (SQLiteConstraintException E){
            AlertDialog Alerta = new AlertDialog.Builder(this).create();
            Alerta.setMessage("se presentó una violación de integridad, se intentó grabar mas de una tupla con la misma clave");
            Alerta.show();
            return;
        }
        limpiar();
    }

    public void borrar(){
        int cve = Integer.parseInt(txtCve.getText().toString());
        if(cve <= 999){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ proporcionar clave",Toast.LENGTH_LONG);msg.show();
            txtCve.requestFocus();
            return;
        }
        String cadena="DELETE FROM CLIENTES WHERE CVE=" + cve + "";
        BD.execSQL(cadena);
        limpiar();
    }

    public void actualizar(){
        int cve = Integer.parseInt(txtCve.getText().toString());
        String nombre = txtNombre.getText().toString();
        String colonia = txtColonia.getText().toString();
        String ciudad = txtCiudad.getText().toString();
        String estado = txtEstado.getText().toString();
        if(cve <= 999 || nombre.length() == 0 || colonia.length() == 0 || ciudad.length() == 0 || estado.length() == 0){
            Toast msg = Toast.makeText(getBaseContext(), "FALTÓ CAPTURAR ALGÚN DATO",Toast.LENGTH_LONG);msg.show();
            txtCve.requestFocus();
            return;
        }
        String cadena="UPDATE CLIENTES SET NOMBRE='"+ nombre + "', COLONIA='"+ colonia +"', CIUDAD='"+ ciudad +"', ESTADO='"+estado+"' WHERE CVE="+ cve+"";
        BD.execSQL(cadena);
        limpiar();
    }

    public void limpiar(){
        txtCve.setText("");
        txtNombre.setText("");
        txtCiudad.setText("");
        txtEstado.setText("");
        txtColonia.setText("");
        txtCve.requestFocus();
    }

}