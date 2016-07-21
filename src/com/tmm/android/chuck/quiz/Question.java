package com.tmm.android.chuck.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
	
	private String question;
	private String answer;
	private String option1;
	private String option2;
	private String option3;
	private int rating;

	
	
	// getter and setter for question
	 
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
	//getter and setter for answer
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	//getter and setter for rating
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	

	
	//getter and setter for option 1
	public String getOption1() {
		return option1;
	}
	
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	
	//getter and setter for option 2
	public String getOption2() {
		return option2;
	}
	
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	
	
	//getter and setter for option 3
	public String getOption3() {
		return option3;
	}
	
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	
	//create List of Question options and shuffle them so in random order
	public List<String> getQuestionOptions(){
		List<String> shuffle = new ArrayList<String>();
		shuffle.add(answer);
		shuffle.add(option1);
		shuffle.add(option2);
		shuffle.add(option3);
		Collections.shuffle(shuffle);
		return shuffle;
	}

}
