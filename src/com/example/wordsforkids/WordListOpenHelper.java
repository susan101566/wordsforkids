package com.example.wordsforkids;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordListOpenHelper extends SQLiteOpenHelper {
    
    public static WordListOpenHelper me;
    
    public static WordListOpenHelper getInstance (Context c){
       if (me == null) {
           me = new WordListOpenHelper(c);
       }
       return me;
    }

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "WordsForKidsManager";
	
	private static final String TABLE_PHOTOS = "photos";
	
	private static final String KEY_ID = "id";
	private static final String KEY_FILENAME = "filename";
	private static final String KEY_ANSWER = "answer";
	
	private WordListOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_FILENAME + " TEXT NOT NULL,"
				+ KEY_ANSWER + " TEXT NOT NULL" + ")";
		db.execSQL(CREATE_PHOTOS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        // Create tables again
        onCreate(db);
	}
	
	
	// Adding new photo
	public void addPhoto(Photo photo) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_FILENAME, photo.getFilename());
		values.put(KEY_ANSWER, photo.getAnswer());
		
		db.insert(TABLE_PHOTOS, null, values);
		db.close();
	}
	 
	// Getting single photo
	public Photo getPhoto(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_PHOTOS, new String[] { KEY_ID,
	            KEY_FILENAME, KEY_ANSWER }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	    
	    Photo photo = new Photo(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2));
	    
	    return photo;
	}
	 
	// Getting All Photos
	public List<Photo> getAllPhotos() {
		List<Photo> photoList = new ArrayList<Photo>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_PHOTOS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Photo photo = new Photo();
	            photo.setID(Integer.parseInt(cursor.getString(0)));
	            photo.setFilename(cursor.getString(1));
	            photo.setAnswer(cursor.getString(2));
	            // Adding contact to list
	            photoList.add(photo);
	        } while (cursor.moveToNext());
	    }
	 
	    return photoList;
	}
	 
	// Getting photos Count
	public int getPhotosCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PHOTOS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}
	
	// Updating single photo
	public int updatePhoto(Photo photo) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_FILENAME, photo.getFilename());
	    values.put(KEY_ANSWER, photo.getAnswer());
	 
	    // updating row
	    return db.update(TABLE_PHOTOS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(photo.getID()) });
	}
	 
	// Deleting single photo
	public void deletePhoto(Photo photo) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PHOTOS, KEY_ID + " = ?",
	            new String[] { String.valueOf(photo.getID()) });
	    db.close();
	}
	
}
