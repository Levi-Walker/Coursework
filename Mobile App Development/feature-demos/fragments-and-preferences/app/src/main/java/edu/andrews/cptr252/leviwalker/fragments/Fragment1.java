package edu.andrews.cptr252.leviwalker.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View Fragment1View = inflater.inflate(R.layout.fragment_1, container, false);
        ConstraintLayout newUserConstrained = Fragment1View.findViewById(R.id.newUserConst);
        TextView userPromtFragment1 = Fragment1View.findViewById(R.id.userTextPrompt);
        EditText editText = Fragment1View.findViewById(R.id.editTextText);
        Button btnName = Fragment1View.findViewById(R.id.confirm);
        Button newUser = Fragment1View.findViewById(R.id.newUser);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String name = sharedPreferences.getString("name","");
        userPromtFragment1.setText("Welcome " + name);
        if (name.equals("")){
            newUserConstrained.setVisibility(View.VISIBLE);
        }
        else{
            newUserConstrained.setVisibility(View.INVISIBLE);
        }

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("name", editText.getText().toString());
                editor.apply();
                userPromtFragment1.setText("Welcome " + editText.getText().toString());
                newUserConstrained.setVisibility(View.INVISIBLE);
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("name","");
                editor.apply();
                userPromtFragment1.setText("Welcome");
                newUserConstrained.setVisibility(View.VISIBLE);
            }
        });




        return Fragment1View;
    }
}
