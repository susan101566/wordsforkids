package com.example.wordsforkids;

import com.example.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class WfkMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
