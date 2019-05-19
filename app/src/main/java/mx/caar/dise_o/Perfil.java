package mx.caar.dise_o;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import mx.caar.dise_o.DBLocal.DBOpen_Helper;
import mx.caar.dise_o.Modelos.Usuario;

public class Perfil extends AppCompatActivity {

    Usuario usuario;
    ArrayList<String> compras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        DBOpen_Helper helper = new DBOpen_Helper(this);

        usuario = helper.getData_Usuario();

        EditText etNombre = (EditText) findViewById(R.id.etNombre_Perfil);
        EditText etApellidoPat = (EditText) findViewById(R.id.etAPPat_Perfil);
        EditText etApellidoMat = (EditText) findViewById(R.id.etAPMat_Perfil);
        EditText etNickname = (EditText) findViewById(R.id.etNickName_Perfil);
        EditText etCorreo = (EditText) findViewById(R.id.etCorreo_Perfil);

        Button btnNuevoProducto = (Button) findViewById(R.id.btnAddProducto);
        Button btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);

        ImageButton imgbInfo = (ImageButton) findViewById(R.id.imgbInfo);


    }


    public void informacion_app(View view){

    }


}
