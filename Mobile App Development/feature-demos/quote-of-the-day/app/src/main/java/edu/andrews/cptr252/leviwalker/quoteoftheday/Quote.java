package edu.andrews.cptr252.leviwalker.quoteoftheday;

public class Quote {
    private int mQuote;

    private int mAuthor;

    public Quote(int quote, int author){
        mQuote = quote;
        mAuthor = author;
    }

    public int getmQuote() {
        return mQuote;
    }

    public void setmQuote(int mQuote) {
        this.mQuote = mQuote;
    }

    public int getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(int mAuthor) {
        this.mAuthor = mAuthor;
    }
}
