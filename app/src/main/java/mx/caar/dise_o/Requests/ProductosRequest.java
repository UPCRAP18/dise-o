package mx.caar.dise_o.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProductosRequest extends StringRequest {

    static final String PRODUCTS_URL = "https://dise-o-api.herokuapp.com/productos/productos.php";
    private Map<String,String> params;

    public ProductosRequest(String categoria, Response.Listener<String> listener){
        super(Method.POST, PRODUCTS_URL, listener, null);
        params = new HashMap<>();
        params.put("categoria", categoria);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
