package com.example.wordsforkids;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.example.utils.Utils;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherWordList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_word_list);
        // Show the Up button in the action bar.
        setupActionBar();
        populateListView();
        me = this;
    }

    private LayoutInflater mInflater;
    private ListView mListView;
    private List<Photo> photos;
    private Vector<String> names;
    private Vector<String> pics;
    private Context me;
    

    private void populateListView() {
        mListView = (ListView) findViewById(R.id.listView1);
        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        photos = WordListOpenHelper.getInstance(this).getAllPhotos();
        names = new Vector<String>();
        pics = new Vector<String>();

        for (Photo photo : photos) {
            names.add(photo.getAnswer());
            pics.add(photo.getFilename());
        }

        LazyAdapter adapter = new LazyAdapter(this, pics);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);
        final Context c = this;
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Utils.showMsg(c, "clicked on list item!");
            }
        });
    }
    public class LazyAdapter extends BaseAdapter {
        
        private Activity activity;
        private Vector<String> data;
        private LayoutInflater inflater = null;
        
        public LazyAdapter(Activity a, Vector<String> d) {
            activity = a;
            data=d;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi=convertView;
            if(convertView==null)
                vi = inflater.inflate(R.layout.listrow, null);

            TextView text=(TextView)vi.findViewById(R.id.textView1);;
            ImageView image=(ImageView)vi.findViewById(R.id.imageView1);
            text.setText(names.get(position));
            displayImage(data.get(position), image);
            return vi;
        }
    }
    
    public void displayImage(String url, ImageView imageView) {
        FileInputStream in;
        try {
            in = new FileInputStream(url);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
            imageView.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
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
    private String uuid;

    // When the new button is clicked.
    public void teacherAnswer(View view) {
        // The name of the new picture.
        uuid = UUID.randomUUID().toString();
        File root = Utils.imageroot;
        if (!root.exists()){
            root.mkdirs();
        }
        destination = new File(Utils.imageroot, uuid + ".jpg");
        try {
            destination.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!destination.exists())
            Utils.showMsg(this, "WHY!!");

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
            intent.putExtra("uuid", uuid + "");
            startActivity(intent);
        } else {
            Toast.makeText(this, "TeacheWordList: bad request", Toast.LENGTH_LONG).show();
        }
    }

}
