package mx.caar.dise_o;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.caar.dise_o.DBLocal.DBOpen_Helper;
import mx.caar.dise_o.Requests.CreateCompraRequest;
import mx.caar.dise_o.Requests.HistorialRequest;

public class Carrito extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView lstCarrito;
    Button btnComprar;
    RadioGroup rbgTarjetas;
    Spinner spPago;
    LinearLayout layTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        lstCarrito = (ListView) findViewById(R.id.lstCarrito);
        btnComprar = (Button) findViewById(R.id.btnComprar);
        rbgTarjetas = (RadioGroup) findViewById(R.id.rbTipoTarjeta);
        spPago = (Spinner) findViewById(R.id.spPago);
        layTarjeta = (LinearLayout) findViewById(R.id.layTarjeta);

        spPago.setOnItemSelectedListener(this);

        DBOpen_Helper helper = new DBOpen_Helper(this);
        if(!helper.getData_Carrito().isEmpty()){
            Adaptador_Carrito adaptadorCarrito = new Adaptador_Carrito(helper.getData_Carrito(), this);
            lstCarrito.setAdapter(adaptadorCarrito);
        }else {
            AlertDialog.Builder message = new AlertDialog.Builder(this);
            message.setMessage("El carrito está vacio");
            message.setPositiveButton("Aceptar", null);
            message.create().show();
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject data = new JSONObject(response);

                    boolean success = data.getBoolean("success");

                    if(success){
                        AlertDialog.Builder message = new AlertDialog.Builder(Carrito.this);
                        message.setMessage("La compra se ha realizado con exito");
                        message.setPositiveButton("Aceptar", null);
                        message.create().show();
                    }else {
                        AlertDialog.Builder message = new AlertDialog.Builder(Carrito.this);
                        message.setMessage("Se ha producido un error");
                        message.setPositiveButton("Aceptar", null);
                        message.create().show();
                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                }


            }
        };




        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateCompraRequest compraRequest = new CreateCompraRequest("","","", responseListener);

                RequestQueue queue = Volley.newRequestQueue(Carrito.this);

                queue.add(compraRequest);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            layTarjeta.setVisibility(View.GONE);
        }else {
            layTarjeta.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
