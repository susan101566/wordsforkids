package com.example.wordsforkids;

public class Photo {
	private int _id;
	private String _filename;
	private String _answer;
	
	public Photo() {}
	
	public Photo(int id, String filename, String answer) {
		this._id = id;
		this._filename = filename;
		this._answer = answer;
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
	
	
}
