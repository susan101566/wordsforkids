package com.example.wordsforkids;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.utils.Utils;

public class WfkMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File file = Utils.imageroot;
        if (!file.exists()) {
            file.mkdirs();
        }
//        Utils.showMsg(this, file.exists() + "");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfk_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_wfk_main, menu);
        return true;
    }

    public void studentWordList(View view) {
        Intent intent = new Intent(this, StudentWordList.class);
        startActivity(intent);
    }

    public void teacherWordList(View view) {
        Intent intent = new Intent(this, TeacherWordList.class);
        startActivity(intent);
    }
}
