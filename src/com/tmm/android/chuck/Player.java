package com.tmm.android.chuck;

public class Player {
	String id; //String because of DBHelper was originally int
	String name;
	String email;
	String password;
	
	//empty constructor
	public Player(){
		
	}
	
	//constructor
	public Player(String id, String name,String email){
		this.id=id;
		this.name=name;
		this.email=email;
	}
	
	//constructor
	public Player(String name, String email){
		this.name=name;
		this.email=email;
	}
	
	//getting id
	public String getID(){
		return id;
	}
	
	//setting id
	public void setID(String id){
		this.id=id;
	}

	//getting name
	public String getName(){
		return name;
	}
	
	//setting name
	public void setName(String name){
		this.name=name;
	}
	
	//getting email
	public String getEmail(){
		return email;
	}
	
	//setting email
	public void setEmail(String email){
		this.email=email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void  setPassword(String password){
		this.password=password;
	}
}
