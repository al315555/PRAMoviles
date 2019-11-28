package com.example.ilm.pra1moviles.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.ilm.pra1moviles.FileUtil;
import com.example.ilm.pra1moviles.ListProductsInstance;
import com.example.ilm.pra1moviles.Producto;
import com.example.ilm.pra1moviles.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private String nombreStringValue;
    private String descripcionStringValue;
    private String precioStringValue;
    private double latitud, longitud;
    private ImageView imgnewProducto;
    private TextView coordenadas;
    private Context context;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mlocationCallback;

    private static final String EURO_SYMBOL_CURRENCY = "€";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private static final int REQUEST_MULTIPERMISION= 1;

    SendMessage SM;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = root.getContext();
        //Coordenadas
        coordenadas= root.findViewById(R.id.text_coordenadas);
        mlocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0){
                    Location location = locationList.get(locationList.size() - 1);
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    ListProductsInstance.lastlocation = location;
                    coordenadas.setText(String.format("Lat:%f Lon:%f", latitud, longitud));
                    if (mFusedLocationClient != null){
                        mFusedLocationClient.removeLocationUpdates(this);
                    }
                }
             }
        };

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000);
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setNumUpdates(1);

        requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION},
                REQUEST_MULTIPERMISION);

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mlocationCallback, Looper.myLooper());
        imgnewProducto =   root.findViewById(R.id.item_newimg);
        final TextView textNombre= root.findViewById(R.id.text_nombre);
        textNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                nombreStringValue = textNombre.getText().toString();
            }
        });
        final TextView textDescripcion= root.findViewById(R.id.text_descripcion);
        textDescripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                descripcionStringValue = textDescripcion.getText().toString();
            }
        });final TextView textPrecio= root.findViewById(R.id.text_precio);
        textPrecio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String precio =  textPrecio.getText().toString().trim();
                if (!precio.endsWith(EURO_SYMBOL_CURRENCY)){
                    precio = precio + EURO_SYMBOL_CURRENCY;
                }
                precioStringValue = precio;
            }
        });
        final Button buttonAddProduct = root.findViewById(R.id.button_add_product);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonAction();
                Toast.makeText(root.getContext(), "Producto nuevo añadido a la lista", Toast.LENGTH_SHORT).show();
            }
        });
        final Button buttonAddFoto = root.findViewById(R.id.button_add_foto);
        buttonAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFotoAction();
                Toast.makeText(root.getContext(), "Cargando la cámara...", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void addFotoAction() {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, FileUtil.fromFile(FileUtil.getTempFile(context), context) );
            startActivityForResult(intent, ListProductsInstance.REQUEST_CAMERA);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == ListProductsInstance.REQUEST_CAMERA) {
                final File file = FileUtil.getTempFile(context);
                try {
                    Bitmap captureBmp = android.provider.MediaStore.Images.Media.getBitmap(context.getContentResolver(), FileUtil.fromFile(file, context) );
                    ListProductsInstance.imagenProductoNuevo = captureBmp;
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    captureBmp.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    imgnewProducto.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addButtonAction() {
        final Producto productoNuevo = new Producto();
        productoNuevo.setDescripcion(descripcionStringValue);
        productoNuevo.setPrecio(precioStringValue);
        productoNuevo.setNombre(nombreStringValue);
        productoNuevo.setCoordenadas(coordenadas.getText().toString().trim());
        Bitmap imagen = ListProductsInstance.imagenProductoNuevo;
        if ( imagen != null) {
            Bitmap imagenCopia = Bitmap.createScaledBitmap(imagen, imagen.getWidth(), imagen.getHeight(), false);
            productoNuevo.setImagen(imagenCopia);
        }
        ListProductsInstance.imagenProductoNuevo = null;
        SM.sendData(productoNuevo);
        //GO TO PRODUCTS LIST
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_gallery);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(context, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface SendMessage {
        void sendData(Producto newProducto);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

}
