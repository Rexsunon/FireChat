package com.rexindustries.firechat.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rexindustries.firechat.R;
import com.rexindustries.firechat.activity.HomeActivity;
import com.rexindustries.firechat.util.Manager;
import com.rexindustries.firechat.util.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private final String TAG = Login.class.getSimpleName();

    private TextView forgotPassword, createAccount;
    private EditText mEmailField, mPasswordField;
    private Button loginBtn;

    private Context context;

    private FirebaseAuth auth;
    private SharedPrefManager manager;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        login(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        manager = new SharedPrefManager(context);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        auth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginUser(view);
        launchSignupPage(view);
        launchForgotPassword(view);
    }

    private void loginUser(View view) {
        mEmailField = view.findViewById(R.id.login_email);
        mPasswordField = view.findViewById(R.id.login_password);
        loginBtn = view.findViewById(R.id.login_btn);


        loginBtn.setOnClickListener(view1 -> {
            if (validateForm() == true) {
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();

                                if (Manager.LOGGER == true) {
                                    Log.d(TAG, "Login with Email and Password successful.");
                                }
                                Toast.makeText(context, "Login with Email and Password successful.", Toast.LENGTH_SHORT).show();

                                String userEmail = user.getEmail();
                                String name = user.getDisplayName();

                                manager.saveToPref("user_email", userEmail);
                                manager.saveToPref("username", name);
                                login(user);
                            } else {
                                if (Manager.LOGGER == true) {
                                    Log.e(TAG, "Login with Email and Password unsuccessful.", task.getException());
                                }
                                Toast.makeText(context, "Login with Email and Password unsuccessful.", Toast.LENGTH_SHORT).show();
                                login(null);
                            }
                        });
            } else {
                validateForm();
            }

        });
    }

    private void launchSignupPage(View view) {
        createAccount = view.findViewById(R.id.sign_up);

        createAccount.setOnClickListener(view1 -> Manager.loadFragment(new SignUp(), context, R.id.main_fragment_container));
    }

    private void launchForgotPassword(View view) {
        forgotPassword = view.findViewById(R.id.forgot_password);

        forgotPassword.setOnClickListener(view1 -> Manager.loadFragment(new ForgotPassword(), context, R.id.main_fragment_container));
    }

    private void login(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(context, HomeActivity.class));
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

}
