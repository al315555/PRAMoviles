package com.example.ilm.pra2moviles.drawer.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilm.pra2moviles.drawer.producto.Producto;
import com.example.ilm.pra2moviles.R;
import com.example.ilm.pra2moviles.util.ShareData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_mapa, container, false);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.theMap);
        mapFragment.getMapAsync(this);
        return v;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        for(Producto producto : ShareData.productoList){
            final double latitud = producto.getLatitud();
            final double longitud = producto.getLongitud();
            final String nombre = producto.getNombre();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                    .title("Localización de : " + nombre).icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
        if(ShareData.lastlocation != null ) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ShareData.lastlocation.getLatitude(), ShareData.lastlocation.getLongitude()), 8));
        }
    }

}
