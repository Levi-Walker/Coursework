package edu.andrews.cptr252.leviwalker.quoteoftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuoteActivity extends AppCompatActivity {

    private TextView mQuoteTextView;
    private TextView mAuthorTextView;
    private Button mNextButton;

    private Quote[] mQuoteList = new Quote[]{
            new Quote(R.string.quote_text_0, R.string.quote_author_0),
            new Quote(R.string.quote_text_1, R.string.quote_author_1),
            new Quote(R.string.quote_text_2, R.string.quote_author_2),
    };

    private int mCurrentIndex = 0;

    public void updateQuote(){
        int quote = mQuoteList[mCurrentIndex].getmQuote();
        int author = mQuoteList[mCurrentIndex].getmAuthor();

        mQuoteTextView.setText(quote);
        mAuthorTextView.setText(author);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        mQuoteTextView = findViewById(R.id.quoteTextView);
        mAuthorTextView = findViewById(R.id.authorTextView);

        mNextButton = findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex++;
                if(mCurrentIndex==mQuoteList.length){
                    mCurrentIndex=0;
                }
                updateQuote();
            }
        });
    }
}