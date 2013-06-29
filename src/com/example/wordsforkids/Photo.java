package com.example.wordsforkids;

public class Photo {
	private int _id;
	private String _filename;
	private String _answer;
	private int _score;
	
	public Photo() {}
	
	public Photo(int id, String filename, String answer) {
		this._id = id;
		this._filename = filename;
		this._answer = answer;
		this._score = 0;
	}
	
	public Photo(int id, String filename, String answer, int score) {
		this._id = id;
		this._filename = filename;
		this._answer = answer;
		this._score = score;
	}

	public int getID() {
		return _id;
	}

	public void setID(int _id) {
		this._id = _id;
	}

	public String getFilename() {
		return _filename;
	}

	public void setFilename(String _filename) {
		this._filename = _filename;
	}

	public String getAnswer() {
		return _answer;
	}

	public void setAnswer(String _answer) {
		this._answer = _answer;
	}

	public int getScore() {
		return _score;
	}

	public void setScore(int _score) {
		this._score = _score;
	}
	
	public void incrementScore() {
		this._score++;
	}
	
}
