package com.example.wordsforkids;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TeacherWordList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_word_list);
        // Show the Up button in the action bar.
        setupActionBar();
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
        getMenuInflater().inflate(R.menu.teacher_word_list, menu);
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

    // Just a number so our intent listener doesn't listen for the wrong intent.
    private static final int REQUEST_IMAGE = 100;
    private File destination;

    // When the new button is clicked.
    public void teacherAnswer(View view) {
        // The name of the new picture.
        String filename = UUID.randomUUID().toString();
        destination = new File(Utils.imageroot, filename + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
        startActivityForResult(intent, REQUEST_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(this, TeacherAnswer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("picture", destination.getAbsolutePath());
            startActivity(intent);
        } else {
            Toast.makeText(this, "TeacherWordList: bad request", Toast.LENGTH_LONG).show();
        }
    }

}
