package mx.caar.dise_o.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mx.caar.dise_o.Modelos.Producto;

public class CrearProductoRequest extends StringRequest {

    private static final String PRODUCTOS_REQUEST_URL = "https://dise-o-api.herokuapp.com/productos/create.php";
    private Map<String,String> params;

    public CrearProductoRequest(Producto producto, Response.Listener<String> listener){
        super(Request.Method.POST, PRODUCTOS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("imagen", producto.getImagen());
        params.put("nombre", producto.getNombre());
        params.put("precio", producto.getPrecio());
        params.put("categoria", producto.getCategoria());
        params.put("subcategoria", producto.getSubcategoria());
        params.put("stock", producto.getStock());

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
