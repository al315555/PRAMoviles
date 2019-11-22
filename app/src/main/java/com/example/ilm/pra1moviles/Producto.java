package com.example.ilm.pra1moviles;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Producto{

    private String nombre;
    private String precio;
    private String descripcion;
    private Bitmap imagen;

    /**
     * No args constructor for use in serialization
     */

    public Producto(){super();}


    /**
     *
     * @param nombre
     * @param precio
     * @param descripcion
     * @param imagen
     */

    public Producto(String nombre, String precio, String descripcion, Bitmap imagen){
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
