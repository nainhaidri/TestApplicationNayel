package com.example.testapplicationnayel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailFiled, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailFiled = findViewById(R.id.emailAddress);
        passwordField = findViewById(R.id.password);


        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailFiled.getEditableText().toString(), passwordField.getEditableText().toString());
            }
        });
    }

    private void signIn(String email, String password) {
        if (!email.equals("") &&  (Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            if (!password.equals("") && password.length() > 5){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(LoginActivity.this, ComplaintActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showErrorDialog(e.getMessage());
                    }
                });
            }
            else{
                Toast.makeText(this, "Password too short", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Email is badly formatted", Toast.LENGTH_LONG).show();
        }
    }
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
