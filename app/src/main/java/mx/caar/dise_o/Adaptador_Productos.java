package mx.caar.dise_o;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import mx.caar.dise_o.Modelos.Producto;

public class Adaptador_Productos implements ListAdapter {
    ArrayList<Producto> productos;
    Context contexto;
    Adaptador_Productos.Listener producto_listener;

    public Adaptador_Productos(ArrayList<Producto> productos, Context contexto, Adaptador_Productos.Listener listener) {
        this.productos = productos;
        this.contexto = contexto;
        producto_listener = listener;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Producto producto = productos.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(contexto);
            convertView = layoutInflater.inflate(R.layout.productos_layout, null);

            ImageView imgProducto = (ImageView) convertView.findViewById(R.id.imgProducto);
            TextView txtNombre = (TextView) convertView.findViewById(R.id.txtNombre_Prod);
            TextView txtSubcat = (TextView) convertView.findViewById(R.id.txtCant_Prod);
            TextView txtPrecio = (TextView) convertView.findViewById(R.id.txtPrecio_Prod);
            TextView txtDisponible = (TextView) convertView.findViewById(R.id.txtDisponible);

            if(producto.getImagen().isEmpty()){
                imgProducto.setImageDrawable(contexto.getResources().getDrawable(R.drawable.product_holder));
            }else {
                Glide.with(contexto).load(producto.getImagen()).apply(new RequestOptions().centerInside()).into(imgProducto);
            }

            txtNombre.setText(producto.getNombre());
            txtSubcat.setText(producto.getSubcategoria());
            txtPrecio.setText(String.format("$%s", producto.getPrecio()));
            if(Integer.parseInt(producto.getStock()) > 0 ){
                txtDisponible.setText("Disponible");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        producto_listener.OnItemSelected(producto);
                    }
                });
            }else {
                txtDisponible.setText("Sin inventario");
            }


        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return productos.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public interface Listener {
        void OnItemSelected(Producto producto_seleccionado);
    }
}
