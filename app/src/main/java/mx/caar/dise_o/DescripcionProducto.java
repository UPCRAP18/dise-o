package mx.caar.dise_o;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import mx.caar.dise_o.DBLocal.DBOpen_Helper;
import mx.caar.dise_o.Modelos.Producto;

public class DescripcionProducto extends AppCompatActivity {

    String id="", nombre="", imagen="", categoria="", subcat="", precio="";
    int cantidad_producto = 0;
    TextView txtCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_producto);

        TextView txtNombre = (TextView) findViewById(R.id.txtNombre_Desc);
        TextView txtPrecio = (TextView) findViewById(R.id.txtPrecio_Desc);
        TextView txtCategoria = (TextView) findViewById(R.id.txtCat_Desc);
        TextView txtSubCat = (TextView) findViewById(R.id.txtSubCat_Desc);
        txtCantidad = (TextView) findViewById(R.id.txtCantProducto);
        ImageView imgProducto = (ImageView) findViewById(R.id.imgProducto_Desc);
        ImageButton imgbAdd = (ImageButton)  findViewById(R.id.imgbAdd);
        ImageButton imgbRest = (ImageButton)  findViewById(R.id.imgbMenos);

        if(getIntent().getExtras() != null){
            ArrayList<String> data = getIntent().getStringArrayListExtra("DATOS");
            id = data.get(0);
            nombre = data.get(1);
            imagen = data.get(2);
            categoria = data.get(3);
            subcat = data.get(4);
            precio = data.get(5);
        }


        txtNombre.setText(nombre);
        txtCategoria.setText(categoria);
        txtSubCat.setText(subcat);
        txtPrecio.setText(String.format("$%s", precio));
        if(!imagen.isEmpty()){
            Glide.with(this).load(imagen).into(imgProducto);
        }else {
            imgProducto.setImageDrawable(getResources().getDrawable(R.drawable.product_holder));
        }
    }

    public void addUno(View view){
        cantidad_producto++;
        txtCantidad.setText(String.format(Locale.getDefault(),"%d",cantidad_producto));
    }

    public void quitarUno(View view){
        if(cantidad_producto>0){
            cantidad_producto--;
            txtCantidad.setText(String.format(Locale.getDefault(),"%d",cantidad_producto));
        }else {
            Toast.makeText(this, "No puede quitar mas productos", Toast.LENGTH_SHORT).show();
            cantidad_producto=0;
        }
    }


    public void addCarrito(View view){
        DBOpen_Helper helper = new DBOpen_Helper(this);

        if(cantidad_producto!= 0){
            Producto prod_add = new Producto(id, nombre, imagen, precio, String.valueOf(cantidad_producto));

            if(helper.setDataCarrito(prod_add)){
                Toast.makeText(this, "Se ha añadido correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "No puede agregar 0 items", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent back = new Intent(DescripcionProducto.this, SubCategorias.class);
            back.putExtra("CATEGORIA", categoria);
            startActivity(back);
            this.finish();
        }
        return true;

    }
}
