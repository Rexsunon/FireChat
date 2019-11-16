package com.rexindustries.firechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rexindustries.firechat.R;
import com.rexindustries.firechat.util.SharedPrefManager;

public class HomeActivity extends AppCompatActivity {

    private TextView userWelcomeText;
    private Button logoutBtn;

    private FirebaseAuth auth;

    private SharedPrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        manager = new SharedPrefManager(this);

        userWelcomeText = findViewById(R.id.user_welcome_text);
        logoutBtn = findViewById(R.id.logout_btn);

        setUserWelcomeText();
        logout();
    }

    private void setUserWelcomeText() {
        String welcomeText = "Welcome " + manager.loadFromPref("user_email");
        userWelcomeText.setText(welcomeText);
    }

    private void logout() {
        logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            manager.clearPref();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });
    }
}
