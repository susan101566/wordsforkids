package com.example.wordsforkids;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class TeacherAnswer extends Activity {
    
    private String imageName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_answer);
        // Show the Up button in the action bar.
        setupActionBar();

        ImageView picture = (ImageView) findViewById(R.id.picture);
        try {
            imageName = this.getIntent().getStringExtra("picture");
            FileInputStream in = new FileInputStream(imageName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
            picture.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "file not found", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // When the submit button is clicked.
    public void submitAnswer(View view) {
        EditText answerView = (EditText) findViewById(R.id.answer);
        String answer = answerView.getText().toString();
        if (answer.length() == 0){
            Utils.showMsg(this, "Please enter description.");
            return;
        }
        Photo newPhoto = new Photo(123, imageName, answer);
        boolean status = WordListOpenHelper.getInstance(this).addPhoto(newPhoto);
        if (status == false) {
            Utils.showMsg(this, "Sorry, something wrong happened in the database.");
            return;
        }
        Utils.showMsg(this, "Stored!");
        Intent intent = new Intent(this, TeacherWordList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
