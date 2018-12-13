package com.fredhappyface.blackc4t;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
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


public class Main extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        Intent intent = getIntent();
        int page = 0;
        String data = intent.getDataString();

        if (data != null){
            page = Integer.parseInt(data);
        }
        mViewPager.setCurrentItem(page);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Add each fragment to each tab. So Tab_Home -> Frag_OTP, Tab_otp -> Frag_otp ...
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            // Switch case for each fragment
            Fragment fragment = null;
            switch (position){
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

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 4;
        }
    }


    public void runOTP(View v){
        /*
        Get objects, strings and lengths
         */
        EditText message = (EditText) findViewById(R.id.otp_message);
        String messageString = message.getText().toString();
        int messageLength = messageString.length();

        EditText key = (EditText) findViewById(R.id.otp_key);
        String keyString = key.getText().toString();
        int keyLength = keyString.length();

        ToggleButton toggle = (ToggleButton) findViewById(R.id.otp_decrypt);
        boolean decrypt = toggle.isChecked();

        TextView output = (TextView) findViewById(R.id.otp_output);

        /*
        Define other variables
         */
        String outputString = "";
        boolean error = false;

        /*
        If keyString has a length of 0, let the user know
         */
        if(keyLength < 1){
            error = true;
            outputString = "Enter a key";
        }

        /*
        For each character in the message, shift the value by key[index]
         */
        if(!error) {
            for (int index = 0; index < messageLength; index++) {
                if (index >= keyLength) {
                    keyString += keyString;
                    keyLength += keyLength;
                }
                char keyChar = keyString.charAt(index);
                char messageChar = messageString.charAt(index);
                char outChar;
                if (decrypt) {
                    outChar = (char) (messageChar - keyChar);
                } else {
                    outChar = (char) (messageChar + keyChar);
                }
                outputString += outChar;
            }
        }

        /*
        Write outputString to the text label
         */
        output.setText(outputString);

    }


    public void run2KOTP(View v){
        /*
        Get objects, strings and lengths
         */
        EditText message = (EditText) findViewById(R.id.kotp_message);
        String messageString = message.getText().toString();
        int messageLength = messageString.length();

        EditText key1 = (EditText) findViewById(R.id.kotp_key1);
        String keyString1 = key1.getText().toString();
        int keyLength1 = keyString1.length();

        EditText key2 = (EditText) findViewById(R.id.kotp_key2);
        String keyString2 = key2.getText().toString();
        int keyLength2 = keyString2.length();

        ToggleButton toggle = (ToggleButton) findViewById(R.id.kotp_decrypt);
        boolean decrypt = toggle.isChecked();

        TextView output = (TextView) findViewById(R.id.kotp_output);

        /*
        Define other variables
         */
        String outputString = "";
        boolean error = false;

        /*
        If keyString has a length of 0, let the user know
         */
        if(keyLength1 < 1 || keyLength2 < 1){
            error = true;
            outputString = "Enter both keys";
        }

        /*
        For each character in the message, shift the value by key[index]
         */
        if(!error) {
            for (int index = 0; index < messageLength; index++) {
                if (index >= keyLength1) {
                    keyString1 += keyString1;
                    keyLength1 += keyLength1;
                }
                if (index >= keyLength2) {
                    keyString2 += keyString2;
                    keyLength2 += keyLength2;
                }
                int keyChar = keyString1.charAt(index) + keyString2.charAt(index);
                char messageChar = messageString.charAt(index);
                char outChar;
                if (decrypt) {
                    outChar = (char) (messageChar - keyChar);
                } else {
                    outChar = (char) (messageChar + keyChar);
                }
                outputString += outChar;
            }
        }

        /*
        Write outputString to the text label
         */
        output.setText(outputString);

    }

    public void runPKE(View v){
        /*
        Get objects, strings and lengths
         */
        EditText message = (EditText) findViewById(R.id.pke_message);
        String messageString = message.getText().toString();
        int messageLength = messageString.length();

        EditText modulus = (EditText) findViewById(R.id.pke_modulus);
        String modulusString = modulus.getText().toString();
        int modulusLength = modulusString.length();

        EditText publicKey = (EditText) findViewById(R.id.pke_publickey);
        String publicKeyString = publicKey.getText().toString();
        int publicKeyLength = publicKeyString.length();

        EditText privateKey = (EditText) findViewById(R.id.pke_privatekey);
        String privateKeyString = privateKey.getText().toString();
        int privateKeyLength = privateKeyString.length();

        ToggleButton toggle = (ToggleButton) findViewById(R.id.pke_decrypt);
        boolean decrypt = toggle.isChecked();

        TextView output = (TextView) findViewById(R.id.pke_output);

        /*
        Define other variables
         */
        String outputString = "";
        int modulusInt = 0;
        int publicKeyInt = 0;
        int privateKeyInt = 0;

        /*
        If modulus has not been completed, populate it
         */
        if(modulusLength < 1){
            // populate mod
        }
        else{
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
                prime0 = pkePrimeGen(64,256);
                isPrime = pkeIsPrime(prime0);
            }
            while (isPrime == false);

            do {
                prime1 = pkePrimeGen(64,256);
                isPrime = pkeIsPrime(prime1);
            }
            while (isPrime == false);
            /*
            Generate the key modulus - this is used in the encryption and decryption methods
             */
            modulusInt = prime0*prime1;

            /*
            Generate the euler totient - this is used to generate the private key exponent from the public key exponent
             */
            int eulerTotient = (prime0-1)*(prime1-1);

            /*
            Use Euclid's Algorithm to verify that publicKeyExponent and eulerTotient are coprime
             */

            int factor;
            do {
                publicKeyInt = getRandomInt(1,eulerTotient);
                factor = pkeHighestCommonFactor(publicKeyInt,eulerTotient);
            }
            while (factor!= 1);

            /*
            Calculates the private key exponent from the public key exponent
             */
            privateKeyInt = pkeModInverse(publicKeyInt,eulerTotient);
        }
        else{
            publicKeyInt = Integer.parseInt(publicKeyString);
            privateKeyInt = Integer.parseInt(privateKeyString);
        }

        /*
        Fill the modulus, public and private fields
         */
        modulus.setText(Integer.toString(modulusInt));
        publicKey.setText(Integer.toString(publicKeyInt));
        privateKey.setText(Integer.toString(privateKeyInt));


        /*
        Do the encryption/ decryption
         */
        String outstring = "";
        for (int index = 0; (index < messageLength); index += 1) {
            int charInt;
            if ((!decrypt)) {
                charInt = pkeExpmod(messageString.charAt(index),publicKeyInt,modulusInt);
            } else {
                charInt = pkeExpmod(messageString.charAt(index),privateKeyInt,modulusInt);
            }
            outstring += (char)(charInt);
        }

        /*
        Write to output
         */
        output.setText(outstring);

    }

    /*
    Checks that the argument is a prime number
     */
    private boolean pkeIsPrime(int x){
        if(x%2 == 0 && x>2){
            return false;
        }
        int i = 3;
        double sqrt = Math.sqrt(x);
        while(i < sqrt){
            if(x%i == 0){
                return false;
            }
            i += 2;
        }
        return true;
    }

    /*
    Generates a number that may be prime
     */
    private int pkePrimeGen(int min, int max){
        return getRandomInt((int)(min/2.0),(int)(max/2.0))*2 + 1;
    }

    /*
    Returns random int between min and max (inclusive)
     */
    private int getRandomInt(int min, int max){
        return (int)((Math.random() * (max - min)) + min);
    }

    /*
    Finds the Highest Common Factor or argument a and arguement b
     */
    private int pkeHighestCommonFactor(int a, int b){
        if (b == 0){
            return a;
        }
        else{
            return pkeHighestCommonFactor(b, a % b);
        }
    }

    /*
    Calculates the private key exponent from the public key exponent
     */
    private int pkeModInverse(int a, int m){
        for (int x = 1; x < m; x++){
            if ((a * x) % m == 1){
                return x;
            }
        }
        return 1;
    }

    private int pkeExpmod( int base, int exp, int mod ){
        if (exp == 0) return 1;
        if (exp % 2 == 0){
            int expmod = pkeExpmod( base, (exp / 2), mod);
            return (int)(Math.pow(expmod , 2) % mod);
        }
        else {
            return (base * pkeExpmod( base, (exp - 1), mod)) % mod;
        }
    }

    public void runPassword(View v){
        /*
        Get objects, strings and lengths
         */
        EditText words = (EditText) findViewById(R.id.password_words);
        String wordsString = words.getText().toString();
        int wordsLength = wordsString.length();

        EditText numbers = (EditText) findViewById(R.id.password_numbers);
        String numbersString = numbers.getText().toString();
        int numbersLength = numbersString.length();

        EditText symbols = (EditText) findViewById(R.id.password_symbols);
        String symbolsString = symbols.getText().toString();
        int symbolsLength = symbolsString.length();

        TextView output = (TextView) findViewById(R.id.password_output);

        boolean error = false;

        int wordsInt = 0;
        int numbersInt = 0;
        int symbolsInt = 0;
        /*
        If a field has content, set
         */
        if(wordsLength > 0){
            wordsInt = Integer.parseInt(wordsString);
        }
        if(numbersLength > 0){
            numbersInt = Integer.parseInt(numbersString);
        }
        if(symbolsLength > 0){
            symbolsInt = Integer.parseInt(symbolsString);
        }

        String outputString = "";

        /*
        Populate allWords
         */
        String[] allWords = getAllWords();

        /*
        allWords can be null if the file cannot be read so report the error
         */
        if (allWords == null){
            error = true;
            outputString = "Error reading file";
        }

        if(!error) {
            /*
            Populate arrays
             */
            String[] wordsArray = getRandomWord(wordsInt, allWords);
            int[] digitArray = getRandomDigit(numbersInt);
            char[] symbolsArray = getRandomSymbol(symbolsInt);
            /*
            Add contents to outputString
             */
            for (int index = 0; index < wordsArray.length; index++) {
                outputString += capitaliseFirstLetter( wordsArray[index]);
            }
            for (int index = 0; index < digitArray.length; index++) {
                outputString += digitArray[index];
            }
            for (int index = 0; index < symbolsArray.length; index++) {
                outputString += symbolsArray[index];
            }

        }

        output.setText(outputString);


    }

    /*
    Return a string with the fist letter capitalised
     */
    private String capitaliseFirstLetter(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1,string.length());
    }

    /*
    Return random symbols
     */
    private char[] getRandomSymbol(int quantity){
        char[] symbols = new char[quantity];
        for(int index = 0; index < quantity; index ++) {
            symbols[index] = (char) getRandomInt(33, 44);
        }
        return symbols;
    }

    /*
    Return random digits
     */
    private int[] getRandomDigit(int quantity){
        int[] digits = new int[quantity];
        for(int index = 0; index < quantity; index ++) {
            digits[index] = (char) getRandomInt(0,9);
        }
        return digits;
    }

    /*
    Get the list of words from the text file
     */
    private String[] getAllWords() {
        String word = "";
        List<String> allWords = new ArrayList<String>();

        InputStream inputStream = getResources().openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            while ((word = reader.readLine()) != null) {
                allWords.add(word);
            }
        } catch (Exception e) {
            return null;
        }
        String[] out = new String[allWords.size()];
        for(int index = 0; index < allWords.size(); index++){
            out[index] = allWords.get(index);
        }
        return out;

    }
    /*
    Return random words
     */
    private String[] getRandomWord(int quantity, String[] allWords){
        String[] words = new String[quantity];
        for(int index = 0; index < quantity; index ++) {
            int line = getRandomInt(0, allWords.length);
            words[index] = allWords[line];
        }
        return words;
    }



}
