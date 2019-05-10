package com.fredhappyface.blackc4t;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

    private final static Locale LOCALE = Locale.ENGLISH;

    @Override
    final protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        Intent intent = getIntent();
        int page = 0;
        String data = intent.getDataString();

        if (data != null) {
            page = Integer.parseInt(data);
        }
        mViewPager.setCurrentItem(page);


    }


    /*
    Create the overflow menu
     */
    @Override
    final public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    Do when an option has been selected
     */
    @Override
    final public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Activity_Settings.class));
            return true;
        }


        if (id == R.id.action_about) {
            startActivity(new Intent(this, Activity_About.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /*
         * Add each fragment to each tab. So Tab_Home -> Frag_OTP, Tab_otp -> Frag_otp ...
         */
        @Override
        final public Fragment getItem(final int position) {
            // Switch case for each fragment
            Fragment fragment = null;
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
                case 3:
                    fragment = new Frag_Password();
                    break;
                default:
                    break;

            }
            return fragment;
        }

        /*
        Show total number of pages
         */
        @Override
        final public int getCount() {
            return 4;
        }
    }


    final public void runOTP(final View view) {
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


    final public void runTwoKey(final View view) {
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

    final public void runPKE(final View view) {
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
        int publicKeyInt;
        int privateKeyInt;

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
            /*
            Generate the seed prime numbers
             */
            boolean isPrime;
            int prime0;
            int prime1;
            do {
                prime0 = PublicKeyEncryption.pkePrimeGen(64, 256);
                isPrime = PublicKeyEncryption.pkeIsPrime(prime0);
            }
            while (!isPrime);

            do {
                prime1 = PublicKeyEncryption.pkePrimeGen(64, 256);
                isPrime = PublicKeyEncryption.pkeIsPrime(prime1);
            }
            while (!isPrime);
            /*
            Generate the key modulus - this is used in the encryption and decryption methods
             */
            modulusInt = prime0 * prime1;

            /*
            Generate the euler totient - this is used to generate the private key exponent from the public key exponent
             */
            int eulerTotient = (prime0 - 1) * (prime1 - 1);

            /*
            Use Euclid's Algorithm to verify that publicKeyExponent and eulerTotient are coprime
             */

            int factor;
            do {
                publicKeyInt = Tools.getRandomInt(1, eulerTotient);
                factor = PublicKeyEncryption.pkeHighestCommonFactor(publicKeyInt, eulerTotient);
            }
            while (factor != 1);

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
        StringBuilder outString = new StringBuilder();
        for (int index = 0; (index < messageLength); index += 1) {
            int charInt;
            if ((!decrypt)) {
                charInt = PublicKeyEncryption.pkeExpMod(messageString.charAt(index), publicKeyInt, modulusInt);
            } else {
                charInt = PublicKeyEncryption.pkeExpMod(messageString.charAt(index), privateKeyInt, modulusInt);
            }
            outString.append((char) (charInt));
        }

        /*
        Write to output
         */
        output.setText(outString.toString());

    }



    final public void runPassword(View v) {
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
        String[] allWords = getAllWords();

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
        } catch (Exception e) {
            return null;
        }
        final String[] out = new String[allWords.size()];
        for (int index = 0; index < allWords.size(); index++) {
            out[index] = allWords.get(index);
        }
        return out;

    }




}
