package mx.caar.dise_o;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mx.caar.dise_o.Modelos.Producto;
import mx.caar.dise_o.Requests.CrearProductoRequest;

public class NuevoProducto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        EditText etNombre = (EditText) findViewById(R.id.etNombre_Prod);
        EditText etImagen = (EditText) findViewById(R.id.etImagen_Prod);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecio_Prod);
        EditText etSubCat = (EditText) findViewById(R.id.etSubcat_Prod);
        EditText etStock = (EditText) findViewById(R.id.etStock_Prod);
        Spinner spCategoria = (Spinner) findViewById(R.id.spCategoria_Prod);
        Button sendRequest = (Button) findViewById(R.id.btnRegistrar_Producto);

        String[] categorias = getResources().getStringArray(R.array.categorias);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject data = new JSONObject(response);

                    boolean success = data.getBoolean("success");

                    if(success){
                        AlertDialog.Builder error_msg = new AlertDialog.Builder(NuevoProducto.this);
                        error_msg.setMessage("Se ha creado correctamente el producto")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NuevoProducto.this.recreate();
                                    }
                                })
                                .create().show();
                    }else {
                        AlertDialog.Builder error_msg = new AlertDialog.Builder(NuevoProducto.this);
                        error_msg.setMessage("Ha ocurrido un error al crear el producto")
                                .setPositiveButton("Reintentar", null)
                                .create().show();
                    }

                }catch (JSONException ex){
                    ex.printStackTrace();
                }


            }
        };

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNombre.getText().toString().isEmpty() || !etPrecio.getText().toString().isEmpty() || !etStock.getText().toString().isEmpty() ||
                    !etSubCat.getText().toString().isEmpty()){
                    String imagen = "";
                    if(!etImagen.getText().toString().isEmpty()){
                        imagen = etImagen.getText().toString();
                    }
                    String nombre = etNombre.getText().toString();
                    String precio = etPrecio.getText().toString();
                    String subcat = etSubCat.getText().toString();
                    String categoria = categorias[spCategoria.getSelectedItemPosition()];
                    String stock = etStock.getText().toString();


                    Producto producto = new Producto("", imagen, nombre, precio, categoria, subcat, stock);

                    CrearProductoRequest prodRequest = new CrearProductoRequest(producto, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(NuevoProducto.this);

                    queue.add(prodRequest);
                }else {
                    Toast.makeText(NuevoProducto.this, "No puede dejar estos campos vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
