package com.example.ilm.pra1moviles;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

public final class FileUtil {
    public static File getTempFile(Context context){
        //it will return /sdcard/image.tmp
        final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, "imageNewProducto.tmp");
    }

    public static Uri fromFile(File file, Context context){
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
    }
}
