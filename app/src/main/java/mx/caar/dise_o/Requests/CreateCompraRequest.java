package mx.caar.dise_o.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateCompraRequest extends StringRequest {

    private static final String CREATE_REQUEST_URL = "https://dise-o-api.herokuapp.com/compras/create.php";
    private Map<String,String> params;

    public CreateCompraRequest(String id_usuario, String total, String items, Response.Listener<String> listener){
        super(Request.Method.POST, CREATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id_usuario", id_usuario );
        params.put("total", total);
        params.put("items", items);

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
