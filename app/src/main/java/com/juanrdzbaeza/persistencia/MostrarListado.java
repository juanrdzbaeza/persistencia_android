package com.juanrdzbaeza.persistencia;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarListado extends AppCompatActivity {

    private ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_listado);
        /*
        consulta a la base de datos
         */
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,"administracion", null, 1
        );
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = null;
        try {
            fila = db.query(
                    "articulos",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        } catch (SQLException sql) {
            Toast.makeText(this, "algo falló", Toast.LENGTH_SHORT).show();
        }
        /*
        addChild() construira los elementos en la interfaz
         */
        String[] rowChild = new String[3];
        if (fila.moveToFirst()) {
            do {
                rowChild[0] = fila.getString(0);
                rowChild[1] = fila.getString(1);
                rowChild[2] = fila.getString(2);
                addChild(rowChild);
            }while (fila.moveToNext());
        } else {
            Toast.makeText(this, "la cagaste", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Recibe un Array de String con los datos de los articulos, que va repartiendo sobre
     * los textviews, cada vez que es llamado escrive una linea en la pantalla del movil.
     * @param rowChild
     */
    public void addChild(String[] rowChild) {
        /*
        volcando los datos en el layout
         */
        layout = findViewById(R.id.contenido);
        LayoutInflater inflador = LayoutInflater.from(this);
        int id = R.layout.layout_articulo;
        View view = inflador.inflate(
                id, null, false
        );
        TextView codigo         = view.findViewById(R.id.codigo);
        TextView descripcion    = view.findViewById(R.id.descripcion);
        TextView precio         = view.findViewById(R.id.precio);
        codigo.setText(rowChild[0]);
        descripcion.setText(rowChild[1]);
        precio.setText(rowChild[2]);
        layout.addView(view);
    }

    public void salir(View v) {
        finish();
    }
}
