package com.example.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {
    static int VERSION = 4;
    String sqlCreateAutos = "CREATE TABLE AUTOS (PLACA TEXT PRIMARY KEY, MARCA TEXT,MODELO TEXT, YEAR INTEGER, CVECLIENTE INTEGER)";
    String sqlCreateClientes = "CREATE TABLE CLIENTES (CVE INTEGER PRIMARY KEY, NOMBRE TEXT, COLONIA TEXT, CIUDAD TEXT, ESTADO TEXT) ";
    String sqlCreateServicios = "CREATE TABLE SERVICIOS (NOORDEN INTEGER PRIMARY KEY, PLACA TEXT, KILOMETRAJE INTEGER, IMPORTE REAL, FECHA TEXT)";


    public BaseDeDatos(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateAutos);
        db.execSQL(sqlCreateClientes);
        db.execSQL(sqlCreateServicios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AUTOS");
        db.execSQL(sqlCreateAutos);
        db.execSQL("DROP TABLE IF EXISTS CLIENTES");
        db.execSQL(sqlCreateClientes);
        db.execSQL("DROP TABLE IF EXISTS SERVICIOS");
        db.execSQL(sqlCreateServicios);
    }
}
