package com.example.ilm.pra2moviles.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    public static File getTempFile(final Context context, final String imgName){
        //it will return /sdcard/image.tmp
        final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, imgName);
    }

    public static File getTempFile(final Context context){
        return getTempFile(context, "imageNewProducto.tmp");
    }

    public static Uri fromFile(final File file, final Context context){
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
    }

    //Las imagenes venían giradas en el capturador, por lo que se giran 90 grados para enderezar
    public static Bitmap rotateBitmap(Bitmap captureBmp) {
        if(captureBmp != null) {
            Matrix matrix = new Matrix();
            int rotate = 90;
            matrix.postRotate(rotate);
            Bitmap bmResult = Bitmap.createBitmap(captureBmp, 0, 0, captureBmp.getWidth(), captureBmp.getHeight(), matrix, true);
            System.gc();
            return bmResult;
        }
        return null;
    }

    public static void getBitMap(ImageView itemImg, String filename, Context context) {
        //byte[] byteArray = getIntent().getByteArrayExtra("IMG");
        try {
            if(filename != null && filename.contains("images")) {

                Bitmap captureBmp = BitmapFactory.decodeStream(context.getAssets().open(filename));
                // Bitmap captureBmp = FileUtil.rotateBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                itemImg.setImageBitmap(captureBmp);

            } else if(filename != null && filename.contains("tmp")){
                final File file = FileUtil.getTempFile(context,filename);
                Bitmap captureBmp = android.provider.MediaStore.Images.Media.getBitmap(context.getContentResolver(), FileUtil.fromFile(file, context) );
                // Bitmap captureBmp = FileUtil.rotateBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                itemImg.setImageBitmap(captureBmp);
            }
        }catch (IOException ioex){
            Log.d("ProductList.:39:45", "IOException -> " + ioex.getLocalizedMessage().toString());
            itemImg.setImageBitmap(null);
        }
    }
}
