package mx.caar.dise_o.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mx.caar.dise_o.Modelos.Usuario;

public class ActualizarUsuarioRequest extends StringRequest {

    private static final String UPDATE_REQUEST_URL = "https://dise-o-api.herokuapp.com/usuarios/update.php";
    private Map<String,String> params;

    public ActualizarUsuarioRequest(Usuario user, Response.Listener<String> listener){
        super(Request.Method.POST, UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", user.getId());
        params.put("nombre", user.getNombre());
        params.put("apellido_paterno", user.getApellido_pat());
        params.put("apellido_materno", user.getApellido_mat());
        params.put("email", user.getEmail());
        params.put("nickname", user.getNick());

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
