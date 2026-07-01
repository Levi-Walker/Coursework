package edu.andrews.cptr252.leviwalker.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Course", "Mobile App Dev");
        editor.putInt("CPTR_code", 252);
        editor.putBoolean("Active", true);

        String course = sharedPreferences.getString("Course","");
        int CPTR_code = sharedPreferences.getInt("CPTR_code", 0);
        boolean active = sharedPreferences.getBoolean("Active", false);

        editor.apply();

        TextView txtmsg = findViewById(R.id.txtmsg);

        String msg = "Course:" + course + "\nCode: " + CPTR_code + "\n" + active;

        txtmsg.setText(msg);

    }
}