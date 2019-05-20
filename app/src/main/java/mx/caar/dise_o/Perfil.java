package mx.caar.dise_o;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.caar.dise_o.DBLocal.DBOpen_Helper;
import mx.caar.dise_o.Modelos.Usuario;
import mx.caar.dise_o.Requests.ActualizarUsuarioRequest;
import mx.caar.dise_o.Requests.HistorialRequest;

public class Perfil extends AppCompatActivity {

    Usuario usuario;
    ArrayList<String> compras = new ArrayList<>();
    EditText etNombre, etApellidoPat, etApellidoMat, etNickname, etCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        DBOpen_Helper helper = new DBOpen_Helper(this);

        usuario = helper.getData_Usuario();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNombre = (EditText) findViewById(R.id.etNombre_Perfil);
        etApellidoPat = (EditText) findViewById(R.id.etAPPat_Perfil);
        etApellidoMat = (EditText) findViewById(R.id.etAPMat_Perfil);
        etNickname = (EditText) findViewById(R.id.etNickName_Perfil);
        etCorreo = (EditText) findViewById(R.id.etCorreo_Perfil);
        ListView lstCompras = (ListView) findViewById(R.id.lstCompras);
        etNombre.setText(usuario.getNombre());
        etApellidoPat.setText(usuario.getApellido_pat());
        etApellidoMat.setText(usuario.getApellido_mat());
        etCorreo.setText(usuario.getEmail());
        etNickname.setText(usuario.getNick());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject data = new JSONObject(response);

                    boolean success = data.getBoolean("success");

                    if(success){

                        JSONArray dataArray = data.getJSONArray("compras");

                        for (int i = 0; i < data.getInt("count"); i++){
                            JSONObject compra = dataArray.getJSONObject(i);
                            compras.add(String.format("Cantidad de productos: %s - Total: %s - Fecha: %s", compra.get("cantidad_productos"), compra.get("total_pagado"), compra.get("fecha_compra")));
                        }

                        if(!compras.isEmpty()){
                            ArrayAdapter<String> adaptador = new ArrayAdapter<>(Perfil.this, android.R.layout.simple_list_item_1, compras);
                            lstCompras.setAdapter(adaptador);
                        }


                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                }


            }
        };

        HistorialRequest historialRequest = new HistorialRequest(helper.getData_Usuario().getId(), responseListener);

        RequestQueue queue = Volley.newRequestQueue(Perfil.this);

        queue.add(historialRequest);



    }

    public void nuevo_producto(View view){
        Intent nuevo = new Intent(Perfil.this, NuevoProducto.class);
        startActivity(nuevo);
    }

    public void actualizar(View view){
        DBOpen_Helper helper = new DBOpen_Helper(Perfil.this);
        String nombre = etNombre.getText().toString();
        String apellido_pat = etApellidoPat.getText().toString();
        String apellido_mat = etApellidoMat.getText().toString();
        String correo = etCorreo.getText().toString();
        String nick = etNickname.getText().toString();
        String id = helper.getData_Usuario().getId();

        Usuario user = new Usuario(id, nombre, apellido_pat,apellido_mat, nick, correo);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject data = new JSONObject(response);

                    boolean success = data.getBoolean("success");

                    if(success){
                        AlertDialog.Builder message = new AlertDialog.Builder(Perfil.this);

                        message.setMessage("Se han actualizado los datos");
                        message.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.setDataUser(user);
                                Perfil.this.recreate();
                            }
                        });
                        message.create().show();

                    }else {
                        AlertDialog.Builder message = new AlertDialog.Builder(Perfil.this);

                        message.setMessage("Ha ocurrido un error en la request");
                        message.setPositiveButton("Aceptar", null);
                        message.create().show();

                        System.out.println(data.get("message"));
                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                }


            }
        };


        ActualizarUsuarioRequest usuarioRequest = new ActualizarUsuarioRequest(user, responseListener);

        RequestQueue queue = Volley.newRequestQueue(Perfil.this);

        queue.add(usuarioRequest);

    }

    public void cerrar_sesion(View view){
        DBOpen_Helper helper = new DBOpen_Helper(this);
        if(helper.dropUsr()){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setMessage("Se han eliminado los datos");
            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent login = new Intent(Perfil.this, Login.class);
                    startActivity(login);
                }
            });


            dialogo.create().show();

        }
    }

    public void informacion_app(View view){
        LayoutInflater inflater = getLayoutInflater();
        Toast informacion = new Toast(this);
        View info = inflater.inflate(R.layout.informacion_aplicacion, null);

        informacion.setView(info);
        informacion.setDuration(Toast.LENGTH_LONG);

        informacion.show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inicio = new Intent(this, Inicio.class);
        startActivity(inicio);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}
