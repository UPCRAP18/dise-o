package mx.caar.dise_o.Modelos;

import java.util.stream.StreamSupport;

public class Producto {

    String id, imagen,nombre,precio,categoria,subcategoria,stock, cantidad;

    public Producto(String id, String nombre, String imagen, String precio, String cantidad){
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(String id, String imagen, String nombre, String precio, String categoria, String subcategoria, String stock) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.stock = stock;
    }

    public String getCantidad(){
        return cantidad;
    }

    public void setCantidad(String cantidad){
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
