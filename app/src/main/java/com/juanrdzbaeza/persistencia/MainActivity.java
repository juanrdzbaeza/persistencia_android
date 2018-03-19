package com.juanrdzbaeza.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2, et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et1);
        et3 = findViewById(R.id.et1);

    }

    /**
     * Lo primero que hacemos en este método es crear un objeto de la clase que planteamos
     * anteriormente y le pasamos al constructor this (referencia del Activity actual),
     * "administracion" (es el nombre de la base de datos que crearemos en el caso que no exista)
     * luego pasamos null y un uno indicando que es la primer versión de la base de datos
     * (en caso que cambiemos la estructura o agreguemos tablas por ejemplo podemos pasar un dos
     * en lugar de un uno para que se ejecute el método onUpgrade donde indicamos la nuestra
     * estructura de la base de datos).
     *
     * Luego de crear un objeto de la clase AdminSqLiteOpenHelper procedemos a crear un objeto
     * de la clase SQLiteDataBase llamando al método getWritableDatabase (la base de datos se
     * abre en modo lectura y escritura).
     *
     * Creamos un objeto de la clase ContentValues y mediante el método put inicializamos todos
     * tos campos a cargar.
     *
     * Seguidamente llamamos al método insert de la clase SQLiteDatabase pasando en el primer
     * parámetro el nombre de la tabla, como segundo parámetro un null y por último el objeto
     * de la clase ContentValues ya inicializado (este método es el que provoca que se inserte
     * una nueva fila en la tabla articulos en la base de datos llamada administracion).
     *
     * Borramos seguidamente los EditText y mostramos un mensaje para que conozca el operador
     * que el alta de datos se efectuó en forma correcta:
     * @param v
     */
    public void alta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,"administracion", null, 1
        );
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo           = et1.getText().toString();
        String descripcion      = et2.getText().toString();
        String precio           = et3.getText().toString();
        ContentValues registro  = new ContentValues();
        registro.put("codigo",codigo);
        registro.put("descripcion",descripcion);
        registro.put("precio",precio);
        bd.insert("articulos",null, registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        Toast.makeText(this, "Se guardaron los datos del articulo", Toast.LENGTH_SHORT).show();
    }





}














































