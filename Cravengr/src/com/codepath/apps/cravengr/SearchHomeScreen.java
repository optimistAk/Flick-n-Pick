package com.codepath.apps.cravengr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.cravengr.flickr.FlickrLoginActivity;

public class SearchHomeScreen extends Activity {

	private EditText etSearchQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_home_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_home_screen, menu);
		return true;
	}

	public void onSubmitClick (View v){
		
		etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
		Intent i = new Intent(getApplicationContext(), FlickrLoginActivity.class);
		
		Toast.makeText(this, "Home screen "+etSearchQuery.getText().toString(), Toast.LENGTH_SHORT).show();
		
		SharedPreferences pref =   
			    PreferenceManager.getDefaultSharedPreferences(this);
			Editor edit = pref.edit();
			edit.putString("userQueryFromDB", etSearchQuery.getText().toString());
			edit.commit(); 
		
		i.putExtra("craveQuery", etSearchQuery.getText().toString());
		startActivity(i);
	}
	
	public void onClickFeelingHungry (View v){
		
		etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
		Intent i = new Intent(getApplicationContext(), YelpSearchActivity.class);
		i.putExtra("craveQuery", etSearchQuery.getText().toString());
		startActivity(i);
	}
}
