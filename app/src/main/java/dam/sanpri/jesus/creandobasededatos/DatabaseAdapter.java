package dam.sanpri.jesus.creandobasededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jes on 17/11/2017.
 */

public class DatabaseAdapter {
    //es static porque solo quiero tener un databaseHelper para los dos en caso de tener
    //varias instancias de la clase
    private static DatabaseHelper helper = null;
    private final Context context;

    public DatabaseAdapter(Context context){
        if(helper == null){
            helper = new DatabaseHelper(context);
        }
        this.context = context;
    }

    public long crearUsuario(String nombre){
        SQLiteDatabase db = helper.getWritableDatabase();

        //Valores a insertar se meten en un content values
        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.Name, nombre);

        return db.insert(DatabaseHelper.TABLE_NAMES, null, content);
    }

    public List<String> getNames(){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columnas = {DatabaseHelper.Name};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAMES, columnas, null, null, null, null, null);

        List<String> resultados = new ArrayList<>();
        while(cursor.moveToNext()){
            resultados.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Name)));
        }
        return resultados;
    }

    public int borrarUsuario(String nomb){
        SQLiteDatabase db = helper.getWritableDatabase();
        //Segundo parametro es where
        return db.delete(DatabaseHelper.TABLE_NAMES, DatabaseHelper.Name+" = '"+nomb+"'", null);
    }

    public int renameName (String old, String nuevo){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.Name, nuevo);

        return db.update(DatabaseHelper.TABLE_NAMES, content, DatabaseHelper.Name+" = "+old, null);
    }

    private class DatabaseHelper extends SQLiteOpenHelper{
        private static final String db_name = "mydatabase";
        private static final int version = 1;

        //Nombre tablas y columnas
        private static final String TABLE_NAMES = "NAMES";
        private static final String UID = "_id";
        private static final String Name = "Name";


        public DatabaseHelper(Context context) {
            super(context, db_name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // crea la tabla de nombre NAMES con columna id y columna name
            db.execSQL("CREATE TABLE " +TABLE_NAMES + "(" +UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Name +" VARCHAR(255));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
