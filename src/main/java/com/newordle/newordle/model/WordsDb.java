package com.newordle.newordle.model;

public class WordsDb {

    private int _id;
    private String word;
    private boolean used;

    public WordsDb(int _id, String word, boolean used) {
        this._id = _id;
        this.word = word;
        this.used = used;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean getUsedStatus() {
        return this.used;
    }

    public void setUsedStatus(boolean used) {
        this.used = used;
    }
}
