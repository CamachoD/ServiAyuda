package com.serviayuda.pc.serviayuda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PC on 28/02/2018.
 */

public class BienvenidaAdministrador extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);
        Intent i = new Intent(BienvenidaAdministrador.this, MenuViewPagerAdmin.class);
        startActivity(i);
    }
}
