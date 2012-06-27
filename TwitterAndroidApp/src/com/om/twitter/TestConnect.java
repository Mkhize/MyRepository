/**
 * This example shows how to connect to Twitter, display authorization dialog and save user's token
 * and username.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 * 
 * http://www.londatiga.net
 */

package com.om.twitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.om.R;
import com.om.twitter.TwitterApp.TwDialogListener;

public class TestConnect extends Activity {
	private TwitterApp mTwitter;
	private CheckBox mTwitterBtn;

	private static final String twitter_consumer_key = "Insert your consumer key here";
	private static final String twitter_secret_key = "Insert your secret key here";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_twitter);

		mTwitterBtn	= (CheckBox) findViewById(R.id.twitterCheck);
		
		mTwitterBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onTwitterClick();
			}
		});
		
		mTwitter 	= new TwitterApp(this, twitter_consumer_key,twitter_secret_key);
		
		mTwitter.setListener(mTwLoginDialogListener);
		
		if (mTwitter.hasAccessToken()) {
			mTwitterBtn.setChecked(true);
			
			String username = mTwitter.getUsername();
			username		= (username.equals("")) ? "Unknown" : username;
			
			mTwitterBtn.setText("  Twitter (" + username + ")");
			mTwitterBtn.setTextColor(Color.WHITE);
		}
		
		Button goBtn = (Button) findViewById(R.id.button1);
		
		goBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(TestConnect.this, TestPost.class));
			}
		});
	}
	
	private void onTwitterClick() {
		if (mTwitter.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setMessage("Delete current Twitter connection?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   mTwitter.resetAccessToken();
			        	   
			        	   mTwitterBtn.setChecked(false);
			        	   mTwitterBtn.setText("  Twitter (Not connected)");
			        	   mTwitterBtn.setTextColor(Color.GRAY);
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                
			                mTwitterBtn.setChecked(true);
			           }
			       });
			final AlertDialog alert = builder.create();
			
			alert.show();
		} else {
			mTwitterBtn.setChecked(false);
			
			mTwitter.authorize();
		}
	}

	private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {
		public void onComplete(String value) {
			String username = mTwitter.getUsername();
			username		= (username.equals("")) ? "No Name" : username;
		
			mTwitterBtn.setText("  Twitter  (" + username + ")");
			mTwitterBtn.setChecked(true);
			mTwitterBtn.setTextColor(Color.WHITE);
			
			Toast.makeText(TestConnect.this, "Connected to Twitter as " + username, Toast.LENGTH_LONG).show();
		}
		
		public void onError(String value) {
			mTwitterBtn.setChecked(false);
			
			Toast.makeText(TestConnect.this, "Twitter connection failed", Toast.LENGTH_LONG).show();
		}
	};
}