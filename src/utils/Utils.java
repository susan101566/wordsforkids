package utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class Utils {
    public static File imageroot = new File(Environment.getExternalStorageDirectory() + File.separator + "WFK"
            + File.separator);
    
    public static void showMsg(Context c, String msg){
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
        
    }


    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
