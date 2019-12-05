package com.fredhappyface.blackc4t;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public abstract class Abstract_Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int currentTheme;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(HtmlCompat.fromHtml("<font color='#ABB2BF'>BlackC4t</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentTheme = sharedPreferences.getInt("theme", 3);
        switch (currentTheme) {
            case (0):
                setTheme(R.style.LightTheme);
                break;
            case (1):
                setTheme(R.style.DarkTheme);
                break;
            case (2):
                setTheme(R.style.BlackTheme);
                break;
            default:
                switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        setTheme(R.style.DarkTheme);
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        setTheme(R.style.LightTheme);
                        break;
                }
                break;

        }

    }

    @Override
    protected final void onResume() {
        super.onResume();
        final int theme = sharedPreferences.getInt("theme", 3);
        if (currentTheme != theme) {
            currentTheme = theme;
            recreate();
        }
    }
}
