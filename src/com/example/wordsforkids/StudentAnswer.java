package com.example.wordsforkids;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentAnswer extends Activity implements OnInitListener {

	//*** TTS START ***/ 	// Also the implements OnInitListener. 
    //TTS object
    private TextToSpeech TTS;
    //status check code
    private int DATA_CHECK_CODE = 0;
	//*** TTS END ***/
    
    
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_answer);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//*** TTS START ***/
		//check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, DATA_CHECK_CODE);
    	//*** TTS END ***/
        
		Intent intent = getIntent();
		String wordId = intent.getStringExtra(StudentWordList.WORD_ID);
		id = Integer.parseInt(wordId);
		
		TextView idText = (TextView) findViewById(R.id.wordId);
		idText.setText(wordId);
		
		ImageView img = (ImageView) findViewById(R.id.picture);
		FileInputStream in;
		try {
			in = new FileInputStream(WordListOpenHelper.getInstance(this).getPhoto(id).getFilename());
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 10;
	        Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
			img.setImageBitmap(bmp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//*** TTS START ***/	
	public void onDestroy() {
		if (TTS != null) {
			TTS.stop();
			TTS.shutdown();
		}
		super.onDestroy();
	}
	//*** TTS END ***/
	
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

	
	public void submitAnswer(View view) {
//		Intent intent = new Intent(this, StudentWordList.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
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
	
	//*** TTS START ***/
	public void speakAnswer(View view) {
		Photo photo = WordListOpenHelper.getInstance(this).getPhoto(id);
		String correctAnswer = photo.getAnswer();
		speak(correctAnswer);
	}
	
	private void speak(String text) {
		TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	
    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
            TTS = new TextToSpeech(this, this);
            }
            else {
                    //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
        //setup TTS
    public void onInit(int initStatus) {
            //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(TTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                TTS.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
	//*** TTS END ***/
}
