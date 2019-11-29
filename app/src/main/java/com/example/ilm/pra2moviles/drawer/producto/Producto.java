package com.example.ilm.pra2moviles.drawer.producto;

import android.graphics.Bitmap;

public class Producto{

    private String nombre;
    private String precio;
    private String descripcion;
    private Bitmap imagen;
    private double latitud;
    private double longitud;

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

    public Producto(String nombre, String precio, String descripcion, Bitmap imagen, double jlat, double jlon){
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.latitud = jlat;
        this.longitud = jlon;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString(){
        return nombre;
    }

    public void setCoordenadas(String coordenadas) {
        //Lat:%d Lon:%d
        String[] splitted = coordenadas.split(" ");
        double lat = Double.parseDouble(splitted[0].substring(splitted[0].lastIndexOf(":") + 1 ).replace(',', '.').trim());
        double lon = Double.parseDouble(splitted[1].substring(splitted[1].lastIndexOf(":") + 1 ).replace(',', '.').trim());
        this.latitud = lat;
        this.longitud = lon;
    }
}
