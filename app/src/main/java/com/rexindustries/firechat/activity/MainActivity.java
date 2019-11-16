package com.rexindustries.firechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rexindustries.firechat.R;
import com.rexindustries.firechat.fragment.Login;
import com.rexindustries.firechat.util.Manager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultFragment();
    }

    private void defaultFragment() {
        Manager.loadFragment(new Login(), this, R.id.main_fragment_container);
    }
}
