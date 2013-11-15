package com.codepath.apps.cravengr.flickr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.cravengr.R;
import com.codepath.apps.cravengr.R.layout;
import com.codepath.apps.cravengr.R.menu;
import com.codepath.oauth.OAuthLoginActivity;

public class FlickrLoginActivity extends OAuthLoginActivity<FlickrClient> {

	public String userQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
    @Override
    public void onLoginSuccess() {
 
    	Intent i = new Intent(this, PhotosActivity.class);
    	i.putExtra("craveQuery", userQuery);
    	startActivity(i);
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        getClient().connect();
    }

}
