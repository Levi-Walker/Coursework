package edu.andrews.cptr252.leviwalker.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View Fragment2View = inflater.inflate(R.layout.fragment_2, container, false);
        TextView textfragment2 = Fragment2View.findViewById(R.id.fragment2TextBox);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String name = sharedPreferences.getString("name","");
        textfragment2.setText("Welcome "+name);
        if(name.equals("")){
            textfragment2.setText("Unknown User");
        }
        else{
            textfragment2.setText("Hey "+name+", What do you want to do today?");
        }
        return Fragment2View;
    }
}
