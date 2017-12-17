package dam.sanpri.jesus.creandobasededatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseAdapter database;
    EditText nombre, nuevoNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseAdapter(this);
        database.crearUsuario("Desesperacion");

        nombre = (EditText) findViewById(R.id.nombre);
        nuevoNombre = (EditText)findViewById(R.id.nuevo);
    }

    public void anadir(View view) {
        String strNombre = nombre.getText().toString();
        database.crearUsuario(strNombre);
    }

    public void eliminar(View view) {
        String strNombre = nombre.getText().toString();
        database.borrarUsuario(strNombre);
    }

    public void mostrar(View view) {
        List<String> nombres = database.getNames();

        for (int i = 0; i < nombres.size(); i++) {
            String name = nombres.get(i);
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view) {
        String nombreOld = nombre.getText().toString();
        String nombreNew = nuevoNombre.getText().toString();

        database.renameName(nombreOld, nombreNew);

    }
}
