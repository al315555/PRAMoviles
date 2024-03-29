package com.example.ilm.pra2moviles.drawer.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.ilm.pra2moviles.drawer.producto.ItemListFragment;
import com.example.ilm.pra2moviles.adapter.MyItemRecyclerViewAdapter;
import com.example.ilm.pra2moviles.ProductDetailActivity;
import com.example.ilm.pra2moviles.drawer.producto.Producto;
import com.example.ilm.pra2moviles.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.ilm.pra2moviles.util.ShareData;

public class ProductListFragment extends Fragment implements
        ItemListFragment.OnListFragmentInteractionListener{

    private View root;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<Producto> productoList ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_product_list, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        if(ShareData.productoList.isEmpty()) {
            productoList = inicializarProductos();
            ShareData.productoList.addAll(productoList);
        }
        productoList = ShareData.productoList;
        mAdapter = new MyItemRecyclerViewAdapter(productoList, this);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onListFragmentInteraction(Producto item) {
        Intent intent = new Intent(root.getContext(), ProductDetailActivity.class);
        intent.putExtra("NOMBRE", item.getNombre());
        intent.putExtra("PRECIO", item.getPrecio());
        intent.putExtra("DESC", item.getDescripcion());

        if(item.getImgFileName() != null) {
            /*ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            item.getImagen().compress(Bitmap.CompressFormat.JPEG, 100, bStream);
            byte[] byteArray = bStream.toByteArray();
             */
            intent.putExtra("FILENAME", item.getImgFileName());
        }
        startActivity(intent);
    }

    public ArrayList<Producto> inicializarProductos() {
        ArrayList<Producto> JsonProductos=new ArrayList<Producto>();
        String json = null;
        try {

            InputStream is = root.getContext().getAssets().open("listaproductos.json");
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
                final String filename = "images/"+jimagen;
                Bitmap imagen=
                        BitmapFactory.decodeStream(root.getContext().getAssets().open(filename));
                Double jlat=jProducto.getDouble("latitud");
                Double jlon=jProducto.getDouble("longitud");
                JsonProductos.add(new Producto(jnombre, jprecio,
                        jdescripcion, imagen, jlat, jlon, filename));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonProductos;
    }

}