package com.example.wordsforkids;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter{
    
}
//
//public class ImageAdapter extends BaseAdapter {
//	private Context mContext;
//	
//	public ImageAdapter(Context c) {
//		mContext = c;
//	}
//	
//	
//	@Override
//	public int getCount() {
//		return WordListOpenHelper.getInstance(mContext).getPhotosCount();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
////		return null;
//		return WordListOpenHelper.getInstance(mContext).getPhoto(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
////		return 0;
//		
//		return WordListOpenHelper.getInstance(mContext).getPhoto(position).getID();
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ImageView imageView;
//		if (convertView == null) {
//			imageView = new ImageView(mContext);
//			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//		} else {
//			imageView = (ImageView) convertView;
//		}
//		
//
//        FileInputStream in;
//		try {
//			in = new FileInputStream(WordListOpenHelper.getInstance(mContext).getPhoto(position).getFilename());
//			BitmapFactory.Options options = new BitmapFactory.Options();
//	        options.inSampleSize = 10;
//	        Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
//			
//			imageView.setImageBitmap(bmp);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		return imageView;
//	}
//
//}
