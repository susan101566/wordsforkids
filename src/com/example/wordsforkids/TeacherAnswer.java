package com.example.wordsforkids;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.utils.Utils;
import com.example.wordsforkids.R.drawable;

public class TeacherAnswer extends Activity {
    
    private String imageName = null;
    private String uuid = null;
    
    private int editId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_answer);
        // Show the Up button in the action bar.
        setupActionBar();

        Intent intent = getIntent();
        String word_id = intent.getStringExtra(TeacherWordList.WORD_ID);
        if (word_id == null) {
        	editId = -1;
        }
        else if (word_id.contains("n")) {
        	editId = -1;
        } else {
        	editId = Integer.parseInt(word_id);
        }
        
        ImageView picture = (ImageView) findViewById(R.id.picture);
        if (editId == -1) {
		    try {
		        imageName = this.getIntent().getStringExtra("picture");
		        uuid = this.getIntent().getStringExtra("uuid");
		        this.audioFilename = Utils.getAudioFilename(uuid);
		        FileInputStream in = new FileInputStream(imageName);
		        BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inSampleSize = 10;
		        Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
		        picture.setImageBitmap(bmp);
		    } catch (FileNotFoundException e) {
		        Toast.makeText(this, "Can't find the file... Sorry", Toast.LENGTH_LONG).show();
		    }
		    
		    ImageView deleteButton = (ImageView) findViewById(R.id.deleteButton);
		    deleteButton.setVisibility(View.GONE); // for gone. 
        } else {
        	FileInputStream in;
        	Photo photo = WordListOpenHelper.getInstance(this).getPhoto(editId);
            try {
                imageName = photo.getFilename();
                uuid = Utils.getUUIDFromPicFilename(imageName);
                this.audioFilename = Utils.getAudioFilename(uuid);
                in = new FileInputStream(imageName);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
                picture.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            EditText editText = (EditText) findViewById(R.id.answer);
            editText.setText(photo.getAnswer());
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
        
        boolean status;
        if (editId == -1) {
        	Photo newPhoto = new Photo(123, imageName, answer);
        	status = WordListOpenHelper.getInstance(this).addPhoto(newPhoto);
        } else {
        	Photo photo = new Photo(editId, imageName, answer);
        	int statusId = WordListOpenHelper.getInstance(this).updatePhoto(photo);
//        	Utils.showMsg(this, ""+statusId);
        	if (statusId >=0) status = true;
        	else status = false;
        }
        if (status == false) {
            Utils.showMsg(this, "Sorry, something wrong happened in the database.");
            return;
        }
        if (editId == -1) {
        	Utils.showMsg(this, "Stored!");
        } else {
        	Utils.showMsg(this, "Updated!");
        }
        Intent intent = new Intent(this, TeacherWordList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    
    private boolean startRecording = true;
    private boolean startPlaying = true;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private String audioFilename = null;
    
    private void onPlay(boolean start) {
        if (start){ 
            startPlaying();
        } else {
            stopPlaying();
        }
    }
    
    private void onRecord(boolean start) {
        if (start){
            startRecording();
        } else {
            stopRecording();
        }
    }
    
    private void startPlaying() {
       mPlayer = new MediaPlayer();
       try {
           mPlayer.setDataSource(audioFilename);
           mPlayer.prepare();
           mPlayer.start();
       } catch (IOException e) {
           Log.e("danggg!!", "prepare() failed");
       }
    }
    
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        File f = new File(audioFilename);
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        mRecorder.setOutputFile(audioFilename);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("errrorrrrr!!", "prepare() failed");
        }

        mRecorder.start();
    }
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    
    public void recordAnswer(View view){
       onRecord(startRecording);
       ImageView recButton = (ImageView) findViewById(R.id.recButton);
       if (startRecording){
//    	   recButton.setText("Stop Recording");
    	   recButton.setContentDescription("Stop Recording");
    	   recButton.setImageResource(drawable.stop_recording_button);
       } else {
//    	   recButton.setText("Start Recording");
    	   recButton.setContentDescription("Start Recording");
    	   recButton.setImageResource(drawable.start_recording_button);
       }
       startRecording = !startRecording;
    }
    
    public void playAnswer(View view) {
        onPlay(startPlaying);
//        Button playButton = (Button) findViewById(R.id.playButton);
        if (startPlaying){
//        	playButton.setText("Stop");
//          Utils.showMsg(this, "start playing"); 
       } else {
//    	   playButton.setText("Play");
//          Utils.showMsg(this, "stop playing"); 
       }
       startPlaying = !startPlaying;
    }
    
    public void deletePhoto(View view) {
    	Photo photo = new Photo(editId);
    	WordListOpenHelper.getInstance(this).deletePhoto(photo);
    	
    	Utils.showMsg(this, "Deleted");
        Intent intent = new Intent(this, TeacherWordList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
