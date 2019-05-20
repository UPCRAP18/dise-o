package mx.caar.dise_o;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mx.caar.dise_o.DBLocal.DBOpen_Helper;
import mx.caar.dise_o.Modelos.Usuario;
import mx.caar.dise_o.Requests.LoginRequest;

public class Login extends AppCompatActivity {

    DBOpen_Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnRegistrar = (Button) findViewById(R.id.btnRegistrar_Producto);

        final EditText txteCorreo = (EditText) findViewById(R.id.txteCorreo);
        final EditText txtePwd = (EditText) findViewById(R.id.txtePwd);

        helper = new DBOpen_Helper(Login.this);

        if(helper.getData_Usuario().getId() != null){
            Intent send_inicio = new Intent(Login.this, Inicio.class);
            startActivity(send_inicio);
            Login.this.finish();
        }


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send_registro = new Intent(Login.this, Register.class);
                startActivity(send_registro);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txteCorreo.getText().toString().isEmpty() || !txtePwd.getText().toString().isEmpty()){
                    final String correo = txteCorreo.getText().toString();
                    final String password = txtePwd.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try{
                                JSONObject data = new JSONObject(response);

                                boolean success = data.getBoolean("success");

                                if(success){
                                    Usuario usuario = new Usuario();

                                    usuario.setEmail(data.getString("email"));
                                    usuario.setId(String.valueOf(data.getInt("id")));
                                    usuario.setNombre(data.getString("nombre"));
                                    usuario.setApellido_pat(data.getString("apellido_paterno"));
                                    usuario.setApellido_mat(data.getString("apellido_materno"));
                                    usuario.setNick(data.getString("nickname"));

                                    helper = new DBOpen_Helper(Login.this);

                                    helper.setDataUser(usuario);

                                    Intent send_inicio = new Intent(Login.this, Inicio.class);
                                    startActivity(send_inicio);
                                    Login.this.finish();

                                }else {
                                    AlertDialog.Builder error_msg = new AlertDialog.Builder(Login.this);
                                    error_msg.setMessage("Ha ocurrido un error en el inicio de sesion")
                                            .setPositiveButton("Reintentar", null)
                                            .create().show();
                                }

                            }catch (JSONException ex){
                                ex.printStackTrace();
                            }


                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(correo, password, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(Login.this);

                    queue.add(loginRequest);


                }else {
                    Toast.makeText(Login.this, "No puede dejar los campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
