package com.juanrdzbaeza.persistencia;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        String contentFile  = et2.getText().toString();

        try {
            //File tarjetaDir   = Environment.getExternalStorageDirectory();
            /*
                para escribir en la memoria externa hemos tenido que conocer la ruta de la misma
                en el sistema de ficheros, despues hemos creado la carpeta persistenciaFolder
                para que este sea el directorio de almacenamiento de los ficheros datos que
                almacena la aplicacion.
            */
            File folder         = new File("/storage/sdcard1/persistenciaFolder");

            Toast.makeText(this, folder.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            File directorioConFichero = new File(
                    folder.getAbsolutePath(), nameFile
            );
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(directorioConFichero)
            );
            osw.write(contentFile);
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueros grabados correctamente", Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");

        } catch (IOException e) {
            Toast.makeText(this, "Algo fallo", Toast.LENGTH_SHORT).show();
        }
    }


    public void recuperar(View v) {
        String nameFile         = et1.getText().toString();
        //File folder           = Environment.getExternalStorageDirectory();
        File folder             = new File("/storage/sdcard1/persistenciaFolder");
        File[] listOfFiles      = folder.listFiles();
        Boolean fileFound = false;
        for (File f : listOfFiles) {
            // Toast.makeText(this, folder.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, f.getName(), Toast.LENGTH_SHORT).show();
            if (nameFile.equals(f.getName())) fileFound = true;
        }
        if (fileFound) {
            File tarjetaDir         = Environment.getExternalStorageDirectory();
            File directorioWithFile = new File(
                    tarjetaDir.getAbsolutePath(), nameFile
            );
            try {
                FileInputStream fIn         = new FileInputStream(directorioWithFile);
                InputStreamReader archivo   = new InputStreamReader(fIn);
                BufferedReader br           = new BufferedReader(archivo);
                String linea                = br.readLine();
                String todo                 = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                et2.setText(todo);

            } catch (IOException e) {
                Toast.makeText(this, "algo fallo", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "el nombre de fichero no existe", Toast.LENGTH_SHORT).show();
            et2.setText("");
        }
    }
}
