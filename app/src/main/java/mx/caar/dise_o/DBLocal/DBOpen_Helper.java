package mx.caar.dise_o.DBLocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mx.caar.dise_o.Modelos.Producto;
import mx.caar.dise_o.Modelos.Usuario;

public class DBOpen_Helper extends SQLiteOpenHelper {

    static final String DB_NAME = "dise_o";
    static final String COLUMN_ID = "id_usuario";
    static final String COLUMN_NOMBRE = "nombre_usuario";
    static final String COLUMN_EMAIL = "email_usuario";
    static final String COLUMN_APPAT = "apellido_pat_usuario";
    static final String COLUMN_APMAT = "apellido_mat_usuario";
    static final String COLUMN_NICKNAME = "nickname_usuario";
    static final String TABLE_PREFS = "preferencias";


    static final String COLUMN_ID_PRODUCTO = "id_producto";
    static final String COLUMN_NOMBRE_PRODUCTO = "nombre_producto";
    static final String COLUMN_IMAGEN_PRODUCTO = "imagen_producto";
    static final String COLUMN_PRECIO_PRODUCTO = "precio_producto";
    static final String COLUMN_CANTIDAD_PRODUCTO = "cantidad_producto";
    static final String TABLE_CARRITO = "carrito";
    SQLiteDatabase db;

    public DBOpen_Helper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Usr_Prefs = "CREATE TABLE IF NOT EXISTS " + TABLE_PREFS +
                " ( " + COLUMN_ID + " TEXT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_APPAT + " TEXT, " +
                COLUMN_APMAT + " TEXT, " +
                COLUMN_NICKNAME + ")";

        String Carrito = "CREATE TABLE IF NOT EXISTS " + TABLE_CARRITO +
                " ( " + COLUMN_ID_PRODUCTO + " TEXT, " +
                COLUMN_NOMBRE_PRODUCTO + " TEXT, " +
                COLUMN_IMAGEN_PRODUCTO + " TEXT, " +
                COLUMN_PRECIO_PRODUCTO + " TEXT, " +
                COLUMN_CANTIDAD_PRODUCTO + " TEXT )";

        db.execSQL(Carrito);
        db.execSQL(Usr_Prefs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_Usr = "DROP TABLE IF EXISTS " + TABLE_PREFS;
        db.execSQL(drop_Usr);
        String drop_Carrito = "DROP TABLE IF EXISTS " + TABLE_CARRITO;
        db.execSQL(drop_Carrito);

        onCreate(db);
    }

    private Cursor getData(String TABLE_NAME) {
        db = this.getReadableDatabase();
        Cursor res;
        res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public Usuario getData_Usuario() {

        Cursor res = getData(TABLE_PREFS);

        Usuario usuario = new Usuario();

        res.moveToFirst();

        while (!res.isAfterLast()) {
            usuario.setId(res.getString(0));
            usuario.setNombre(res.getString(1));
            usuario.setEmail(res.getString(2));
            usuario.setApellido_pat(res.getString(3));
            usuario.setApellido_mat(res.getString(4));
            usuario.setNick(res.getString(5));

            res.moveToNext();
        }

        db.close();

        return usuario;

    }

    public boolean setDataUser(Usuario user) {
        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_NOMBRE, user.getNombre());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_APPAT, user.getApellido_pat());
        values.put(COLUMN_APMAT, user.getApellido_mat());
        values.put(COLUMN_NICKNAME, user.getNick());

        if (db.insert(TABLE_PREFS, null, values) != 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }

    }

    public boolean dropUsr() {
        db = getWritableDatabase();
        if (db.delete(TABLE_PREFS, null, null) != 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }

    }


    public ArrayList<Producto> getData_Carrito() {

        Cursor res = getData(TABLE_CARRITO);

        ArrayList<Producto> productos = new ArrayList<>();

        res.moveToFirst();

        while (!res.isAfterLast()) {
            Producto producto_actual = new Producto(res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4));

            productos.add(producto_actual);

            res.moveToNext();
        }

        db.close();

        return productos;

    }

    public boolean setDataCarrito(Producto producto) {
        ArrayList<Producto> productos = getData_Carrito();
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PRODUCTO, producto.getId());
        values.put(COLUMN_NOMBRE_PRODUCTO, producto.getNombre());
        values.put(COLUMN_IMAGEN_PRODUCTO, producto.getImagen());
        values.put(COLUMN_PRECIO_PRODUCTO, producto.getPrecio());


        if(!productos.isEmpty()){
            for (Producto prod : productos){
                if(prod.getId().equals(producto.getId())){
                    int cantPrevia = Integer.parseInt(prod.getCantidad());
                    int cant_add = Integer.parseInt(producto.getCantidad());
                    cantPrevia += cant_add;

                    values.put(COLUMN_CANTIDAD_PRODUCTO, cantPrevia);
                    break;
                }
            }
        }else {
            values.put(COLUMN_CANTIDAD_PRODUCTO, producto.getCantidad());
        }

        if (db.insert(TABLE_CARRITO, null, values) != 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }

    }

    public boolean dropCarrito() {
        db = getWritableDatabase();
        if (db.delete(TABLE_CARRITO, null, null) != 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }

    }


}
