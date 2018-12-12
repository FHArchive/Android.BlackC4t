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
        EditText message = (EditText) findViewById(R.id.message);
        String messageString = message.getText().toString();
        int messageLength = messageString.length();

        EditText key = (EditText) findViewById(R.id.key);
        String keyString = key.getText().toString();
        int keyLength = keyString.length();

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        boolean decrypt = toggle.isChecked();

        TextView output = (TextView) findViewById(R.id.output);

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


}
