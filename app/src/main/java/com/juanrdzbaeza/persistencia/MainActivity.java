package com.juanrdzbaeza.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * Realizar un programa que genere un número aleatorio entre 1 y 50, pedir que el operador
     * lo adivine, informar si ganó o si el número es mayor o menor al ingresado. Cuando el
     * operador lo adivine incrementar en uno el puntaje de juego. Cada vez que se ingrese a
     * la aplicación mostrar el puntaje actual, es decir recordar el puntaje en un archivo de
     * preferencias.
     */
    private TextView tv1,tv2;
    private EditText et1;
    private int secretNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        et1 = findViewById(R.id.et1);
        secretNumber = (int) (Math.random()*51);

        SharedPreferences preferencias=getSharedPreferences(
                "puntuaje", Context.MODE_PRIVATE
        );
        String oldScore = preferencias.getString("puntuaje","0");
        tv1.setText(oldScore);
    }

    public void verificar(View v){
        int num = Integer.parseInt(et1.getText().toString());
        //Log.e("0",String.valueOf(secretNumber));
        //Log.e("1","boton pulsado");
        if (num > secretNumber){
            //Log.e("2","se ha pasado");
            Toast.makeText(this,"se ha pasado",Toast.LENGTH_LONG).show();
        } else if (num < secretNumber){
            //Log.e("3","se ha quedado corto");
            Toast.makeText(this,"se ha quedado corto",Toast.LENGTH_LONG).show();
        } else {
            //Log.e("4","correcto");
            Toast.makeText(this,"correcto",Toast.LENGTH_LONG).show();
            SharedPreferences preferencias=getSharedPreferences(
                    "puntuaje", Context.MODE_PRIVATE
            );
            String oldScore = preferencias.getString("puntuaje","0");
            int newScore = Integer.parseInt(oldScore);
            newScore++;
            String i = String.valueOf(newScore);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("puntuaje", i);
            editor.commit();
            tv1.setText("Puntuaje: "+i);
            secretNumber = (int) (Math.random()*51);
        }
        et1.setText("");
    }

    public void reiniciarPuntuacion(View v){
        SharedPreferences preferencias=getSharedPreferences(
                "puntuaje", Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("puntuaje", "0");
        editor.commit();
        tv1.setText("Puntuaje: 0");
    }

}
