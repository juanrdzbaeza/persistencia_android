package com.juanrdzbaeza.persistencia;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);

    }

    /**
     * Cuando se presiona el botón "ALTA" se ejecuta el método "alta" recordemos inicializar la
     * propiedad "onClick" del botón desde la ventana de visualización del archivo XML.
     *
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
        // conexion con la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,"administracion", null, 1
        );
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo           = et1.getText().toString();
        String descripcion      = et2.getText().toString();
        String precio           = et3.getText().toString();

        //Toast.makeText(this, "prueba de impresion"+codigo+" "+descripcion+" "+precio+" ", Toast.LENGTH_SHORT).show();

        ContentValues registro  = new ContentValues();
        registro.put("codigo",codigo);
        registro.put("descripcion",descripcion);
        registro.put("precio",precio);

        if(codigo.isEmpty() ){
            Toast.makeText(this, "El codigo no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if(descripcion.isEmpty()){
            Toast.makeText(this, "la descripcion no puede estar vacía", Toast.LENGTH_SHORT).show();
            return;
        }
        if(precio.isEmpty()){
            Toast.makeText(this, "El precio no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            db.insert("articulos",null, registro);
            Toast.makeText(this, "Se guardaron los datos del articulo", Toast.LENGTH_SHORT).show();
        } catch (SQLException sql) {
            Toast.makeText(this, "No introdujo ningun dato", Toast.LENGTH_SHORT).show();
        }
        db.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");

    }

    /**
     * Cuando se presiona el botón "CONSULTA POR CODIGO" se ejecuta el método consultaporcodigo:
     *
     * En el método consultaporcodigo lo primero que hacemos es crear un objeto de la clase
     * AdminSQLiteOpenHelper y obtener una referencia de la base de datos llamando al método
     * getWritableDatabase.
     *
     * Seguidamente definimos una variable de la clase Cursor y la inicializamos con el valor
     * devuelto por el método llamado rawQuery.
     *
     * La clase Cursos almacena en este caso una fila o cero filas (una en caso que hayamos
     * ingresado un codigo existente en la tabla articulos), llamamos al método moveToFirst() de la
     * clase Cursos y retorna true en caso de existir un articulo con el codigo ingresado, en caso
     * contrario retorna cero.
     *
     * Para recuperar los datos propiamente dichos que queremos consultar llamamos al método
     * getString y le pasamos la posición del campo a recuperar (comienza a numerarse en cero,
     * en este ejemplo la columna cero representa el campo descripcion y la columna 1 representa
     * el campo precio)
     *
     * @param v
     */
    public void consultaPorCodigo(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,"administracion", null, 1
        );
        SQLiteDatabase db = admin.getWritableDatabase();
        String cod      = et1.getText().toString();

        try {
            Cursor fila     = db.rawQuery(
                    "select descripcion,precio " +
                            "from articulos " +
                            "where codigo="+cod,null
            );
            // si fila = true significa que la consulta ha encontrado una coincidencia y la ha devuelto
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0));
                et3.setText(fila.getString(1));
            } else {
                Toast.makeText(this, "No existe articulo con ese código", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException sql) {
            Toast.makeText(this, "No introdujo ningun dato", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    /**
     * En el método consultapordescripcion lo primero que hacemos es crear un objeto de la clase
     * AdminSQLiteOpenHelper y obtener una referencia de la base de datos llamando al método
     * getWritableDatabase.
     *
     * Seguidamente definimos una variable de la clase Cursor y la inicializamos con el valor
     * devuelto por el método llamado rawQuery.
     *
     * Es importante notar en el where de la clausula SQL hemos dispuesto comillas simples entre
     * el contenido de la variable descri:
     * @param v
     */
    public void consultaPorDescripcion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "administracion", null, 1
        );
        SQLiteDatabase db = admin.getWritableDatabase();
        String descri       = et2.getText().toString();

        try {
            Cursor fila         = db.rawQuery(
                    "select codigo,precio " +
                            "from articulos " +
                            "where descripcion='"+descri+"'",null
            );
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0));
                et3.setText(fila.getString(1));
            } else {
                Toast.makeText(this, "No existe articulo con esa descripción", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException sql) {
            Toast.makeText(this, "No introdujo ningun dato", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    /**
     * Para borrar uno o más registros la clase SQLiteDatabase tiene un método que le pasamos en el
     * primer parámetro el nombre de la tabla y en el segundo la condición que debe cumplirse para
     * que se borre la fila de la tabla. El método delete retorna un entero que indica la cantidad
     * de registros borrados:
     * @param v
     */
    public void bajaPorCodigo(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,"administracion",null,1
        );
        SQLiteDatabase db   = admin.getWritableDatabase();
        String code         = et1.getText().toString();

        if(code.isEmpty()){
            Toast.makeText(this, "El codigo no puede ser vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int cantidadBorrada = db.delete(
                    "articulos","codigo="+code,null
            );
            if (cantidadBorrada == 1) {
                Toast.makeText(this, "Se borró el articulo con codigo: "+code, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe el articulo con codigo: "+code, Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException sql) {
            Toast.makeText(this, "No introdujo ningun dato", Toast.LENGTH_SHORT).show();
        }
        db.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
    }

    /**
     * En la modificación de datos debemos crear un objeto de la clase ContentValues y mediante el
     * método put almacenar los valores para cada campo que será modificado. Luego se llama al
     * método update de la clase SQLiteDatabase pasando el nombre de la tabla, el objeto de la clase
     * ContentValues y la condición del where (el cuanto parámetro en este ejemplo no se lo emplea)
     * @param v
     */
    public void update(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,"administracion", null, 1
        );
        SQLiteDatabase db       = admin.getWritableDatabase();

        String codigo           = et1.getText().toString();
        String descripcion      = et2.getText().toString();
        String precio           = et3.getText().toString();

        if(codigo.isEmpty() ){
            Toast.makeText(this, "El codigo no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor fila = null;
        try {
             fila     = db.rawQuery(
                    "select descripcion,precio " +
                            "from articulos " +
                            "where codigo="+codigo,null
            );
        } catch (SQLException sql) {
            Toast.makeText(this, "No introdujo ningun dato", Toast.LENGTH_SHORT).show();
        }
        if(fila.moveToFirst()){
            if(descripcion.isEmpty()){
                descripcion = fila.getString(0);
            }
            if(precio.isEmpty()){
                precio = fila.getString(1);
            }
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            try {
                db.update(
                        "articulos", registro, "codigo="+codigo,null
                );
            } catch (SQLException sql) {
                Toast.makeText(this, "algo falló", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Se modificó el articulo con código: " + codigo, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el articulo con código: "+codigo, Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    /**
     * Llamamos a la actividad que se encargara de mostrar el listado de todos los artículos
     * @param v
     */
    public void verListadoArticulos(View v) {
        Intent i = new Intent(this, MostrarListado.class);
        startActivity(i);
    }
}
