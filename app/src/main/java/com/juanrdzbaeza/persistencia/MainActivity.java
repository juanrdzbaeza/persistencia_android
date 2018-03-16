package com.juanrdzbaeza.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        /*
        Obtenemos una referencia de un objeto de la clase SharedPreferences a través del método
        getSharedPreferences. El primer parámetro es el nombre del archivo de preferencias y el
        segundo la forma de creación del archivo (MODE_PRIVATE indica que solo esta aplicación
        puede consultar el archivo XML que se crea)
         */
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        /*
        Para extraer los datos del archivo de preferencias debemos indicar el nombre a extraer
        y un valor de retorno si dicho nombre no existe en el archivo de preferencias (en nuestro
         ejemplo la primera vez que se ejecute nuestro programa como es lógico no existe el
         archivo de preferencias lo que hace que Android lo cree, si tratamos de extraer el valor
          de mail retornará el segundo parámetro es decir el String con una cadena vacía:
         */
        et1.setText(preferences.getString("mail",""));
    }

    public void ejecutar(View v){
        /*
        Cuando se presiona el botón "confirmar" lo que hacemos es grabar en el archivo de
        preferencias el contenido del EditText en una variable llamada "mail":
         */
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mail",et1.getText().toString());
        editor.commit();
        finish();

        /*
        Debemos crear un objeto de la clase Editor y obtener la referencia del objeto de la clase
        SharedPreferences que acabamos de crear. Mediante el método putString almacenamos en mail
        el valor del String cargado en el EditText. Luego debemos llamar al método commit de la
        clase Editor para que el dato quede almacenado en forma permanente en el archivo de
        preferencias. Esto hace que cuando volvamos a arrancar la aplicación se recupere el último
        mail ingresado.
         */

    }
}






































