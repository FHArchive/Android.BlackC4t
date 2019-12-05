package com.fredhappyface.blackc4t;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Activity_Main extends Abstract_Activity {

    private static final Locale LOCALE = Locale.ENGLISH;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        final ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        final Intent intent = getIntent();
        int page = 0;
        final String data = intent.getDataString();

        if (data != null) {
            page = Integer.parseInt(data);
        }
        mViewPager.setCurrentItem(page);


    }


    /*
    Create the overflow menu
     */
    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    Do when an option has been selected
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here.
        final int itemId = item.getItemId();


        if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, Activity_Settings.class));
            return true;
        }


        if (itemId == R.id.action_about) {
            startActivity(new Intent(this, Activity_About.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        /*
         * Add each fragment to each tab. So Tab_Home -> Frag_OTP, Tab_otp -> Frag_otp ...
         */
        @Override
        public final Fragment getItem(final int position) {
            // Switch case for each fragment
            final Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new Frag_OTP();
                    break;
                case 1:
                    fragment = new Frag_2KOTP();
                    break;
                case 2:
                    fragment = new Frag_PKE();
                    break;
                default:
                    fragment = new Frag_Password();
                    break;

            }
            return fragment;
        }

        /*
        Show total number of pages
         */
        @Override
        public final int getCount() {
            return 4;
        }
    }


    public final void runOTP(final View view) {
        /*
        Get objects, strings and lengths
         */
        final EditText message = findViewById(R.id.otp_message);
        final String messageString = message.getText().toString();

        final EditText key = findViewById(R.id.otp_key);
        final String keyString = key.getText().toString();
        final int keyLength = keyString.length();

        final ToggleButton toggle = findViewById(R.id.otp_decrypt);
        final boolean decrypt = toggle.isChecked();

        final TextView output = findViewById(R.id.otp_output);

        /*
        Define other variables
         */
        StringBuffer outputString = new StringBuffer();
        boolean error = false;

        /*
        If keyString has a length of 0, let the user know
         */
        if (keyLength < 1) {
            error = true;
            outputString.append("Enter a key");
        }

        /*
        For each character in the message, shift the value by key[index]
         */
        if (!error) {
            outputString = Tools.doOneTimePad(keyString, messageString, decrypt);
        }

        /*
        Write outputString to the text label
         */
        output.setText(outputString);

    }


    public final void runTwoKey(final View view) {
        /*
        Get objects, strings and lengths
         */
        final EditText message = findViewById(R.id.two_key_message);
        final String messageString = message.getText().toString();

        final EditText key1 = findViewById(R.id.two_key_key1);
        final String keyString1 = key1.getText().toString();
        final int keyLength1 = keyString1.length();

        final EditText key2 = findViewById(R.id.two_key_key2);
        final String keyString2 = key2.getText().toString();
        final int keyLength2 = keyString2.length();

        final ToggleButton toggle = findViewById(R.id.two_key_decrypt);
        final boolean decrypt = toggle.isChecked();

        final TextView output = findViewById(R.id.two_key_output);

        /*
        Define other variables
         */
        StringBuffer outputString = new StringBuffer();
        boolean error = false;

        /*
        If keyString has a length of 0, let the user know
         */
        if (keyLength1 < 1 || keyLength2 < 1) {
            error = true;
            outputString.append("Enter both keys");
        }

        /*
        For each character in the message, shift the value by key[index]
         */
        if (!error) {
            outputString = Tools.doTwoKey(keyString1, keyString2,messageString, decrypt);
        }

        /*
        Write outputString to the text label
         */
        output.setText(outputString);

    }

    public final void runPKE(final View view) {
        /*
        Get objects, strings and lengths
         */
        final EditText message = findViewById(R.id.pke_message);
        final String messageString = message.getText().toString();
        final int messageLength = messageString.length();

        final EditText modulus = findViewById(R.id.pke_modulus);
        final String modulusString = modulus.getText().toString();
        final int modulusLength = modulusString.length();

        final EditText publicKey = findViewById(R.id.pke_public_key);
        final String publicKeyString = publicKey.getText().toString();
        final int publicKeyLength = publicKeyString.length();

        final EditText privateKey = findViewById(R.id.pke_private_key);
        final String privateKeyString = privateKey.getText().toString();
        final int privateKeyLength = privateKeyString.length();

        final ToggleButton toggle = findViewById(R.id.pke_decrypt);
        final boolean decrypt = toggle.isChecked();

        final TextView output = findViewById(R.id.pke_output);

        /*
        Define other variables
         */
        int modulusInt = 0;
        final int publicKeyInt;
        final int privateKeyInt;

        /*
        Get the modulus
         */
        if (modulusLength > 0) {
            modulusInt = Integer.parseInt(modulusString);
        }
        /*
        If the public key has not been filled and encrypting or
        If the private key has not been filled and decrypting then
        generate publicKeyInt and privateKeyInt
         */
        if ((publicKeyLength < 1 && !decrypt) || (privateKeyLength < 1 && decrypt)) {
            final int[] result = PublicKeyEncryption.generate();
            modulusInt = result[0];
            final int eulerTotient = result[1];
            publicKeyInt = result[2];

            /*
            Calculates the private key exponent from the public key exponent
             */
            privateKeyInt = PublicKeyEncryption.pkeModInverse(publicKeyInt, eulerTotient);
        } else {
            publicKeyInt = Integer.parseInt(publicKeyString);
            privateKeyInt = Integer.parseInt(privateKeyString);
        }

        /*
        Fill the modulus, public and private fields
         */
        modulus.setText(String.format(LOCALE, "%d", modulusInt));
        publicKey.setText(String.format(LOCALE, "%d", publicKeyInt));
        privateKey.setText(String.format(LOCALE, "%d", privateKeyInt));


        /*
        Do the encryption/ decryption
         */
        final StringBuilder outString = new StringBuilder();
        for (int index = 0; (index < messageLength); index += 1) {
            final int charInt;
            if (decrypt) {
                charInt = PublicKeyEncryption.pkeExpMod(messageString.charAt(index), privateKeyInt, modulusInt);
            } else {
                charInt = PublicKeyEncryption.pkeExpMod(messageString.charAt(index), publicKeyInt, modulusInt);

            }
            outString.append((char) (charInt));
        }

        /*
        Write to output
         */
        output.setText(outString.toString());

    }



    public final void runPassword(final View view) {
        /*
        Get objects, strings and lengths
         */
        final EditText words = findViewById(R.id.password_words);
        final String wordsString = words.getText().toString();
        final int wordsLength = wordsString.length();

        final EditText numbers = findViewById(R.id.password_numbers);
        final String numbersString = numbers.getText().toString();
        final int numbersLength = numbersString.length();

        final EditText symbols = findViewById(R.id.password_symbols);
        final String symbolsString = symbols.getText().toString();
        final int symbolsLength = symbolsString.length();

        final TextView output = findViewById(R.id.password_output);

        boolean error = false;

        int wordsInt = 0;
        int numbersInt = 0;
        int symbolsInt = 0;
        /*
        If a field has content, set
         */
        if (wordsLength > 0) {
            wordsInt = Integer.parseInt(wordsString);
        }
        if (numbersLength > 0) {
            numbersInt = Integer.parseInt(numbersString);
        }
        if (symbolsLength > 0) {
            symbolsInt = Integer.parseInt(symbolsString);
        }

        StringBuffer outputString = new StringBuffer();

        /*
        Populate allWords
         */
        final String[] allWords = getAllWords();

        /*
        allWords can be null if the file cannot be read so report the error
         */
        if (allWords == null) {
            error = true;
            outputString.append("Error reading file");
        }

        if (!error) {
            outputString = Tools.doPassword(wordsInt, numbersInt, symbolsInt, allWords);
        }

        output.setText(outputString);


    }


    /*
    Get the list of words from the text file
     */
    private String[] getAllWords() {
        String word;
        final List<String> allWords = new ArrayList<>();

        final InputStream inputStream = getResources().openRawResource(R.raw.words);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            while ((word = reader.readLine()) != null) {
                allWords.add(word);
            }
        } catch (final Exception e) {
            return null;
        }
        final String[] out = new String[allWords.size()];
        for (int index = 0; index < allWords.size(); index++) {
            out[index] = allWords.get(index);
        }
        return out;

    }




}
