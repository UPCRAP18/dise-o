package mx.caar.dise_o.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HistorialRequest extends StringRequest {

    private static final String HISTORIAL_REQUEST_URL = "https://dise-o-api.herokuapp.com/compras/user_compras.php";
    private Map<String,String> params;

    public HistorialRequest(String id, Response.Listener<String> listener){
        super(Method.POST, HISTORIAL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
