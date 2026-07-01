package edu.andrews.cptr252.leviwalker.pantrytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

public class FilterFragment extends Fragment {

    private static final String PREFS_NAME = "filters";
    private static final String KEY_EXPIRED_CHECKED = "expired_checked";
    private static final String KEY_LESS_THAN_5_CHECKED = "less_than_5_checked";
    private static final String KEY_IS_OPEN = "filter_open";

    private OnButtonClickListener buttonClickListener;

    private CheckBox expiredCheckBox;
    private CheckBox lessThan5CheckBox;
    private boolean submitted;

    private SharedPreferences sharedPreferences;

    public FilterFragment() {
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        Button submit = view.findViewById(R.id.submitFilter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.onButtonClicked();
            }
        });

        expiredCheckBox = view.findViewById(R.id.expired);
        lessThan5CheckBox = view.findViewById(R.id.lessthan5);

        expiredCheckBox.setChecked(sharedPreferences.getBoolean(KEY_EXPIRED_CHECKED, false));
        lessThan5CheckBox.setChecked(sharedPreferences.getBoolean(KEY_LESS_THAN_5_CHECKED, false));

        expiredCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = expiredCheckBox.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_EXPIRED_CHECKED, checked);
                editor.apply();
            }
        });

        lessThan5CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = lessThan5CheckBox.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_LESS_THAN_5_CHECKED, checked);
                editor.apply();
            }
        });


        return view;
    }

    public boolean submit(){
        return submitted;
    }

    public void setSubmitted(boolean sub){
        submitted = sub;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickListener) {
            buttonClickListener = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

}
