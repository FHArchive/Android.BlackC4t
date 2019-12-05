package com.fredhappyface.blackc4t;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class Activity_Settings extends Abstract_Activity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final RadioGroup themeChoices = findViewById(R.id.theme);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentTheme = sharedPreferences.getInt("theme", 3);
        final RadioButton radioButton = (RadioButton) themeChoices.getChildAt(currentTheme);
        radioButton.setChecked(true);

    }


    public final void changeTheme(final View view) {
        final RadioGroup themeChoices = findViewById(R.id.theme);

        final int radioButtonID = themeChoices.getCheckedRadioButtonId();
        final View radioButton = themeChoices.findViewById(radioButtonID);
        final int idx = themeChoices.indexOfChild(radioButton);


        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme", idx);
        editor.apply();
        recreate();
    }
}
