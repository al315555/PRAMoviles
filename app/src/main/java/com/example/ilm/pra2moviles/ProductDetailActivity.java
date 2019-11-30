package com.example.ilm.pra2moviles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilm.pra2moviles.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class ProductDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setTitle("Detalle de producto");

        final TextView descText   =   findViewById(R.id.descText);
        final TextView precioText =   findViewById(R.id.precioText);
        final TextView nombreText =   findViewById(R.id.nombreText);
        final ImageView itemImg   =   findViewById(R.id.item_img);

        nombreText.setText((String)getIntent().getSerializableExtra("NOMBRE"));
        precioText.setText((String)getIntent().getSerializableExtra("PRECIO"));
        descText.setText((String)getIntent().getSerializableExtra("DESC"));
        final String filename = (String)getIntent().getSerializableExtra("FILENAME");
        FileUtil.getBitMap(itemImg, filename, this);
        Log.d("TDDM-PRA1-DetailItem", nombreText.getText().toString());
        //AVAILABLE TO SHOW BACK BUTTON ON TOP
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean returnValueUnless = super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
            this.finish();
            return true;
        }
        return returnValueUnless;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
