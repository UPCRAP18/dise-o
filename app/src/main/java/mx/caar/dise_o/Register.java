package mx.caar.dise_o;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mx.caar.dise_o.DBLocal.DBOpen_Helper;
import mx.caar.dise_o.Modelos.Usuario;
import mx.caar.dise_o.Requests.LoginRequest;
import mx.caar.dise_o.Requests.RegistroRequest;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Button btnRegistrar = (Button) findViewById(R.id.btnRegistrar_Usuario);

        final EditText txteNombre = (EditText) findViewById(R.id.txteNombre_Registro);
        final EditText txteNick = (EditText) findViewById(R.id.txteNick_Registro);
        final EditText txteAPat = (EditText) findViewById(R.id.txteAPPat_Registro);
        final EditText txteAMat = (EditText) findViewById(R.id.txteAPMat_Registro);
        final EditText txteEmail = (EditText) findViewById(R.id.txteEmail_Registro);
        final EditText txtePassword = (EditText) findViewById(R.id.txtePwd_Registro);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txteNombre.getText().toString().isEmpty() ||
                        !txteNick.getText().toString().isEmpty() ||
                        !txteAPat.getText().toString().isEmpty() ||
                        !txteAMat.getText().toString().isEmpty() ||
                        !txteEmail.getText().toString().isEmpty() ||
                        !txtePassword.getText().toString().isEmpty()){

                    final String nombre = txteNombre.getText().toString();
                    final String nick = txteNick.getText().toString();
                    final String appat = txteAPat.getText().toString();
                    final String apmat = txteAMat.getText().toString();
                    final String correo = txteEmail.getText().toString();
                    final String password = txtePassword.getText().toString();

                    Usuario user = new Usuario("",nombre, appat, apmat, nick, correo);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try{
                                JSONObject data = new JSONObject(response);

                                boolean success = data.getBoolean("success");

                                if(success){
                                    AlertDialog.Builder error_msg = new AlertDialog.Builder(Register.this);
                                    error_msg.setMessage("Se ha creado el usuario")
                                            .setPositiveButton("Aceptar", null)
                                            .create().show();

                                }else {
                                    System.out.println(data.toString());
                                    AlertDialog.Builder error_msg = new AlertDialog.Builder(Register.this);
                                    error_msg.setMessage("Ha ocurrido un error al crear el usuario")
                                            .setPositiveButton("Reintentar", null)
                                            .create().show();
                                }

                            }catch (JSONException ex){
                                ex.printStackTrace();
                            }


                        }
                    };

                    RegistroRequest registerRequest = new RegistroRequest(user, password, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(Register.this);

                    queue.add(registerRequest);


                }else {
                    Toast.makeText(Register.this, "No puede dejar los campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
