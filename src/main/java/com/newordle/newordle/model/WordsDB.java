package com.newordle.newordle.model;

// import org.springframework.data.mongodb.core.mapping.Document;

public class WordsDB {
    // @Document("WordsDB")
    private int index;
    private String word;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
