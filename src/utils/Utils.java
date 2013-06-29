package utils;

import java.io.File;

import android.os.Environment;

public class Utils {
    public static File imageroot = new File(Environment.getExternalStorageDirectory() + File.separator + "WFK"
            + File.separator);

}
