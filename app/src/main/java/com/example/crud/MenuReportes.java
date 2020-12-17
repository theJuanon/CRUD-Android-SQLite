package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuReportes extends AppCompatActivity implements View.OnClickListener {

    Button btnReporte1, btnReporte2, btnReporte3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reportes);

        btnReporte1 = findViewById(R.id.btnReporte1);
        btnReporte2 = findViewById(R.id.btnReporte2);
        btnReporte3 = findViewById(R.id.btnReporte3);

    }

    @Override
    public void onClick(View evt) {
        if (evt == btnReporte1){
            startActivity(new Intent(this, Reporte1.class));
            return;
        }
        if (evt == btnReporte2){
            startActivity(new Intent(this, Reporte2.class));
            return;
        }
        if (evt == btnReporte3){
            startActivity(new Intent(this, Reporte3.class));
        }
    }
}