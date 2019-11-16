package com.rexindustries.firechat.util;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Manager {
    public static final boolean LOGGER = true;

    public static void loadFragment(Fragment fragment, Context context, int res) {
        AppCompatActivity activity = (AppCompatActivity) context;
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(res, fragment)
                .commit();
    }
}
