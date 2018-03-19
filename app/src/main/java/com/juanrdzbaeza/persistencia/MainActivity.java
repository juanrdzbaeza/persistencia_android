package com.juanrdzbaeza.persistencia;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);


    }

    public void grabar(View v) {
        String nameFile     = et1.getText().toString();
        nameFile            = nameFile.replace('/','-');

        try {
            OutputStreamWriter archivo = new OutputStreamWriter(
                    openFileOutput(nameFile, Activity.MODE_PRIVATE)
            );
            archivo.write(et2.getText().toString());
            archivo.flush();
            archivo.close();

        } catch (IOException e) {
            Toast.makeText(this, "Algo falló", Toast.LENGTH_SHORT).show();
        }


        Toast.makeText(this, "Los datos fueron grabados", Toast.LENGTH_SHORT).show();
        et1.setText("");
        et2.setText("");
    }

    public void recuperar(View v) {
        String nameFile         = et1.getText().toString();
        nameFile                = nameFile.replace('/','-');
        boolean encontrado      = false;
        String[] archivos = fileList(); // fileList() devuelve todos los ficheros que almacena la app

        for (String f : archivos) {
            if (nameFile.equals(f)) encontrado = true;
        }
        if (encontrado == true) {
            try {
                InputStreamReader archivo = new InputStreamReader(
                        openFileInput(nameFile)
                );
                BufferedReader br   = new BufferedReader(archivo);
                String linea        = br.readLine();
                String todo         = "";
                while (linea != null){
                    todo    = todo + linea + "\n";
                    linea   = br.readLine();
                }
                br.close();
                archivo.close();
                et2.setText(todo);
            } catch (IOException e) {
                Toast.makeText(this, "Algo falló", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No existen datos para esa fecha", Toast.LENGTH_SHORT).show();
            et2.setText("");
        }
    }
}
