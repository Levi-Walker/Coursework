package edu.andrews.cptr252.leviwalker.servicecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView arrow;
    TextView text;
    private int click = 1;
    Animation appears;
    Animation disappears;
    private boolean animating = false;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrow = findViewById(R.id.arrowIndicator);
        text = findViewById(R.id.textview);


        text.setText("Click to continue");
        arrow.setVisibility(View.INVISIBLE);


        appears = new AlphaAnimation(0,1);
        appears.setDuration(500);
        disappears = new AlphaAnimation(1,0);
        disappears.setDuration(500);

        appears.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation){
                arrow.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation){
                arrow.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation){

            }
        });

        disappears.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation){
                arrow.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation){
                arrow.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation){

            }
        });
    }

    public void clicked(View view) {
        if (!animating) {
            animating = true;
            click = Math.random() < 0.5 ? 1 : -1;
            String dir = (click > 0 ? "Right" : "Left");
            text.setText(dir);
            arrow.setScaleX(click);
            arrow.startAnimation(appears);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    arrow.startAnimation(disappears);
                    text.setText("Click to continue");
                    animating = false;
                }
            }, 2000);
        }
    }
}