package mx.caar.dise_o;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.caar.dise_o.Modelos.Producto;
import mx.caar.dise_o.Requests.ProductosRequest;

public class SubCategorias extends AppCompatActivity implements Adaptador_Productos.Listener {

    String CATEGORIA = "";
    ArrayList<Producto> productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);

        if(getIntent().getExtras() != null){
            CATEGORIA = getIntent().getStringExtra("CATEGORIA");
            getSupportActionBar().setTitle(CATEGORIA);
        }

        ListView lstProductos = (ListView) findViewById(R.id.lstProductos);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject data = new JSONObject(response);

                    boolean success = data.getBoolean("success");

                    if(success){

                        JSONArray dataArray = data.getJSONArray("productos");

                        for (int i = 0; i < data.getInt("count"); i++){
                            JSONObject producto = dataArray.getJSONObject(i);
                            Producto prod = new Producto(String.valueOf(producto.getString("id")),
                                    producto.getString("imagen"),
                                    producto.getString("nombre"),
                                    producto.getString("precio"),
                                    producto.getString("categoria"),
                                    producto.getString("subcategoria"),
                                    producto.getString("stock"));

                            productos.add(prod);

                        }

                        if(!productos.isEmpty()){
                            Adaptador_Productos adaptador = new Adaptador_Productos(productos, SubCategorias.this, SubCategorias.this);
                            lstProductos.setAdapter(adaptador);
                        }


                    }else {
                        AlertDialog.Builder error_msg = new AlertDialog.Builder(SubCategorias.this);
                        error_msg.setMessage("No se ha encontrado ningun producto en esta categoria")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SubCategorias.this.onBackPressed();
                                    }
                                })
                                .create().show();
                    }

                }catch (JSONException ex){
                    ex.printStackTrace();
                }


            }
        };

        ProductosRequest productRequest = new ProductosRequest(CATEGORIA, responseListener);

        RequestQueue queue = Volley.newRequestQueue(SubCategorias.this);

        queue.add(productRequest);


    }

    @Override
    public void OnItemSelected(Producto producto_seleccionado) {

        Intent mostrar_informacion = new Intent(SubCategorias.this, DescripcionProducto.class);
        ArrayList<String> informacion = new ArrayList<>();
        informacion.add(producto_seleccionado.getId());
        informacion.add(producto_seleccionado.getNombre());
        informacion.add(producto_seleccionado.getImagen());
        informacion.add(producto_seleccionado.getCategoria());
        informacion.add(producto_seleccionado.getSubcategoria());
        informacion.add(producto_seleccionado.getPrecio());

        mostrar_informacion.putExtra("DATOS", informacion);

        startActivity(mostrar_informacion);

    }
}
