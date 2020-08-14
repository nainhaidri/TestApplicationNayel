package com.example.testapplicationnayel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText nameField, emailField, passwordField, confirmPasswordField;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nameField = findViewById(R.id.name);
        emailField = findViewById(R.id.emailAddress);
        passwordField = findViewById(R.id.password);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(nameField.getEditableText().toString(),
                        emailField.getEditableText().toString(),
                        passwordField.getEditableText().toString(),
                        confirmPasswordField.getEditableText().toString());
            }
        });


    }

    private void register(final String name, String email, String password, String confirmPassword) {
        if (!name.equals("") && name.length() > 3){
            if (!email.equals("")){
                if (!password.equals("") && password.length() > 5){
                    if (!confirmPassword.equals("") && confirmPassword.length() > 5){
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            if (password.equals(confirmPassword)){
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .build();

                                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage("You are successfully registered. Please sign in.")
                                                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    }
                                                }).setCancelable(false);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage(e.getMessage())
                                                .setTitle("Some Error Occurred. Please Try Again Later")
                                                .setCancelable(false)
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(this, "Email address is badly formatted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "Confirm passwordField too short", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please enter a valid emailFiled", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
        }
    }
}
