package com.example.ilm.pra2moviles.util;

import android.graphics.Bitmap;
import android.location.Location;

import com.example.ilm.pra2moviles.drawer.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public final class ShareData {
    public static final int REQUEST_CAMERA = 0;
    public static Bitmap imagenProductoNuevo = null;
    public static Location lastlocation = null;
    public static List<Producto> productoList = new ArrayList<>();
}
