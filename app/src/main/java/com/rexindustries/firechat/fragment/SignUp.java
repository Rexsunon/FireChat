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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rexindustries.firechat.R;
import com.rexindustries.firechat.activity.HomeActivity;
import com.rexindustries.firechat.util.Manager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {

    private final String TAG = SignUp.class.getSimpleName();

    private EditText emailField, passwordField;
    private Button signUpBtn;

    private Context context;

    private FirebaseAuth auth;

    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        auth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUp(view);
    }

    private void signUp(View view) {
        emailField = view.findViewById(R.id.sign_up_email);
        passwordField = view.findViewById(R.id.sign_up_password);
        signUpBtn = view.findViewById(R.id.sign_up_btn);


        signUpBtn.setOnClickListener(view1 -> {
            if (validateForm() == true) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();

                                if (Manager.LOGGER == true) {
                                    Log.d(TAG, "Create user with Email and Password successful.");
                                }
                                Toast.makeText(context, "Create user with Email and Password successful.", Toast.LENGTH_SHORT).show();

                                login(user);
                            } else {
                                if (Manager.LOGGER == true) {
                                    Log.e(TAG, "Create user with Email and Password unsuccessful.", task.getException());
                                }
                                Toast.makeText(context, "Create user with Email and Password unsuccessful.", Toast.LENGTH_SHORT).show();
                                login(null);
                            }
                        });
            } else {
                validateForm();
            }
        });
    }

    private void login(FirebaseUser user) {
        if (user != null) {
            Manager.loadFragment(new Login(), context, R.id.main_fragment_container);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }
}
