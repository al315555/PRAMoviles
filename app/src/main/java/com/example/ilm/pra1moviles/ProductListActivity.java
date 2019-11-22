package com.example.ilm.pra1moviles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProductListActivity  extends FragmentActivity implements
        ItemListFragment.OnListFragmentInteractionListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Productos disponibles");
        if(toolbar != null)
            toolbar.setTitle("Productos disponibles");

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyItemRecyclerViewAdapter(inicializarProductos(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListFragmentInteraction(Producto item) {
        Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
        intent.putExtra("NOMBRE", item.getNombre());
        intent.putExtra("PRECIO", item.getPrecio());
        intent.putExtra("DESC", item.getDescripcion());

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        item.getImagen().compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();
        intent.putExtra("IMG", byteArray);

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public ArrayList<Producto> inicializarProductos() {
        ArrayList<Producto> JsonProductos=new ArrayList<Producto>();
        String json = null;
        try {

            InputStream is = getAssets().open("listaproductos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jProductos=jsonObject.getJSONArray("productos");
            for (int i=0; i< jProductos.length(); i++)
            {
                JSONObject jProducto=jProductos.getJSONObject(i);
                String jnombre=jProducto.getString("nombre");
                String jprecio=jProducto.getString("precio");
                String jdescripcion=jProducto.getString("descripcion");
                String jimagen=jProducto.getString("imagen");
                Bitmap imagen=
                        BitmapFactory.decodeStream(getAssets().open("images/"+jimagen));
                JsonProductos.add(new Producto(jnombre, jprecio,
                        jdescripcion, imagen));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonProductos;
    }

}
