
package com.tmm.android.chuck;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//RulesActivity is just a simple activity what has a block of text with a brief 
//description of the game
public class RulesActivity extends Activity implements OnClickListener{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rules);//selects xml file should be used to configure the design
		
		//finish button
		Button backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		//if the back button is clicked then go back to the main menu
		 
		finish();
	}

}
