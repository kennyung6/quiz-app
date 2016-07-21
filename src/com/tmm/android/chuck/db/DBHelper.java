package com.tmm.android.chuck.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.tmm.android.chuck.Player;
import com.tmm.android.chuck.quiz.Question;

//all questions are stored in a DB on the client device, that data needs to be accessed
//this class will access the DB on Android by extending SQLiteOpenHelper
public class DBHelper extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.tmm.android.chuck/databases/"; //will be used later to access file that contains questions
	private static String DB_NAME = "mydatabase1";//name reference for the DB
	private SQLiteDatabase myDataBase; 
	private final Context myContext;
	
	//player table name
	private static final String TABLE_PLAYER="player";
	
	//player table columns names
	private static final String KEY_ID="playerID";
	private static final String KEY_NAME="playerName";
	private static final String KEY_EMAIL="email";

	// Constructor
	//Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	//must override the constructor to initialize the context and DB before starting
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}	

	//Creates a empty database on the system and rewrites it with your own database.
	//checks if the DB already exists, if it doesn't(the first time the app is launched on device) 
	//it calls the copyDataBase() method to get questions...
	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();
		if(!dbExist)
		{
			//By calling this method an empty database will be created into the default system path
			//of your application so we are going to be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase(); 
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	//Check if the database already exist to avoid re-copying the file each time you open the application.
	//return true if it exists, false if it doesn't
	
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			//database does't exist yet.
		}
		if(checkDB != null){
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	//Copies your database from your local assets-folder to the just created empty database in the
	//System folder, from where it can be accessed and handled.
	//This is done by transfering bytestream.
	 
	
	//opens an input stream to read the file and then writes it to the output stream which 
	//has been set up to be our DB Path/Name
	private void copyDataBase() throws IOException{

		//Open the database (questionsDB) as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		//Open the empty database as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes(data) from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException{
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if(myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	//method to return all users
	public List<Player>getUserSet(){
		List<Player>playerSet=new ArrayList<Player>();
		Cursor c=myDataBase.rawQuery("SELECT * FROM PLAYER", null);
		while (c.moveToNext()){
			Player p = new Player();
			p.setID(c.getString(4));
			p.setName(c.getString(5));
			p.setEmail(c.getString(6));
			p.setPassword(c.getString(1));
		}
		return playerSet;
				
	}
	

	// Add your public helper methods to access and get content from the database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
	// to you to create adapters for your views.
	public String category="QUESTIONS";
	//access the data so we can select questions to show the user using DAO access methods
	//method to return a random question set
	public List<Question> getQuestionSet(int difficulty, int numQ){
		//models the questions in a pre-created "question" class 
		//calls an SQL statement and then iterates through the cursor to populate
		//my Question ArrayList
		List<Question> questionSet = new ArrayList<Question>();
		Cursor c = myDataBase.rawQuery("SELECT * FROM " +category+ " WHERE DIFFICULTY=" + difficulty +
				" ORDER BY RANDOM() LIMIT " + numQ, null); //SQL Query to get random questions
		//loops through all rows and adds to list
		while (c.moveToNext()){
			Question q = new Question();
			q.setQuestion(c.getString(3));
			q.setAnswer(c.getString(4));
			q.setOption1(c.getString(5));
			q.setOption2(c.getString(6));
			q.setOption3(c.getString(7));
			q.setRating(difficulty);
			questionSet.add(q);
		}
		return questionSet;
	}
	
	
	// Adding new player
    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, player.getName()); // Player Name
        values.put(KEY_EMAIL, player.getEmail()); // Player Email
 
        // Inserting Row
        db.insert(TABLE_PLAYER, null, values);
        db.close(); // Closing database connection
    }
    
    // Updating single contact
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_EMAIL, player.getEmail());
 
        // updating row
        return db.update(TABLE_PLAYER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(player.getID()) });
    }
}