package com.juanrdzbaeza.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

    }

    /**
     * Cuando se presiona el botón grabar:
     * Extraemos los dos datos de los EditText, creamos un objeto de la clas
     * SharedPReferences con el nombre de "agenda".
     * Creamos un objeto de la clase Editor y procedemos a grabar en el
     * archivo de preferencias mediante putString:
     * @param v
     */
    public void grabar(View v){
        String nombre                   = et1.getText().toString();
        String datos                    = et2.getText().toString();
        SharedPreferences preferences   = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(nombre, datos);
        editor.apply();
        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_LONG).show();
    }

    /**
     * Por otro lado tenemos la lógica para recuperar los datos de una persona de la agenda:
     * Abrimos el archivo de preferencias y llamamos al método getString
     * buscando el nombre ingresado en el et1. En el caso que lo encuentre
     * retorna el dato asociado a dicha clave.
     * @param v
     */
    public void recuperar(View v){
        String nombre = et1.getText().toString();
        SharedPreferences preferences   = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        String d = preferences.getString(nombre, "");
        if (d.length() == 0){
            Toast.makeText(this,"No existe dicho nombre en la agenda",Toast.LENGTH_LONG).show();
        }else{
            et2.setText(d);
        }
    }

}






































