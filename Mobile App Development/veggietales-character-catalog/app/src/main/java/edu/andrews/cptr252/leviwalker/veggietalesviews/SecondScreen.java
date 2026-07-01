package edu.andrews.cptr252.leviwalker.veggietalesviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        ImageView icon = findViewById(R.id.icon);
        TextView description = findViewById(R.id.description);
        TextView veggietalesname = findViewById(R.id.veggietalesName);
        Button info = findViewById(R.id.info);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String txtTitle = extras.getString("title");
            String txtDescription = extras.getString("description");
            int iconID = extras.getInt("icon");
            String extra = extras.getString("button");


            icon.setImageResource(iconID);
            veggietalesname.setText(txtTitle);
            description.setText(txtDescription);

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder myAlert;
                    myAlert = new AlertDialog.Builder(SecondScreen.this);
                    myAlert.setTitle(txtTitle);
                    myAlert.setMessage(extra);
                    myAlert.setIcon(iconID);
                    myAlert.setCancelable(true);
                    myAlert.create();
                    myAlert.show();
                }
            });



        }


    }
}
