package com.mul_alexautoprogramm.clientlist.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.mul_alexautoprogramm.clientlist.R;

public class MySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.my_settings, rootKey);


    }


}
