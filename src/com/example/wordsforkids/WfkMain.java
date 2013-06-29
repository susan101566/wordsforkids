package com.example.wordsforkids;

import java.io.File;

import com.example.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;

public class WfkMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File file = Utils.imageroot;
        if (!file.exists()) {
            file.mkdirs();
        }
        Utils.showMsg(this, file.exists() + "");
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
