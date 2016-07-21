package com.tmm.android.chuck;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

import com.tmm.android.chuck.quiz.Constants;
//SettingsActivity is a simple activity that has three radio buttons which
//allows the user to select the difficulty

public class SettingsActivity extends Activity implements OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);//selects xml file should be used to configure the design
        
        //set listener on update button
         
        Button updateBtn = (Button) findViewById(R.id.nextBtn);
		updateBtn.setOnClickListener(this);
		
		//Set selected button if saved
		 
		updateButtonWithPreferences();
		
    }


	//Method to update default check box
	 
	private void updateButtonWithPreferences() {
		RadioButton c1 = (RadioButton)findViewById(R.id.easySetting);
		RadioButton c2 = (RadioButton)findViewById(R.id.mediumSetting);
		RadioButton c3 = (RadioButton)findViewById(R.id.hardSetting);
		RadioButton c4 = (RadioButton)findViewById(R.id.General);
		RadioButton c5 = (RadioButton)findViewById(R.id.Geography);
		RadioButton c6= (RadioButton)findViewById(R.id.Culture);
		
		SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
		int diff = settings.getInt(Constants.DIFFICULTY, Constants.MEDIUM);
		
		switch (diff)
		{
		case Constants.EASY : 
			c1.toggle();
			break;
			
		case Constants.MEDIUM : 
			c2.toggle();
			break;
			
		case Constants.EXTREME :
			c3.toggle();
			break;	

		}
		//SharedPreferences Catsettings=getSharedPreferences(Constants.CATEGORY,0);
		//int category=settings.getInt(Constants.CATEGORY, Constants.GENERAL);
		
		//switch(category)
		//{
		//case Constants.GENERAL:
			//c4.toggle();
			//break;
			
		//case Constants.GEOGRAPHY:
			//c5.toggle();
			//break;
			
		//case Constants.CULTURE:
			//c6.toggle();
			//break;
		//}
	}





	@Override
	public void onClick(View arg0) {
		//check which settings set and return to menu
		 
		if (!checkSelected())
		{
			return;
		}
		else
		{
			SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
			Editor e = settings.edit();
			e.putInt(Constants.DIFFICULTY, getSelectedSetting());
			e.commit();
			SharedPreferences Catsettings=getSharedPreferences(Constants.CATEGORY,0);
			Editor f=settings.edit();
			f.putInt(Constants.CATEGORY, getSelectedSetting());
			f.commit();
			finish();
		}
		
	}


	//Method to check that a checkbox is selected
	//@return boolean
	 
	private boolean checkSelected() {
		RadioButton c1 = (RadioButton)findViewById(R.id.easySetting);
		RadioButton c2 = (RadioButton)findViewById(R.id.mediumSetting);
		RadioButton c3 = (RadioButton)findViewById(R.id.hardSetting);
		return (c1.isChecked() || c2.isChecked() || c3.isChecked());
	}


	//Get the selected setting 
	 
	private int getSelectedSetting() {
		RadioButton c1 = (RadioButton)findViewById(R.id.easySetting);
		RadioButton c2 = (RadioButton)findViewById(R.id.mediumSetting);
		if (c1.isChecked())
		{
			return Constants.EASY;
		}
		if (c2.isChecked())
		{
			return Constants.MEDIUM;
		}
		
		return Constants.EXTREME;
	}
	
	//Method to check that a checkbox is selected
		//@return boolean
		 
		private boolean checkSelected1() {
			RadioButton c4 = (RadioButton)findViewById(R.id.General);
			RadioButton c5 = (RadioButton)findViewById(R.id.Geography);
			RadioButton c6 = (RadioButton)findViewById(R.id.Culture);
			return (c4.isChecked() || c5.isChecked() || c6.isChecked());
		}


		//Get the selected setting 
		 
		private int getSelectedSetting1() {
			RadioButton c4 = (RadioButton)findViewById(R.id.General);
			RadioButton c5 = (RadioButton)findViewById(R.id.Geography);
			if (c4.isChecked())
			{
				return Constants.GENERAL;
			}
			if (c5.isChecked())
			{
				return Constants.GEOGRAPHY;
			}
			
			return Constants.CULTURE;
		}
	
	
	
}
