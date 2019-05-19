package mx.caar.dise_o.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mx.caar.dise_o.Modelos.Usuario;

public class RegistroRequest extends StringRequest {

    private static final String REGISTRO_REQUEST_URL = "https://dise-o-api.herokuapp.com/usuarios/register.php";
    private Map<String,String> params;

    public RegistroRequest(Usuario user, String password, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTRO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nombre", user.getNombre());
        params.put("apellido_paterno", user.getApellido_pat());
        params.put("apellido_materno", user.getApellido_mat());
        params.put("email", user.getEmail());
        params.put("nickname", user.getNick());
        params.put("password", password);

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
