package com.om.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.om.R;

public class MarathonUIActivity extends Activity { 
/** Called when the activity is first created. */ 
	

@Override 
public void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState); 
setContentView(R.layout.main); 

		
		Intent i  = new Intent();
		i.setClassName("com.om","com.om.twitter.TestConnect");
		startActivity(i);


}

} 


