
package com.tmm.android.chuck;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmm.android.chuck.quiz.Constants;
import com.tmm.android.chuck.quiz.GamePlay;
import com.tmm.android.chuck.quiz.Helper;
import com.tmm.android.chuck.quiz.Question;
import com.tmm.android.chuck.QuestionActivity;
import java.io.IOException;


import android.database.SQLException;

import com.tmm.android.chuck.db.DBHelper;


public class EndgameActivity extends Activity implements OnClickListener {
	
	private GamePlay currentGame;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endgame);//selects xml file should be used to configure the design
		GamePlay currentGame = ((ChuckApplication)getApplication()).getCurrentGame();
		String result = "You Got " + currentGame.getRight() + "/" + currentGame.getNumRounds() + ".. ";
		String comment = Helper.getResultComment(currentGame.getRight(), currentGame.getNumRounds(), getDifficultySettings());
		
		
		TextView results = (TextView)findViewById(R.id.endgameResult);
		results.setText(result + comment);
		
		int image = Helper.getResultImage(currentGame.getRight(), currentGame.getNumRounds(), getDifficultySettings());
		ImageView resultImage = (ImageView)findViewById(R.id.resultPage);
		resultImage.setImageResource(image);
		
		//handle button actions
		Button nxtRoundBtn = (Button) findViewById(R.id.nxtRoundBtn);
		nxtRoundBtn.setOnClickListener(this);
		Button answerBtn = (Button) findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(this);	
	}
	
	
	//Method to return the difficulty settings	
	int level=2;//selects different level for each round
	 
	private int getDifficultySettings() {
		SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
		int diff = settings.getInt(Constants.DIFFICULTY, level);
		return diff;
	}
	
	
	
	//Method to override the back button on the phone
	//to prevent users from navigating back in to the quiz
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_BACK :
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	public static byte gameCount = 0;
	public static String quizCat="QUESTIONS"; //possibly remove static?

	@Override
	public void onClick(View v) {
			Intent i;
			
			switch (v.getId()){
			case R.id.nxtRoundBtn :
				//once logged in, load the main page
				//Log.d("LOGIN", "User has started the game");
				
				//Get Question set //
				List<Question> questions = getQuestionSetFromDb();
				
				

				//Initialise Game with retrieved question set ///
				if (gameCount < 2) //use gameCount to keep track of rounds!
				{
					gameCount++;
				GamePlay c = new GamePlay();
				c.setQuestions(questions);
				c.setNumRounds(getNumQuestions());
				c.count++;
				((ChuckApplication)getApplication()).setCurrentGame(c);  
				
				//Start Game Now.. //
				i = new Intent(this, QuestionActivity.class); //creates new intent and uses it to start another Activity class (QuestionActivity class)
				startActivityForResult(i, Constants.PLAYBUTTON);
				break;
				}
				else if(gameCount==2){
					Intent k = new Intent(this, SplashActivity.class);
					startActivity(k);
					//finish();
				}
				
			case R.id.answerBtn :
				Intent j = new Intent(this, AnswersActivity.class);
				startActivityForResult(j, Constants.PLAYBUTTON);
				break;
		}
	}
	
	//Method that retrieves a random set of questions from
		//the database for the given difficulty
		//@return
		//@throws Error
		 
		private List<Question> getQuestionSetFromDb() throws Error {
			int diff = getDifficultySettings();
			int numQuestions = getNumQuestions();
			DBHelper myDbHelper = new DBHelper(this);
			try {
				myDbHelper.createDataBase();
			} catch (IOException ioe) {
				throw new Error("Unable to create database");
			}
			try {
				myDbHelper.openDataBase();
			}catch(SQLException sqle){
				throw sqle;
			}
			//change the category based on gameCount (rounds)
			if(gameCount==0){
				quizCat="MUSICQUESTIONS";
			}
			else if(gameCount==1){
				quizCat="SPORTSQUESTIONS";
			}
			
			myDbHelper.category=quizCat; //changes original category
			List<Question> questions = myDbHelper.getQuestionSet(diff,  numQuestions);
			myDbHelper.close();
			return questions;
		}


		

		//Method to return the number of questions for the game
		private int getNumQuestions() {
			SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
			int numRounds = settings.getInt(Constants.NUM_ROUNDS, 5); //was 20
			return numRounds;
		}
		
		
		

}
