package com.example.wordsforkids;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import com.example.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentAnswer extends Activity {

    private int id;
    private String picFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer);
        // Show the Up button in the action bar.
        setupActionBar();

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra(StudentWordList.WORD_ID));

        ImageView img = (ImageView) findViewById(R.id.picture);
        FileInputStream in;
        try {
            picFilename = WordListOpenHelper.getInstance(this).getPhoto(id).getFilename();
            in = new FileInputStream(picFilename);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
            img.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        getMenuInflater().inflate(R.menu.student_answer, menu);
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

    public void speakAnswer(View view) {
        String audioPath = Utils.getAudioFilename(Utils.getUUIDFromPicFilename(picFilename));
        MediaPlayer mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audioPath);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("danggg!!", "prepare() failed");
        }
    }
    
    public void submitAnswer(View view) {
        // Intent intent = new Intent(this, StudentWordList.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(intent);
        Photo photo = WordListOpenHelper.getInstance(this).getPhoto(id);
        String correctAnswer = photo.getAnswer();
        EditText editText = (EditText) findViewById(R.id.answer);
        Button button = (Button) findViewById(R.id.submitButton);
        String inputText = editText.getText().toString();
        if (correctAnswer.equalsIgnoreCase(inputText)) {
            editText.setEnabled(false);
            button.setEnabled(false);
            photo.incrementScore();
            WordListOpenHelper.getInstance(this).updatePhoto(photo);
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Wrong...", Toast.LENGTH_SHORT).show();
        }
    }
}
