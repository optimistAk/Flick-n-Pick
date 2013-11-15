package com.codepath.apps.cravengr.flickr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.apps.cravengr.ImageResult;
import com.codepath.apps.cravengr.R;
import com.codepath.apps.cravengr.YelpSearchActivity;
import com.codepath.apps.cravengr.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {
	
	FlickrClient client;
	ArrayList<FlickrPhoto> photoItems;
	GridView gvPhotos;
	PhotoArrayAdapter adapter;
	String userQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		
		
		client = FlickrClientApp.getRestClient();
		photoItems = new ArrayList<FlickrPhoto>();
		gvPhotos = (GridView) findViewById(R.id.gvPhotos);
		adapter = new PhotoArrayAdapter(this, photoItems);
		gvPhotos.setAdapter(adapter);
		loadPhotos();	
		
		gvPhotos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				//Log.d("DEBUG", "Item clicked");
	               Log.d("DEBUG", "clicked each item");
	               Intent i = new Intent(getApplicationContext(), YelpSearchActivity.class);
	               FlickrPhoto imageResult = photoItems.get(position);
	               i.putExtra("result", imageResult);	           
	       		   startActivity(i);
			}
		});		
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos, menu);
		return true;
	}
	
	public void loadPhotos() {
		
		SharedPreferences pref =   
			    PreferenceManager.getDefaultSharedPreferences(this);
			String userQuery = pref.getString("userQueryFromDB", "pizza"); 
		
			Toast.makeText(this, "PhotosAct: " + userQuery, Toast.LENGTH_SHORT).show();
		//userQuery = getIntent().getStringExtra("craveQuery");
		
		client.getInterestingnessList(new JsonHttpResponseHandler() { 
    		public void onSuccess(JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                // Add new photos to SQLite
                try {
					JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
					for (int x = 0; x < photos.length(); x++) {
						String uid  = photos.getJSONObject(x).getString("id");
						FlickrPhoto p = FlickrPhoto.byPhotoUid(uid);
						if (p == null) { p = new FlickrPhoto(photos.getJSONObject(x)); };
						p.save();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.e("debug", e.toString());
				}
                
				// Load into GridView from DB
				for (FlickrPhoto p : FlickrPhoto.recentItems()) {
					adapter.add(p);
				}
				Log.d("DEBUG", "Total: " + photoItems.size());
            }
    	}, userQuery);
	}

}
