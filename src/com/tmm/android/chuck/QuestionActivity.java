
package com.tmm.android.chuck;

import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tmm.android.chuck.db.DBHelper;
import com.tmm.android.chuck.quiz.GamePlay;
import com.tmm.android.chuck.EndgameActivity;
import com.tmm.android.chuck.quiz.Question;
import com.tmm.android.chuck.util.utility;
//QuestionsActivity just displays a question and then a selection of multiple 
//choice answers.

public class QuestionActivity extends Activity implements OnClickListener{

	private Question currentQ;
	private GamePlay currentGame;
	
	
	
	int roundCount=0;
	int count=0;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);//selects xml file should be used to configure the design
        
       
        
        //configure current game and get question
        
        	currentGame = ((ChuckApplication)getApplication()).getCurrentGame();
            currentQ = currentGame.getNextQuestion();
     		Button nextBtn = (Button) findViewById(R.id.nextBtn);
     		nextBtn.setOnClickListener(this);
     		
     		
             
             
             //Update the question and answer options..
             setQuestions();
             
             DBHelper db = new DBHelper(this);
             
             /**
              * CRUD Operations
              * */
             // Inserting Contacts
             Log.d("Insert: ", "Inserting ..");
             db.addPlayer(new Player("Tracey", "tracey@email.com"));
             db.addPlayer(new Player("Billy", "billy@email.com"));
            
             
         }
          
        
    
       

	// Method to set the text for the question and answers from the current games
    //current question
	 
	private void setQuestions() {
		//set the category label to current question category
		TextView catLabel=(TextView)findViewById(R.id.categoryLabel);
		catLabel.setText(EndgameActivity.quizCat);
		
		//set the question text from current question
		String question = utility.capitalise(currentQ.getQuestion()) + "?";
        TextView qText = (TextView) findViewById(R.id.question);
        qText.setText(question);
        
        //set the available options
        List<String> answers = currentQ.getQuestionOptions();
        TextView option1 = (TextView) findViewById(R.id.answer1);
        option1.setText(utility.capitalise(answers.get(0)));
        
        TextView option2 = (TextView) findViewById(R.id.answer2);
        option2.setText(utility.capitalise(answers.get(1)));
        
        TextView option3 = (TextView) findViewById(R.id.answer3);
        option3.setText(utility.capitalise(answers.get(2)));
        
        TextView option4 = (TextView) findViewById(R.id.answer4);
        option4.setText(utility.capitalise(answers.get(3)));
	}
    
	@Override
	public void onClick(View arg0) {
		//Log.d("Questions", "Moving to next question");
		
		//validate that a checkbox has been selected
		
		if (!checkAnswer()) return;

		
		
		//check if it is the end of game
		if (currentGame.isGameOver()){
			//Log.d("Questions", "End of game! lets add up the scores..");
			//Log.d("Questions", "Questions Correct: " + currentGame.getRight());
			//Log.d("Questions", "Questions Wrong: " + currentGame.getWrong());
			
			
			
			Intent k = new Intent(this, EndgameActivity.class);
			startActivity(k);
			finish();
			
		}
		//if game isn't over get another question
		else{
			Intent i = new Intent(this, QuestionActivity.class);
			startActivity(i);
			finish();
		}
		}
	
	
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


	//Check if a checkbox has been selected, and if it
	//has then check if its correct and update the gamescore
	
	private boolean checkAnswer() {
		String answer = getSelectedAnswer();
		if (answer==null){
			//Log.d("Questions", "No Checkbox selection made - returning");
			return false;
		}
		else {
			//Log.d("Questions", "Valid Checkbox selection made - check if correct");
			if (currentQ.getAnswer().equalsIgnoreCase(answer))
			{
				//Log.d("Questions", "Correct Answer!");
				currentGame.incrementRightAnswers();
			}
			else{
				//Log.d("Questions", "Incorrect Answer!");
				currentGame.incrementWrongAnswers();
			}
			return true;
		}
	}
	
	

	
	private String getSelectedAnswer() {
		RadioButton c1 = (RadioButton)findViewById(R.id.answer1);
		RadioButton c2 = (RadioButton)findViewById(R.id.answer2);
		RadioButton c3 = (RadioButton)findViewById(R.id.answer3);
		RadioButton c4 = (RadioButton)findViewById(R.id.answer4);
		if (c1.isChecked())
		{
			return c1.getText().toString();
		}
		if (c2.isChecked())
		{
			return c2.getText().toString();
		}
		if (c3.isChecked())
		{
			return c3.getText().toString();
		}
		if (c4.isChecked())
		{
			return c4.getText().toString();
		}
		
		return null;
	}




	
	
}
