package com.rexindustries.firechat.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rexindustries.firechat.R;
import com.rexindustries.firechat.util.Manager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPassword extends Fragment {

    private EditText emailField;
    private Button sendBtn;

    private Context context;

    private FirebaseAuth auth;

    public ForgotPassword() {
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
        View root = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        auth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetPassword(view);
    }

    private void resetPassword(View view) {
        emailField = view.findViewById(R.id.forgot_pass_email);
        sendBtn = view.findViewById(R.id.forgot_password_btn);

        sendBtn.setOnClickListener(view1 -> {
            if (validateForm() == true) {
                String email = emailField.getText().toString();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Please check your email to reset password.", Toast.LENGTH_LONG).show();
                                Manager.loadFragment(new Login(), context, R.id.main_fragment_container);
                            } else {
                                Toast.makeText(context, "Fail to send email.", Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                validateForm();
            }
        });
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

        return valid;
    }
}
